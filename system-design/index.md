---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: page
title: System Design
nav_order: 2
---

# System Design
<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [Points](#points)
- [To-Read](#to-read)
- [Miscellaneous](#miscellaneous)
  - [Entity design](#entity-design)
  - [Consistent hashing](#consistent-hashing)
  - [Trie data structure](#trie-data-structure)
- [Caching](#caching)
  - [Cache eviction schemes](#cache-eviction-schemes)
- [Good designs](#good-designs)
  - [Data structure designs](#data-structure-designs)
    - [Limit order book design](#limit-order-book-design)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Points
- When you have to optimize for reads, i.e., `R >>> W`, then replicate the data so that all app servers can send the response.
- When an application is write-intensive, you should shard the data so that all the data doesn't go to a single server.
- For a batch processing, when you process something at the EOD, do not process everything at a single stretch. Keep them in queue
and process one after the other so that not many instances are needed at only midnight.
- For any process that involves multiple services, use a field, something like a UUID to identify the operation. Pass this ID
along to all the services using HTTP headers. Then you can filter logs based on this ID. It is similar to opc-request-id in OCI.
- Use **retries** wherever we can for failed requests. It's better to implement these using **exponential backoff with jitter** mechanism. 
Each further retry is delayed by an exponential delay with a final maximum delay. We have to jitter because, we may retry all the requests that we have at the same time which can increase load on the upstream server at the same time. Adding a jitter will help prevent it.

## To-Read

- Graph databases used in social media apps such as Facebook.
- Horizontal and vertical sharding. When to use what.
- Indexing
- CDNs
- Caching levels: Frontend, backend and database
- Social media design board [link](https://whimsical.com/scaler-social-media-platform-Mmg5KJTJ7qQ7qU4HnBrU9Z)
- DAGs, used for batch processing

## Miscellaneous
### Entity design
- Use a join table when you have many-to-many relation. For example students and courses. A student can take many courses and one course 
can be taken by many students. That means we will have another table with an ID field that is a composite of student_id and course_id.
Fields: ID, student_id(FK), course_id(FK), Marks. Here marks indicate the score achieved by that student in that particular course.

### Consistent hashing
- Used for minimizing data-transfer in stateful machines when data has to be transferred from one machine to another.
- We want to minimize number of transfers when a new machine gets added or removed.
- This is important

### Trie data structure
- Used for searching and typeahead.
- This needs a lot of space if using for 26 characters alphabet.
- Better to use for binary characters.

## Caching

- **Read through**: Load data into cache only if there is a cache miss. Staleness is the problem until the cache entry reaches its expiry time.
- **Write through**: First write into the cache, then write into the database and return the result to the user. Writes are slow, but reads are super-fast.
- **Write behind**: First write into cache and return to the user. Asynchronously, database will be updated with the value written in the cache.
- **Refresh ahead**: Refresh the entries in the cache every few seconds/certain time period. So data staleness won't be there for long.

### Cache eviction schemes
- **Least Recently Used**: This can directly be implemented by [LinkedHashMap](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html#LinkedHashMap-int-float-boolean-) in java.

## Good designs

### Data structure designs

#### Limit order book design
- [Design link](https://web.archive.org/web/20110219163448/http://howtohft.wordpress.com/2011/02/15/how-to-build-a-fast-limit-order-book/)
- They used BST, hashmaps and linkedLists all together to design the order book.