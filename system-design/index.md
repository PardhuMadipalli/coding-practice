# System Design

##### Index
- [Coding practice Home](..)
- [Important points](system-design)
- [Databases](/system-design/databases)

## Points
- When you have to optimize for reads, i.e., R >>> W, then replicate the data so that all app servers can send the response.
- When an application is write-intensive, you should shard the data so that all the data doesn't go to a single server.
- For a batch processing, when you process something at the EOD, do not process everything at a single stretch. Keep them in queue
and process one after the other so that not many instances are needed at only midnight.
- For any process that involves multiple services use a field, some thing like a UUID to identify the operation. Pass this ID
along to all the services using HTTP headers. Then you can filter logs based on this ID. It is similar to opc-request-id in OCI.

## TO-Read

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

