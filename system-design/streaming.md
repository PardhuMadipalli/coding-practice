---
layout: custom_page
title: Streaming
parent: System Design
---

## Streaming

Covers two distinct domains: **video streaming** (delivering media to viewers) and **real-time communication** (interactive audio/video like Zoom). They share some concepts but have very different architectural goals.

---

## Encoding and Transcoding

### Encoding
Converting raw, uncompressed video into a compressed digital format using a codec.

- Raw camera footage can be 100+ GB/minute. Encoded H.264 might be 50–100 MB/minute.
- Common codecs: H.264, H.265/HEVC, VP9, AV1 (each newer one is more efficient but more CPU-intensive to decode).
- Happens once at the source (camera, screen recorder, upload).

### Transcoding
Converting an already-encoded video into a different format, resolution, or bitrate.

- Decode → re-encode into multiple output versions.
- Used to create the multiple quality levels needed for adaptive bitrate streaming.
- CPU-intensive. VOD platforms can afford to do it offline. Live streaming must do it in real-time, which adds latency.

```
Original 4K upload → Transcoder → 1080p, 720p, 480p, 360p versions
```

---

## Video on Demand (VOD)

How YouTube and Prime Video serve pre-recorded content.

### Architecture
1. **Upload & Ingest** — user uploads raw video.
2. **Transcoding** — platform transcodes it into 5–10+ quality levels offline (can take minutes to hours, no rush).
3. **Storage** — all versions stored in object storage (e.g., S3).
4. **CDN distribution** — videos are cached on edge servers globally close to viewers.
5. **Playback** — client fetches a manifest file listing available quality levels, then requests segments.

### Adaptive Bitrate Streaming (ABR)
Video is split into small segments (2–10 seconds each). The player monitors bandwidth and switches quality levels between segments seamlessly.

- **HLS** (HTTP Live Streaming) — Apple's protocol. Manifest is `.m3u8`. Widely supported.
- **DASH** (Dynamic Adaptive Streaming over HTTP) — open standard. Manifest is `.mpd`.
- Both run over HTTP/TCP — reliable delivery, works through firewalls and CDNs.

### Why VOD is cheap to scale
- Content is static and highly cacheable. CDN hit rates are 90%+.
- Popular videos are served entirely from edge — origin servers rarely touched.
- Transcoding cost is one-time per video.

---

## Live Streaming (Broadcast)

How YouTube Live and Prime Video live events work. One broadcaster → millions of passive viewers.

### Architecture
1. **Ingest** — broadcaster sends stream to ingest servers via RTMP (TCP) or SRT (UDP).
2. **Real-time transcoding** — stream is transcoded into multiple quality levels as it arrives (adds 2–10s latency).
3. **Packaging** — transcoded stream is packaged into HLS/DASH segments continuously.
4. **CDN** — segments are pushed to edge servers for delivery.
5. **Viewers** — same ABR playback as VOD, but segments are only a few seconds old.

### Latency
- Standard HLS/DASH live: **6–30 seconds** of delay. Acceptable for watching sports or concerts.
- Low-latency HLS (LL-HLS) / Low-latency DASH: **2–5 seconds**.
- Ultra-low latency (e.g., Prime Video's Sye, WebRTC-based): **sub-3 seconds**, uses UDP.

### TCP vs UDP for live streaming
- **TCP (HLS/DASH)**: reliable, CDN-friendly, but higher latency due to retransmissions.
- **UDP (SRT, WebRTC)**: lower latency, some packet loss acceptable, harder to cache/distribute.

For broadcast-style live events, TCP dominates because reliability and massive scale matter more than sub-second latency.

---

## Real-Time Video Conferencing (Zoom model)

Interactive, bidirectional, low-latency. Every participant sends and receives simultaneously.

### Why not P2P for group calls?
Pure peer-to-peer doesn't scale. In a 10-person call, each participant would need to upload 9 streams and download 9 streams. With N participants, each sends N-1 streams — bandwidth grows as O(N²).

### SFU (Selective Forwarding Unit)
The standard architecture for video conferencing at scale.

- Each participant sends their stream **once** to the SFU server.
- The SFU **forwards** (not transcodes) the appropriate streams to each participant.
- No re-encoding on the server — just routing. This keeps latency minimal.
- The SFU can selectively forward lower-quality layers to participants with poor bandwidth.

### MCU (Multipoint Control Unit)
An older alternative where the server mixes all streams into one composite video and sends it to each participant.

- Simpler for the client (receives one stream).
- Much higher server CPU cost (must transcode everything).
- Higher latency due to mixing/transcoding.
- Rarely used now — SFU is preferred.

### How Zoom achieves low latency
- **UDP transport** — no waiting for retransmissions. Packet loss causes brief glitches, not stalls.
- **SVC (Scalable Video Coding)** — one stream with multiple embedded quality layers. SFU forwards the right layers per recipient without separate streams.
- **No server-side transcoding** — SFU only routes packets.
- **Distributed data centers** — traffic routed to nearest data center to minimize hops.
- **RTCP feedback** — continuously monitors jitter, packet loss, and adjusts bitrate dynamically.

Typical end-to-end latency: **100–300ms**.

### P2P for 1-on-1 calls
Zoom uses direct P2P (via WebRTC) for 1-on-1 calls on the same local network to minimize latency. Falls back to SFU routing otherwise.

See [Networking Protocols — WebRTC](./networking-protocols.md#webrtc-web-real-time-communication) for how WebRTC peer connections are established.

---

## Comparison

| | VOD | Live Streaming | Video Conferencing |
|---|---|---|---|
| Direction | One-to-many | One-to-many | Many-to-many |
| Latency | Doesn't matter | 6–30s (or lower) | <300ms required |
| Protocol | HTTP/TCP (HLS, DASH) | HTTP/TCP or UDP | UDP (WebRTC/RTP) |
| Transcoding | Offline, once | Real-time | None (SFU forwards) |
| CDN cacheable | Highly cacheable | Partially | Not cacheable |
| Scale | Millions easily | Millions (with CDN) | Hundreds–thousands per SFU |
| Server role | Storage + CDN | Ingest + transcode + CDN | SFU routing |

---

## Related
- [Networking Protocols](./networking-protocols.md) — WebSockets, SSE, WebRTC
- [Redis](./redis.md) — pub/sub for real-time messaging, used in WebSocket scaling
- [System Design Notes](./index.md) — caching, CDNs, retries
