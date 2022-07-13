# Apache Spark

A successor to MapReduce. Like MapReduce we split the data and process it across nodes. Spark persists data mostly in the memory and 
will only uses disk if it overflows. This is unlike mapReduce which will always persist the data on the disk.

Spark uses RDDs, immutable data-structures. Once you create you cannot update. That means after every operation a new RDD is generated.
RDDs are Resilient Distributed Datasets, and as the name indicates, they are distributed.

Each RDD will be split into multiple partitions. If an RDD is lost, if will perform all the transformations again and regenerate the RDD.

Spark shuffles the data across executors by using RPC calls(Netty).
### Lazy evaluation

Spark doesn't run any transformations unless it encounters an action. An action is something like a reduce operation where data needs 
to be shuffled across executors.