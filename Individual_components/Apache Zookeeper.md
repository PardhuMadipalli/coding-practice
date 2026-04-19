---
layout: custom_page
title: Apache Zookeper
parent: Miscellaneous
---

## 1. The Problem: Why Coordination Is Hard

In a distributed system, multiple processes run on different machines, communicating over an unreliable network. These processes need to agree on things:

- Which process is the leader?
- What is the current configuration?
- Is process P still alive?
- Can I safely access this shared resource without another process accessing it simultaneously?

These are **coordination problems**. They are deceptively hard because of three fundamental realities:

1. **Partial failure** — Machine A can crash while Machine B continues running. Neither knows the other's state for certain.
2. **Asynchronous communication** — A message from A to B might arrive in 1ms, 1 second, or never. You cannot distinguish a slow machine from a dead one.
3. **No global clock** — There is no single clock that all machines agree on. Events on different machines cannot be trivially ordered.

The FLP impossibility result (Fischer, Lynch, Paterson, 1985) proved that in an asynchronous system with even one possible crash failure, no deterministic algorithm can guarantee consensus. This means every practical coordination system must make trade-offs — typically by using timeouts (introducing a notion of time) and requiring only a majority to agree rather than all nodes.

ZooKeeper is a service that solves these coordination problems once, correctly, so that application developers don't have to.

---

## 2. What ZooKeeper Is

ZooKeeper is a **centralized coordination service** that exposes a simple API resembling a filesystem. Distributed applications use it as shared, reliable infrastructure for:

- Storing small pieces of metadata (configuration, state)
- Detecting when processes join, leave, or fail
- Ordering operations across distributed processes
- Electing leaders
- Implementing distributed locks and barriers

It was developed at Yahoo! Research, open-sourced as part of the Apache Hadoop project, and later became a standalone Apache top-level project.

### What ZooKeeper Is NOT

- **Not a database.** It stores small metadata (kilobytes), not application data.
- **Not a message queue.** It can implement simple queues, but that's not its purpose.
- **Not a filesystem.** The tree structure looks like one, but the semantics are different.

---

## 3. Architecture: The Ensemble

ZooKeeper runs as a replicated cluster called an **ensemble**. An ensemble consists of an odd number of servers — typically 3, 5, or 7.

```
                    ┌─────────────────┐
                    │   ZK Server 1   │
                    │   (Follower)    │
                    └────────┬────────┘
                             │
┌──────────┐        ┌───────┴─────────┐        ┌─────────────────┐
│ Client A ├───────►│   ZK Server 2   │◄───────┤    Client C     │
└──────────┘        │   (Leader)      │        └─────────────────┘
                    └───────┬─────────┘
                            │
┌──────────┐        ┌──────┴──────────┐
│ Client B ├───────►│   ZK Server 3   │
└──────────┘        │   (Follower)    │
                    └─────────────────┘
```

### 3.1 Server Roles

**Leader** — Exactly one server in the ensemble is the leader at any time. The leader:
- Processes all write (state-changing) requests
- Proposes changes to followers
- Decides when a change is committed (once a quorum acknowledges)
- Coordinates recovery after failures

**Follower** — All other servers are followers. Followers:
- Serve read requests directly from their local copy of the data
- Forward write requests to the leader
- Participate in voting on proposals from the leader
- Participate in leader election when the current leader fails

**Observer** (optional) — An observer is like a follower that does NOT vote. It receives committed updates from the leader and serves reads, but it doesn't participate in quorum. Observers let you scale read throughput without increasing the number of voters (which would slow down writes because more servers need to acknowledge each proposal).

### 3.2 Quorum

A **quorum** is a strict majority of the voting members: `⌊N/2⌋ + 1` out of N servers.

| Ensemble Size | Quorum | Tolerated Failures |
|:---:|:---:|:---:|
| 3 | 2 | 1 |
| 5 | 3 | 2 |
| 7 | 4 | 3 |

