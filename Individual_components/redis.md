---
layout: custom_page
title: Redis
parent: Miscellaneous
---

## Redis

Redis is an in-memory data store. While most commonly used as a cache, it supports several data structures and features that make it useful across many system design scenarios.

---

### Caching

The most common use case. Store frequently read data in Redis so your database doesn't get hammered on every request.

- Data is stored as key-value pairs in memory — reads are sub-millisecond.
- Set a TTL (time-to-live) on keys so stale data expires automatically.
- Common patterns: cache-aside (app checks Redis first, falls back to DB on miss), write-through (write to cache and DB together).
- Example: caching user profile data, product catalog, rendered HTML fragments.

**Watch out for**: cache invalidation — when the underlying DB data changes, you need to evict or update the cache entry.

---

### Pub/Sub (Publish / Subscribe)

Redis has a built-in messaging system where publishers send messages to a channel and all current subscribers receive them instantly.

```
PUBLISH notifications "user:42 liked your post"
SUBSCRIBE notifications
```

- Fire-and-forget — Redis does not persist the message. If a subscriber is offline, it misses the message.
- Very useful for broadcasting events across multiple app server instances (e.g., WebSocket scaling — see [Networking Protocols](./networking-protocols.md)).
- Example: chat apps, live notifications, invalidating caches across multiple servers.

**Watch out for**: no message durability. If you need guaranteed delivery or replay, use Redis Streams or a dedicated broker like Kafka.

---

### Sorted Sets (Leaderboards)

A sorted set stores members with an associated score. Redis keeps them sorted by score automatically.

```
ZADD leaderboard 9500 "alice"
ZADD leaderboard 8200 "bob"
ZRANGE leaderboard 0 9 REV WITHSCORES   # top 10
```

- O(log N) inserts and lookups.
- Trivial to get top-N, rank of a user, or users within a score range.
- Example: game leaderboards, trending posts ranked by upvotes, search result ranking.

---

### Streams

Redis Streams is a persistent, append-only log — think of it as a lightweight Kafka.

- Producers append messages to a stream: `XADD mystream * event "order_placed" orderId "123"`
- Consumers read from a position and can form consumer groups for parallel processing.
- Unlike pub/sub, messages are **persisted** and can be replayed.
- Example: event sourcing, activity feeds, audit logs, async task queues.

**When to prefer Kafka over Redis Streams**: very high throughput, long retention, complex consumer group semantics, or multi-datacenter replication.

---

### Distributed Locks

When multiple app servers need to coordinate access to a shared resource, Redis can act as a distributed lock manager.

- Use `SET key value NX PX 30000` — sets the key only if it doesn't exist (`NX`), with a 30-second expiry (`PX`).
- The server that sets the key holds the lock. Others poll or retry.
- Expiry ensures the lock is released even if the holder crashes.
- The standard algorithm for this is called **Redlock** (for multi-node Redis setups).
- Example: ensuring only one instance runs a scheduled job, preventing double-processing of a payment.

**Watch out for**: clock drift and network partitions can cause edge cases with Redlock. For critical sections, evaluate whether a stronger coordination service (like ZooKeeper) is needed.

---

### Rate Limiting

Redis counters can track how many requests a client has made within a time window.

- Simple approach: `INCR user:42:requests` + `EXPIRE user:42:requests 60` — increment a counter and set it to expire in 60 seconds.
- If the counter exceeds your threshold, reject the request.
- More precise: use a sliding window with a sorted set — store timestamps of each request as members, prune old ones, count remaining.
- Example: API rate limiting (100 requests/minute per user), login attempt throttling.

---

### Session Storage

HTTP is stateless, so session data (logged-in user info, cart contents) needs to live somewhere shared across all app servers.

- Store session as a hash: `HSET session:abc123 userId 42 role admin`
- Set a TTL to auto-expire inactive sessions.
- Any app server can look up the session by ID — no sticky sessions needed.
- Example: web app login sessions, shopping cart state.

---

## Summary

| Use Case | Redis Feature | Key Benefit |
|---|---|---|
| Caching | Key-value + TTL | Sub-millisecond reads, reduces DB load |
| Pub/Sub | PUBLISH / SUBSCRIBE | Real-time broadcast across servers |
| Leaderboards | Sorted Sets | Auto-sorted, O(log N) updates |
| Event streaming | Streams | Persistent, replayable message log |
| Distributed locks | SET NX PX | Coordination across app instances |
| Rate limiting | INCR + EXPIRE | Simple sliding/fixed window counters |
| Session storage | Hash + TTL | Stateless app servers, shared sessions |
