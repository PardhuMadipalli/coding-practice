---
layout: custom_page
title: Database Locks
parent: Databases
---
# Database Locks

**Table of Contents**
- Table of contents
{:toc}

## Managing locks from client side code on Amazon DDB

- Read the blog at [Building Distributed Locks with the DynamoDB Lock Client](https://aws.amazon.com/blogs/database/building-distributed-locks-with-the-dynamodb-lock-client/).
- The same logic can be used not just in DDB, but in any database.
- The idea here is you maintain another table that specifies which _key_, _lease duration_, _host owner_. _lease start time_ etc of the lock. Each client, when updating any row will acquire the lock first, perform operations, then either heart beat if required or release the lock. Other clients should respect the same lock mechanism.