Why a majority? Because any two majorities must overlap in at least one server. This guarantees that the latest committed state is always known to at least one server in any quorum, preventing data loss and split-brain scenarios.

**Why odd numbers?** An ensemble of 4 requires a quorum of 3 and tolerates 1 failure — the same as an ensemble of 3. The 4th server adds cost without improving fault tolerance. Odd numbers are strictly more efficient.

---

## 4. Sessions, Heartbeats, and Failure Detection

### 4.1 Sessions

When a client connects to ZooKeeper, it establishes a **session**. A session is a logical connection between the client and the ZooKeeper ensemble (not a specific server). The session has:

- A **session ID** — a unique 64-bit identifier assigned by the server.
- A **session timeout** — negotiated between the client and server at connection time. The client proposes a timeout; the server may adjust it within its configured bounds (`minSessionTimeout` and `maxSessionTimeout`, which default to 2× and 20× the tick time respectively).
- A **session password** — used to re-authenticate if the client reconnects to a different server.

### 4.2 Heartbeats

The session is kept alive through **heartbeats**. This is critical to understand:

1. The client periodically sends **ping** messages (heartbeats) to the server it's connected to.
2. The client library sends these automatically — the application developer doesn't manually send them, but the application must not block the client library's thread for extended periods.
3. The heartbeat interval is typically `sessionTimeout / 3`. For example, with a 30-second session timeout, the client sends a heartbeat roughly every 10 seconds.
4. Any request from the client (read, write, or explicit ping) resets the heartbeat timer on the server side.

### 4.3 Session Expiration

If the server does not receive any communication from the client within the session timeout period:

1. The server marks the session as **expired**.
2. All **ephemeral znodes** created by that session are deleted.
3. All **watches** registered by that session are removed.
4. If the client later tries to reconnect with the expired session ID, it receives a `SESSION_EXPIRED` error and must create a new session from scratch.

This is the mechanism that makes failure detection work. If a process crashes or becomes network-partitioned, it stops sending heartbeats, its session expires, and its ephemeral znodes vanish — signaling to other processes that it's gone.

### 4.4 Session States

A client session transitions through these states:

```
CONNECTING ──► CONNECTED ──► CLOSED
     ▲              │
     │              ▼
     └──── DISCONNECTED
                    │
                    ▼
               EXPIRED (terminal)
```

- **CONNECTING** — Initial state. Client is trying to establish a connection.
- **CONNECTED** — Session is active. Client can perform operations.
- **DISCONNECTED** — The TCP connection to the current server was lost, but the session may still be valid. The client library automatically tries to reconnect to another server in the ensemble. During this time, the session timeout clock is ticking on the server side.
- **CONNECTED** (again) — If the client reconnects to another server before the session timeout, the session resumes. All ephemeral znodes and watches are still intact.
- **EXPIRED** — The session timed out on the server side. This is a terminal state. The client must create a new session.

**Key insight:** The session timeout is enforced by the ZooKeeper ensemble, not by the client. Even if the client thinks it's still connected, the server may have already expired the session. This is by design — the server is the authority on session liveness.

---

## 5. The Data Model: Znodes

### 5.1 The Znode Tree

ZooKeeper maintains a tree of data nodes called **znodes**, rooted at `/`. Every znode is identified by its absolute path (e.g., `/app/config/database`).

```
/
├── /zookeeper          (reserved, internal use)
├── /app1
│   ├── /app1/config    (data: {"db_host": "10.0.1.5", "pool_size": 20})
│   └── /app1/leader    (data: "server-3")
└── /services
    └── /services/payment
        ├── /services/payment/instance-0000000001  (ephemeral)
        ├── /services/payment/instance-0000000002  (ephemeral)
        └── /services/payment/instance-0000000003  (ephemeral)
```

Each znode stores:
- **Data** — An opaque byte array. Maximum size is 1MB by default, but in practice you should keep it under a few kilobytes. ZooKeeper is not designed for large data.
- **Metadata (Stat structure)** — Version numbers, timestamps, and other bookkeeping.
- **Children** — A znode can have child znodes (except ephemeral znodes, which cannot have children).
- **ACL** — Access control list governing who can do what.

