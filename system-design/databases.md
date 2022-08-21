# Databases

##### Index
- [Coding practice Home](..)
- [System Design Home](.)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [Important points](#important-points)
- [Database properties](#database-properties)
- [Database types](#database-types)
  - [SQL relational databases](#sql-relational-databases)
  - [Non relational databases](#non-relational-databases)
    - [Key value stores](#key-value-stores)
    - [Document databases](#document-databases)
    - [Columnar databases](#columnar-databases)
    - [Data-warehouses](#data-warehouses)
- [Popular databases](#popular-databases)
    - [MongoDB](#mongodb)
    - [Cassandra](#cassandra)
- [CAP theorem in distributed databases](#cap-theorem-in-distributed-databases)
  - [Databases that offer various combinations of C,A & P](#databases-that-offer-various-combinations-of-ca--p)
    - [C and P](#c-and-p)
    - [A and P](#a-and-p)
    - [C and A](#c-and-a)
- [Indexing](#indexing)
  - [Categories of indexing](#categories-of-indexing)
    - [Dense Indexing](#dense-indexing)
    - [Sparse indexing](#sparse-indexing)
  - [Types of indexing](#types-of-indexing)
    - [Primary](#primary)
    - [Clustered](#clustered)
    - [Secondary](#secondary)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Important points

- When you really want ACID properties for some tables, but if they are not needed after sometime, 
then you can use an RDBMS only for specific time period and then dump the data into Cassandra or some other DB.

## Database properties

*Atomicity*: Entire operation must be done in a single transaction. A good example is debiting money from 
senders account and crediting it into receiver's account. Both should be done or not to be done. Doing only one operation 
is not allowed.

*Consistency*: If two requests are trying to get the same information, both of them should receive the same information.
Simply, all users should see the value written by latest update transaction.

*Isolation*: Two different users operating on a particular table should be isolated. Each transaction is independent of the other.

## Database types

### SQL relational databases
- These are to be when there are a lot of relations among different tables.
- These databases generally guarantee ACID properties.
- Consistency is very hard to achieve in a distributed database. 
  - You have to lock nodes other than master node. Then replicate and release nodes.
  - If all the nodes can't be updated with the latest value, then the transaction will be rolled back.
- SQL DBs are very hard to scale horizontally, but vertical scaling is easy.
- For fixed schemas, SQL DBs are easy to manage.
- We can shard the database based on primary key. For example, PK value starting with A-M can be in the first and N-Z can be in the second node.
  - But it is very hard to do joins because some data will be one table and other data will be other table.
- Here to scale, we follow a scale-up strategy, ie increase the CPU, memory and storage of that single node.
- Very difficult to maintain zero downtime, because to upgrade, you may have to restart the database. 
  - Or else, you have to point your application to a replicated database, then upgrade the first one and then point back to the primary node.

### Non relational databases
- They follow a scale-out strategy. So we just add a new node. Very easy to upgrade or scale-out/in.  

#### Key value stores
- They are used to save application configuration data.
- These are mostly in-memory databases and are widely used for caching. Examples: Redis & DynamoDB.

#### Document databases
- No fixed schema. They can be used where data for various objects do not follow any schema. Saving details of items available
in an ecommerce website. Different items have different fields.
- They support high load of reads and writes.
- They are used when you need all the details related to an object. No expensive joins are needed to find all the details of a single object.
- ACID transactions MAY not be guaranteed here.

#### Columnar databases
- Use for analytic workloads. Cassandra is one such database.

#### Data-warehouses
- Generally used to run analytics on large sets of data. Not used for realtime analytics. Example: Apache Hive

## Popular databases

#### MongoDB
- Guarantees ACID. One of the very few NoSQL databases that do this.
- We can update multiple documents in a single transaction, similarly to how we update multiple rows in a relational DB.
- It offers synchronized replication also.

#### Cassandra
- Does not guarantee ACID.
- It's a columnar database.
- Use this database when we have only a few queries to run on a large set of data. See [codeKarle system design video](https://youtu.be/EpASu_1dUdE?t=1810)
- Each column cell consists of name, value and timestamp.
- Updating a record is nothing but adding a new column with updated value and new timestamp. Old timestamp can be deleted by Cassandra after some time.
- It has multiple shards and each shard can have multiple replicas.
- It does NOT have MASTER-SLAVE architecture, but has peer-to-peer architecture. So as soon as one of the replicas receives the request, it executes it.
- Very fast writes and sufficiently fast reads.

## CAP theorem in distributed databases
- Only one out of consistency, availability and partition failure tolerance is guaranteed.
- In most distributed databases, network partition failures will happen. So you must try to handle that.
- It means that we have to choose between consistency and availability.
- MongoDB and Kafka provide C and P.
- Cassandra focuses on A and P. But it offers an option to tune consistency. We can choose how many replica nodes to say OK before marking
the operation(read/write) as successful.


### Databases that offer various combinations of C,A & P
#### C and P
- MongoDB
- Redis
- Memcache
- BigTable
- HBase

#### A and P
- Cassandra
- Dynamo
- CouchDB

#### C and A
- RDBMS (such as Oracle)

## Indexing

### Categories of indexing

#### Dense Indexing
- All keys will be mapped to a certain block on the disk.
- Problem is this index table itself might be huge depending on the number of rows we have.

#### Sparse indexing
- Table data must be sorted the column where we are indexing.

### Types of indexing

#### Primary
Created on the *ordered* primary key of a table. Use preferably Sparse indexing.

#### Clustered
Created on the *ordered* non-key field of the table. Use preferably Sparse indexing.

#### Secondary
Created on the *unordered* primary key of the table. Here only dense index is possible because the table is not ordered.
