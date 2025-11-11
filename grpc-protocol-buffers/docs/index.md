---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: page
title: gRPC and Protocol Buffers
---
# GRPC

## Types of services

- Unary: Only client sends a request message and a server responds with a reply.
- Server streaming: server sends a sequence of messages.
- Client streaming: Client sends a sequence of messages.
- Bidirectional streaming: both send and receive messages. Like websocket.