### 5.2 The Stat Structure

Every znode has a Stat structure containing:

| Field | Meaning |
|---|---|
| `czxid` | Transaction ID of the change that created this znode |
| `mzxid` | Transaction ID of the last modification |
| `pzxid` | Transaction ID of the last change to the children of this znode |
| `ctime` | Creation timestamp (milliseconds since epoch) |
| `mtime` | Last modification timestamp |
| `version` | Number of data changes (used for optimistic locking) |
| `cversion` | Number of child changes |
| `aversion` | Number of ACL changes |
| `ephemeralOwner` | Session ID of the owner if ephemeral; 0 if persistent |
| `dataLength` | Length of the data in bytes |
| `numChildren` | Number of children |

The `version` field is particularly important. When you update a znode, you can pass the expected version. If the current version doesn't match, the update fails with `BadVersionException`. This is **optimistic concurrency control** — it lets multiple clients safely update the same znode without locks.

### 5.3 Znode Types

**Persistent znodes** — Created explicitly, deleted explicitly. They survive client disconnections, server restarts, and ensemble failures (as long as a quorum survives). Use these for configuration, metadata, and anything that should outlive the process that created it.

**Ephemeral znodes** — Tied to the client session that created them. When the session ends (either by explicit close or by timeout due to missed heartbeats), the ephemeral znode is automatically deleted. Ephemeral znodes **cannot have children**. Use these for representing live processes — if the process dies, the znode disappears, and other processes can detect this.

**Sequential znodes** — When you create a sequential znode, ZooKeeper appends a 10-digit, zero-padded, monotonically increasing sequence number to the path you specify. For example, creating `/locks/lock-` produces `/locks/lock-0000000001`, then `/locks/lock-0000000002`, etc. The counter is maintained per parent znode and never reuses numbers (even after deletions). Use these when you need ordering.

**Container znodes** (added in 3.6) — A special persistent znode that is automatically deleted by the server when its last child is removed. Useful for recipes like leader election or locks where the parent path should be cleaned up when no longer needed.

**TTL znodes** (added in 3.6) — Persistent znodes with a time-to-live. If not modified within the TTL and they have no children, they are candidates for deletion. Must be explicitly enabled in server configuration.

These types can be combined:

| Type | Persistent? | Auto-deleted on session end? | Sequenced? |
|---|:---:|:---:|:---:|
| Persistent | ✓ | ✗ | ✗ |
| Persistent Sequential | ✓ | ✗ | ✓ |
| Ephemeral | ✗ | ✓ | ✗ |
| Ephemeral Sequential | ✗ | ✓ | ✓ |

---

## 6. The ZAB Protocol: How Consensus Works

ZooKeeper uses the **ZooKeeper Atomic Broadcast (ZAB)** protocol for replication. ZAB is a crash-recovery atomic broadcast protocol, similar in goals to Paxos and Raft but designed specifically for ZooKeeper's primary-backup architecture.

### 6.1 Transaction IDs (zxids)

Every state change in ZooKeeper is assigned a globally unique, monotonically increasing **zxid** (ZooKeeper Transaction ID). A zxid is a 64-bit number composed of two parts:

```
zxid = [epoch (32 bits)][counter (32 bits)]
```

- **Epoch** — Incremented every time a new leader is elected. This ensures that proposals from an old leader cannot be confused with proposals from the new leader.
- **Counter** — Incremented for each transaction within an epoch.

For example, zxid `0x0000000300000005` means epoch 3, transaction 5.

### 6.2 The Write Path in Detail

When a client sends a write request (create, delete, setData):

