---
layout: custom_page
title: Miscellaneous
nav_order: Z
---

# Miscellaneous

## Regex

- Most modern regex engines work using state machines and backtracking. Match 1st token, match 2nd token, if doesn't match, move to the next character and start the search again.
- Use the patten `[abc]` instead of `(a|b|c)` when searching for single character search as the latter uses branching and thereby backtracking to match or fail, while the former does not.
    - See the blog [Character Classes or Character Sets - www.regular-expressions.info](https://www.regular-expressions.info/charclass.html) for a good example.
- Some patterns such as `?`,`+`,`*` and the repetition using curly braces are greedy. That is, they always try to match the longest sequences instead of the default eager behaviour (shorter sequences). You can specify to use laziness instead of greed to find the sequences. But sometimes there may be better options than changing to laziness. 
  - In the blog [An Alternative to Laziness - regular-expressions.info](https://www.regular-expressions.info/repeat.html), the author used greedy `+` with negative character class and thereby eliminating the expensive backtracking required in the lazy option.

## GRPC

### Types of services

- Unary: Only client sends a request message and a server responds with a reply.
- Server streaming: server sends a sequence of messages.
- Client streaming: Client sends a sequence of messages.
- Bidirectional streaming: both send and receive messages. Like websocket.

## Big Data and Processing

### Apache Spark

A successor to MapReduce. Like MapReduce we split the data and process it across nodes. Spark persists data mostly in the memory and 
will only uses disk if it overflows. This is unlike mapReduce which will always persist the data on the disk.

Spark uses RDDs, immutable data-structures. Once you create you cannot update. That means after every operation a new RDD is generated.
RDDs are Resilient Distributed Datasets, and as the name indicates, they are distributed.

Each RDD will be split into multiple partitions. If an RDD is lost, if will perform all the transformations again and regenerate the RDD.

Spark shuffles the data across executors by using RPC calls(Netty).
#### Lazy evaluation

Spark doesn't run any transformations unless it encounters an action. An action is something like a reduce operation where data needs 
to be shuffled across executors.

### Apache Flint

This is a successor to Spark that is mainly designed for stream event processing rather than batch processing.