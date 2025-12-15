---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: custom_page
title: System Design
nav_order: 2
---

## Points
- When you have to optimize for reads, i.e., `R >>> W`, then replicate the data so that all app servers can send the response.
- When an application is write-intensive, you should shard the data so that all the data doesn't go to a single server.
- For a batch processing, when you process something at the EOD, do not process everything at a single stretch. Keep them in queue
and process one after the other so that not many instances are needed at only midnight.
- For any process that involves multiple services, use a field, something like a UUID to identify the operation. Pass this ID along to all the services using HTTP headers. Then you can filter logs based on this ID. It is similar to opc-request-id in OCI.
- Use **retries** wherever we can for failed requests. It's better to implement these using **exponential backoff with jitter** mechanism. 
  - Each further retry is delayed by an exponential delay with a final maximum delay. We have to jitter because, we may retry all the requests that we have at the same time which can increase load on the upstream server at the same time. Adding a jitter will help prevent it.
- Use **cells** when there is very high scale. You can split each region also into cells. More about cells in this [AWS re:Invent video](https://youtu.be/YZUNNzLDWb8?si=_eTm0vxNBFbhr9zL&t=2794)
> Cells are defined as taking a large system (e.g., one big DynamoDB for an entire region) and creating multiple independent implementations, where each unit is called a cell. This allows for isolation (42:00, 42:14).
    
    > The video discusses two approaches to cell routing:

    > Cell Router (Standard Approach): A server-side fleet acts as a cell router, directing customer requests to their specific cell (42:57). This approach is simple, highly resilient, and allows for updating cell assignments without customer cooperation. However, the cell router itself and its DNS are still regional points of failure (43:20, 43:43).

    > Independent DNS Name per Cell (DynamoDB's Path): Every cell gets its own independent top-level DNS name and DNS management system. Customers will have their own DNS name (e.g., account id ddbre region.aiws API to AWS) that CNAMEs to their assigned cell (44:22, 44:39). This path results in less shared fate but is more complex as it requires customers to update their SDKs (45:07, 45:23).

    >    The video emphasizes that cells are a great way to reduce regional impact, but it's important to think through why you're implementing them due to the ripple effects on architecture (46:22).

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