```
Step 1: Client sends write to Server 3 (a follower)

Step 2: Server 3 forwards the request to the Leader

Step 3: Leader assigns the next zxid and creates a PROPOSAL
        Leader ──PROPOSAL(zxid, txn)──► Follower 1
        Leader ──PROPOSAL(zxid, txn)──► Follower 2
        Leader ──PROPOSAL(zxid, txn)──► Follower 3
        (Leader also writes to its own transaction log)

Step 4: Each follower writes the proposal to its transaction log
        on disk and sends an ACK back to the leader
        Follower 1 ──ACK──► Leader
        Follower 2 ──ACK──► Leader

Step 5: Once the leader receives ACKs from a quorum (including itself),
        it sends a COMMIT message to all followers
        Leader ──COMMIT(zxid)──► Follower 1
        Leader ──COMMIT(zxid)──► Follower 2
        Leader ──COMMIT(zxid)──► Follower 3

Step 6: All servers apply the transaction to their in-memory data tree

Step 7: The server connected to the client sends the response back
```

**Critical property:** Proposals are committed in strict order. If proposal P1 has a lower zxid than P2, then P1 is committed before P2 on every server. This is what "atomic broadcast" means — all servers see the same sequence of updates.

### 6.3 The Read Path

Reads are served locally by whichever server the client is connected to. The server simply reads from its in-memory data tree and returns the result. No communication with the leader or other servers is needed.

**Consequence:** Reads are fast and scale horizontally (more servers = more read capacity), but they can return **stale data**. If a write was just committed but the follower hasn't received the COMMIT yet, a read on that follower returns the old value.

If your application requires reading the most recent value, you have two options:

1. **`sync()` then `getData()`** — The `sync()` operation forces the connected server to catch up with the leader before the subsequent read. This guarantees the read reflects all writes committed up to the point of the sync call.
2. **Send the read to the leader** — Some client libraries support this, but it reduces the scalability benefit of reads.

### 6.4 Leader Election (ZAB Phase 1)

When the ensemble starts or the current leader fails, ZAB enters the **leader election** phase:

1. Each server proposes itself as leader, broadcasting its vote: `(proposed_leader_id, last_zxid)`.
2. When a server receives a vote from another server, it compares:
   - First, the **zxid** — the server with the higher last zxid is preferred (it has more up-to-date data).
   - If zxids are equal, the **server ID** — the higher server ID wins (arbitrary tiebreaker).
3. If a server sees a better candidate, it changes its vote and re-broadcasts.
4. Once a server sees that a quorum has voted for the same candidate, that candidate becomes the leader.

This ensures the new leader has the most complete transaction history, minimizing the amount of data that needs to be synchronized during recovery.

### 6.5 Recovery and Synchronization (ZAB Phase 2)

After a leader is elected:

1. The leader determines the latest committed zxid.
2. Each follower tells the leader its last zxid.
3. The leader sends each follower the transactions it's missing (a **DIFF**), or if the follower is too far behind, a full **SNAP** (snapshot) of the data tree.
4. Once a follower is synchronized, it joins the active ensemble and can serve reads and participate in voting.

### 6.6 Persistence: Transaction Logs and Snapshots

ZooKeeper persists data in two ways:

- **Transaction log** — Every committed transaction is written to a sequential log file on disk before the ACK is sent. This is an append-only write, which is fast. The transaction log is the source of truth for durability.
- **Snapshots** — Periodically, ZooKeeper writes a full snapshot of the in-memory data tree to disk. Snapshots are "fuzzy" — they may include some transactions that were applied during the snapshot process. This is fine because ZooKeeper replays the transaction log from the snapshot's starting zxid during recovery to reach a consistent state.

For performance, the transaction log and snapshot directories should be on separate physical disks (or at least separate I/O paths).

---

## 7. Watches: The Event Notification System

### 7.1 How Watches Work

A **watch** is a one-time notification mechanism. When a client reads data from ZooKeeper, it can optionally register a watch on that znode. If the znode subsequently changes, ZooKeeper sends a **watch event** to the client.

