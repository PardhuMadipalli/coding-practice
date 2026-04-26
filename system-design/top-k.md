---
layout: custom_page
title: Top-K Problem
parent: System Design
---

## Top-K Problem (e.g. Trending Hashtags)

The Top-K problem is: given a high-velocity stream of events, find the K most frequent items over a recent time window. Classic example: trending hashtags on Twitter.

### The naive approach and why it breaks

Store every distinct item in a Redis Sorted Set and increment its score on each event:

```
ZINCRBY trending:global 1 "#WorldCup"
ZREVRANGE trending:global 0 9 WITHSCORES   # top 10
```

This works at small scale but has two problems at high scale:
- **Memory**: millions of distinct hashtags per hour, most with count = 1. You're storing the entire long tail.
- **Hot key**: a single sorted set key receives every write — becomes a bottleneck.

### Approach 1: Tumbling window buckets + periodic trim

Use one sorted set per time bucket (e.g. per hour), and periodically evict the long tail:

```
ZINCRBY trending:2024010114 1 "#WorldCup"   # write to current hour bucket
ZREMRANGEBYRANK trending:2024010114 0 -1001  # keep only top 1000 entries
EXPIRE trending:2024010114 86400             # auto-delete after 24h
```

To query the last 3 hours, merge buckets:
```
ZUNIONSTORE trending:result 3 trending:...12 trending:...13 trending:...14
ZREVRANGE trending:result 0 9 WITHSCORES
```

Good for medium-scale apps. The trim keeps memory bounded but risks evicting a hashtag that starts slow and surges — if a hashtag accumulates slowly it may get trimmed before it can climb into the top K.

### Approach 2: Redis Top-K (probabilistic, memory-efficient)

Redis has a native `TOPK` data structure (part of RedisBloom) that maintains only the top K items in **O(1) fixed memory** using a probabilistic heavy-hitters algorithm. Items that fall out of the top K are automatically evicted:

```
TOPK.RESERVE trending_topk 10 50 5 0.9   # keep top 10, width=50, depth=5, decay=0.9
TOPK.ADD trending_topk "#WorldCup" "#Messi" "#AI"
TOPK.LIST trending_topk
→ [("#WorldCup", 450000), ("#Messi", 380000), ...]
```

No trimming needed, no memory growth. This is the cleanest Redis-only solution.

**Suitable scale**: apps with up to ~5,000–10,000 events/second hitting a single Redis node. Think: a mid-size social platform, a news site's trending topics, a gaming leaderboard. If your write rate fits within one Redis node's throughput (~100k ops/sec), this works well.

### Why Redis Top-K is not enough for Twitter scale

Twitter sees ~6,000 tweets/second with multiple hashtags each — that's manageable for Redis in isolation. The deeper problems are:

1. **Single node bottleneck**: all writes go to one `TOPK` key on one Redis node. You can't shard a single Top-K structure across nodes — the algorithm needs to see all events to be accurate.
2. **No windowing**: `TOPK` counts since creation, not over a sliding window. You'd need to reset it periodically, losing continuity.
3. **No decay**: a hashtag that trended yesterday but is dead today still holds a high count. You need time-weighted scoring.
4. **No deduplication**: the same user spamming a hashtag inflates counts. You need HyperLogLog or Bloom filter pre-filtering.

### Production approach at Twitter scale

Use Redis only as the **serving layer**, not the counting layer:

```
[Kafka] → [Flink / Spark Streaming]
               │
               │  windowed counts, decay, dedup
               ▼
         [Redis Sorted Set]   ← only top 500 candidates written here
               │
               ▼
         [Trending API] → [CDN cache, 30s TTL]
```

Flink computes windowed counts (e.g. last 1 hour in 5-minute tumbling buckets) with decay and deduplication. Every 30 seconds it writes only the top 500 candidates to Redis. Redis never sees the long tail — it's purely a low-latency read cache. `ZREVRANGE` on a 500-entry sorted set is sub-millisecond.

### Summary

| Approach | Memory | Windowing | Scale |
|---|---|---|---|
| Naive sorted set | Unbounded | Manual TTL buckets | Small |
| Sorted set + trim | Bounded (manual) | Tumbling buckets | Medium |
| Redis `TOPK` | O(1) fixed | None (reset manually) | Medium (~10k events/s) |
| Stream processor + Redis | Minimal (top N only) | Native sliding/tumbling | Twitter-scale |
