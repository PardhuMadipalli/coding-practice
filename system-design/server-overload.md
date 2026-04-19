---
layout: custom_page
title: Cascading Failures
parent: System Design
---

## Server Overload / Cascading Failures

Based on the Google SRE book chapter: [Addressing Cascading Failures](https://sre.google/sre-book/addressing-cascading-failures/)

A cascading failure is a failure that grows over time via positive feedback. One server fails due to overload → remaining servers get more load → they fail too → domino effect.

---

### How it starts

A service running at capacity loses some servers (crash, maintenance, bad deploy). The remaining servers now handle more load than they can sustain. They slow down, exhaust resources, and either crash or fail health checks. The load balancer redistributes their traffic to the survivors, which then also fail. The whole service goes down.

The critical insight: if a service was healthy at 10,000 QPS and started cascading at 11,000 QPS, dropping to 9,000 QPS won't fix it. With most servers crash-looping, only a fraction are healthy — you might need to drop to 1,000 QPS for the system to stabilize and recover.

---

### Resource exhaustion chain

These feed into each other, making root cause hard to identify during an outage:

1. **CPU exhaustion** → requests slow down → more in-flight requests pile up
2. **Memory pressure** → less RAM for caches → lower cache hit rate → more backend calls
3. **Thread starvation** → health checks can't be served → LB marks server unhealthy
4. **GC death spiral** (Java) — less CPU → slower requests → more RAM used → more GC → even less CPU
5. **Crash loops** — servers crash on overload, restart, get immediately overwhelmed, crash again

---

### Preventing server overload

In rough priority order:

1. **Load test to failure** — know your breaking point. Test what happens when you exceed it. Without this, you're guessing.
2. **Serve degraded results** — return cheaper, lower-quality responses under load (e.g., skip images, use simpler ranking).
3. **Load shedding at the server** — reject requests early and cheaply when overloaded (return HTTP 503). Better to drop some requests than crash and drop all of them.
4. **Rate limiting at higher levels** — at reverse proxies (by IP), at load balancers (global overload), at individual tasks (random fluctuations).
5. **Capacity planning** — provision N+2 clusters so losing capacity doesn't trigger a cascade. But capacity planning alone isn't enough.

---

### Queue management

- Queued requests consume memory and increase latency.
- If queue is 10x thread pool and each request takes 100ms, a queued request waits 1.1 seconds — most of it just sitting in the queue.
- For steady traffic: keep queues small (≤50% of thread pool). Reject early when saturated.
- Consider **LIFO** instead of FIFO — old queued requests are likely stale (user already refreshed). Process the newest ones first.

---

### Retries amplify failures

A naive retry policy can turn a small overload into a full cascade:

1. Backend at 10,000 QPS limit, frontend sends 10,100 QPS → 100 rejected.
2. Those 100 are retried → backend now sees 10,200 QPS → 200 rejected.
3. Retries grow: 100 → 200 → 300 → ... → backend melts down.
4. Backend crashes → load redistributes to other backends → they melt down too.

**Retry best practices:**
- Always use **randomized exponential backoff with jitter** (see [System Design notes on retries](./index.md))
- Limit retries per request (don't retry indefinitely)
- Use a **server-wide retry budget** (e.g., max 60 retries/minute per process — if exceeded, just fail)
- **Don't retry at multiple layers** — if DB, backend, frontend, and client each retry 3 times, one user action creates 4³ = 64 DB requests
- Use clear response codes — separate retriable errors from permanent errors. Don't retry malformed requests or permanent failures.
- Return a specific "overloaded" status so clients back off

---

### Deadlines and latency

- **Always set deadlines** on RPCs. No deadline = resources consumed indefinitely.
- **Cancel work past the deadline** — if a request sat in queue for 11s but the client's deadline was 10s, don't process it. The client already gave up.
- **Propagate deadlines down the stack** — if Server A sets a 30s deadline and takes 7s, the RPC to Server B should have a 23s deadline. Without this, downstream servers waste work on already-expired requests.
- **Bimodal latency is dangerous** — if 5% of requests hang until deadline (say 100s) while 95% complete in 100ms, those 5% consume 50x more threads. A 100s deadline with 100ms mean latency can exhaust all threads from just 5% of traffic.

---

### Cold cache / slow startup

Freshly started servers are slower — JIT compilation, empty caches, connection setup. If the service can't handle requests with a cold cache, a mass restart can trigger a cascade.

**Mitigations:**
- Distinguish **latency cache** (nice to have, service works without it) from **capacity cache** (service can't handle load without it). Capacity caches are hard dependencies.
- Slowly ramp traffic to new/restarted servers — let caches warm before sending full load.
- Consider external caches (e.g., memcached/[Redis](./redis.md)) so cache survives server restarts.

---

### Triggers

Common things that kick off a cascade:
- **Process crashes** — even a few can tip a service at the edge of capacity
- **Bad deploys** — new binary or config change under load
- **Organic growth** — traffic grew but capacity didn't
- **Cluster maintenance** — losing a cluster's capacity
- **Traffic shifts** — load balancer moves traffic, changing the load profile

---

### Recovery playbook

Once a cascade is happening:

1. **Add capacity** — if you have idle resources, spin up more tasks
2. **Drop traffic aggressively** — if crash-looping, allow only ~1% through, let servers stabilize, then slowly ramp back up
3. **Disable health-check restarts** — if half the servers are starting up and the other half are being killed for failing health checks, temporarily stop the killing
4. **Restart wedged servers** — if GC death spiral, deadlocks, or leaked threads. But canary first, go slow.
5. **Serve degraded results** — drop non-critical features (images, suggestions, analytics)
6. **Eliminate non-critical load** — turn off index updates, stats gathering, data copies
7. **Block bad queries** — if specific queries are causing crashes (query of death), block them

**Before recovering**: fix the root cause first. If you ramp traffic back up without fixing the trigger, the cascade restarts immediately.

---

### Key takeaways

- Something must give when a system is overloaded. It's better to drop some requests than crash and drop all of them.
- Retries, load shifting, killing unhealthy servers, and caching all improve the steady state but can make cascading failures worse if not designed carefully.
- Load test until failure. Know your breaking point. Test recovery.
- Design servers to be disposable — push state out to shared storage.