```
1. Client calls getData("/app/config", watch=true)
   → Server stores: "Client A is watching /app/config for data changes"
   → Server returns current data to Client A

2. Client B calls setData("/app/config", newData)
   → Server commits the change
   → Server finds that Client A has a watch on /app/config
   → Server sends WatchEvent(type=NodeDataChanged, path=/app/config) to Client A
   → Server removes the watch (it's one-time)

3. Client A receives the event
   → Client A calls getData("/app/config", watch=true) again to:
      a) Get the new data
      b) Re-register the watch for future changes
```

### 7.2 Watch Event Types

| Event Type | Triggered By | Registered Via |
|---|---|---|
| `NodeCreated` | A znode is created at the watched path | `exists()` |
| `NodeDeleted` | The watched znode is deleted | `exists()`, `getData()` |
| `NodeDataChanged` | The data of the watched znode is modified | `exists()`, `getData()` |
| `NodeChildrenChanged` | A child is added or removed from the watched znode | `getChildren()` |

### 7.3 Watch Guarantees

ZooKeeper provides these guarantees about watches:

1. **Ordered delivery** — Watch events are delivered to the client before the client sees the new data through any subsequent read. If a client has a watch on `/config` and another client changes `/config`, the watching client will receive the watch event before any `getData()` call returns the new value. This prevents a subtle race condition where you could read new data without knowing it changed.

2. **Ordered relative to other events** — Watch events are delivered in the same order as the changes were applied on the server.

3. **The client sees the watch event before the new state** — This is a guarantee about the client's local view. The client library delivers the watch callback before it processes any responses that reflect the change.

### 7.4 Watch Caveats

**One-time trigger.** A watch fires exactly once. Between the time you receive the event and re-register the watch, you might miss changes. For example:

```
Time 1: Client reads /config (value=A), sets watch
Time 2: /config changes to B → watch fires
Time 3: /config changes to C
Time 4: Client re-registers watch, reads /config (value=C)
```

The client never saw value B. It saw A, got notified of a change, and then saw C. For most coordination use cases, this is fine — you care about the current state, not every intermediate state. But if you need every change, ZooKeeper watches alone are not sufficient.

**Session events.** If the client disconnects and reconnects (within the session timeout), watches that were registered before the disconnection are re-registered automatically by the client library. However, any watch events that would have fired during the disconnection period may be lost. The client should re-read the data after reconnection.

**Persistent and recursive watches** (added in 3.6) — These are multi-fire watches that don't need re-registration. Persistent watches fire on every change. Recursive watches fire on changes to the znode and all its descendants. These address the one-time limitation but are a newer feature.

---

## 8. Recipes: Building Higher-Level Primitives

ZooKeeper's API is intentionally low-level. The power comes from combining the primitives (znodes, ephemeral nodes, sequential nodes, watches) into higher-level coordination patterns called **recipes**.

### 8.1 Leader Election

**Goal:** Among N processes, exactly one should be the "leader" at any time. If the leader fails, a new one should be elected automatically.

**Algorithm:**

1. All candidates create an **ephemeral sequential** znode under a designated election path:
   ```
   Process A creates: /election/candidate-0000000001
   Process B creates: /election/candidate-0000000002
   Process C creates: /election/candidate-0000000003
   ```

2. Each process calls `getChildren("/election")` to list all candidates.

3. The process whose znode has the **lowest sequence number** is the leader.
   - Process A sees that `/election/candidate-0000000001` is the lowest → Process A is the leader.

4. **Non-leaders watch the znode immediately before theirs** (not the leader's znode):
   - Process B watches `/election/candidate-0000000001` (the one just before it)
   - Process C watches `/election/candidate-0000000002` (the one just before it)

5. If the leader (Process A) crashes:
   - Its session expires → `/election/candidate-0000000001` is deleted (ephemeral)
   - Process B receives a `NodeDeleted` watch event
   - Process B calls `getChildren("/election")` and sees that its znode (`candidate-0000000002`) is now the lowest → Process B becomes the new leader

6. If a non-leader (Process C) crashes:
   - `/election/candidate-0000000003` is deleted
   - No one was watching it (Process B watches `...0001`, not `...0003`), so no unnecessary notifications fire

**Why watch the znode just before yours, not the leader's znode?**

This is critical. Consider what happens if all N processes watch the leader's znode:

```
BAD: All watch the leader
─────────────────────────
Leader (candidate-0001) dies
→ Watch fires on ALL N-1 remaining processes simultaneously
→ ALL N-1 processes call getChildren() at the same time
→ Massive spike of requests to ZooKeeper ("thundering herd" / "herd effect")
→ Only one of them becomes the new leader; the rest wasted effort
```

```
GOOD: Each watches the one before it
─────────────────────────────────────
Leader (candidate-0001) dies
→ Watch fires on ONLY Process B (which watches candidate-0001)
→ Process B calls getChildren(), sees it's the lowest → becomes leader
→ Process C is not disturbed at all
→ Only 1 process reacts, O(1) load on ZooKeeper
```

This is called the **"chain watch"** pattern. Each process forms a chain where it watches its predecessor. When any process in the chain dies, only its immediate successor is notified. This scales to thousands of candidates without overwhelming ZooKeeper.

**What if a middle process dies?**

```
Candidates: 0001 (leader), 0002, 0003, 0004
Watches:    0002→0001, 0003→0002, 0004→0003

Process with 0002 dies:
→ 0003 receives NodeDeleted for 0002
→ 0003 calls getChildren(), gets [0001, 0003, 0004]
→ 0003 sees that 0001 is still the lowest → 0003 is NOT the leader
→ 0003 now watches 0001 (the znode just before it in the new sorted list)
→ The chain repairs itself: 0003→0001, 0004→0003
```

### 8.2 Distributed Locks

**Goal:** Ensure that at most one process holds a lock at any time. Processes that can't acquire the lock should wait in FIFO order.

**Algorithm (very similar to leader election):**

1. To acquire the lock, a process creates an **ephemeral sequential** znode:
   ```
   /locks/resource-X/lock-0000000001
   ```

2. The process calls `getChildren("/locks/resource-X")` and sorts the results.

3. If its znode has the **lowest sequence number**, it holds the lock. Proceed.

4. If not, it sets a watch on the znode **immediately before** its own (same chain pattern as leader election) and waits.

5. When the watch fires (the predecessor released the lock or crashed), the process re-checks `getChildren()`. If it's now the lowest, it holds the lock.

6. To release the lock, the process deletes its znode. (Or if it crashes, the ephemeral znode is automatically deleted.)

**Why this works:**
- Sequential znodes guarantee FIFO ordering — no starvation.
- Ephemeral znodes guarantee that crashed lock holders automatically release.
- Chain watches prevent the herd effect.

**Read-write locks** can be built on top of this by using different prefixes (e.g., `/locks/resource-X/read-` and `/locks/resource-X/write-`). Read locks can coexist; write locks are exclusive.

### 8.3 Group Membership

**Goal:** Maintain a live registry of which processes are currently active members of a group. Detect when members join or leave (including crashes).

**Algorithm:**

1. Create a persistent znode for the group:
   ```
   /groups/payment-service       (persistent)
   ```

2. When a process starts and wants to join the group, it creates an **ephemeral** znode as a child:
   ```
   Process on host-1 creates: /groups/payment-service/member-host-1  (ephemeral)
   Process on host-2 creates: /groups/payment-service/member-host-2  (ephemeral)
   Process on host-3 creates: /groups/payment-service/member-host-3  (ephemeral)
   ```
   The znode data can contain metadata about the member (hostname, port, capabilities, etc.).

3. Any process that wants to know the current group membership calls:
   ```
   getChildren("/groups/payment-service", watch=true)
   → Returns: [member-host-1, member-host-2, member-host-3]
   ```
   The watch is set on the **parent** znode's children list.

4. When a member crashes:
   - Its session expires → its ephemeral znode is deleted
   - ZooKeeper fires a `NodeChildrenChanged` event to all watchers
   - Watchers call `getChildren()` again to get the updated list and re-register the watch:
     ```
     getChildren("/groups/payment-service", watch=true)
     → Returns: [member-host-1, member-host-3]    // host-2 is gone
     ```

5. When a new member joins:
   - It creates its ephemeral znode
   - `NodeChildrenChanged` fires on all watchers
   - Watchers re-read the children list and see the new member

**Why ephemeral znodes are essential here:** If a process crashes without cleanly leaving the group, its ephemeral znode is automatically removed when the session times out. Without ephemeral znodes, you'd need an external health-checking mechanism to detect and remove dead members — which is exactly the hard problem ZooKeeper solves for you.

**Trade-off:** The detection latency equals the session timeout. If the timeout is 30 seconds, it takes up to 30 seconds to detect a crashed member. Lower timeouts detect failures faster but increase the risk of false positives (a slow GC pause could cause a healthy process to be declared dead).

### 8.4 Configuration Management

**Goal:** Store configuration centrally. All application instances should use the same config and be notified immediately when it changes.

**Algorithm:**

1. Store configuration in a persistent znode:
   ```
   /config/database  →  data: {"host": "db-primary.internal", "port": 5432, "pool": 20}
   ```

2. Each application instance reads the config at startup:
   ```
   getData("/config/database", watch=true)
   ```

3. When an operator updates the config:
   ```
   setData("/config/database", newConfigBytes, version=currentVersion)
   ```
   The version check ensures that if two operators try to update simultaneously, one will fail with `BadVersionException` (optimistic concurrency).

4. All watching instances receive `NodeDataChanged` events, re-read the config, and apply it — no restart needed.

### 8.5 Distributed Barriers

**Goal:** N processes should all wait until every one of them has reached a certain point, then all proceed together.

**Algorithm:**

1. Create a barrier znode: `/barriers/phase-1`
2. Each process creates a child: `/barriers/phase-1/process-N`
3. Each process calls `getChildren("/barriers/phase-1")` and checks if the count equals N.
4. If not, set a watch on the children and wait.
5. When the last process joins, `NodeChildrenChanged` fires on all watchers. They re-check the count, see N children, and proceed.

---

## 9. The Client API

### 9.1 Core Operations

| Operation | Description | Can Set Watch? |
|---|---|:---:|
| `create(path, data, acl, flags)` | Create a znode with given data, ACL, and type flags | No |
| `delete(path, expectedVersion)` | Delete a znode if version matches (-1 = any version) | No |
| `exists(path, watch)` | Check if a znode exists. Returns Stat or null | Yes |
| `getData(path, watch)` | Read znode data and Stat | Yes |
| `setData(path, data, expectedVersion)` | Update znode data if version matches | No |
| `getChildren(path, watch)` | List child znode names | Yes |
| `sync(path)` | Flush the channel between client and leader | No |
| `multi(ops...)` | Execute multiple operations atomically (all or nothing) | No |

### 9.2 Multi-Operations

The `multi()` operation lets you batch multiple operations into a single atomic unit. Either all operations succeed or none do. This is essential for maintaining invariants across multiple znodes:

```
multi(
    create("/app/data/item-1", data1),
    create("/app/index/key-1", pointer1),
    setData("/app/metadata", updatedMeta, version)
)
```

If any operation fails (e.g., the version check on metadata), none of the creates happen either.

### 9.3 ACLs (Access Control Lists)

Every znode has an ACL that controls access. An ACL entry has three parts:

- **Scheme** — The authentication mechanism (`world`, `auth`, `digest`, `ip`, `sasl`)
- **ID** — The identity within that scheme (e.g., a username, an IP range)
- **Permissions** — A combination of:
  - `CREATE` — Can create child znodes
  - `READ` — Can read data and list children
  - `WRITE` — Can set data
  - `DELETE` — Can delete child znodes
  - `ADMIN` — Can set ACLs

ACLs are not inherited. Each znode has its own ACL. This is different from filesystem permissions where children inherit from