# Redis常用命令

帮助文档（HELP命令）  
HELP @{group} | 查看某个分组所有命令，如 help @string。   
HELP {command} |  查看某个命令详情，如 help get。

## 0 generic
| 命令 | 说明 |
| ---- | --- |
| SELECT index | Change the selected database for the current connection |
| KEYS pattern | 查找所有符合匹配模式的key |
| TTL key | 获取key的生存时间 |
| DEL key [key ...] | Delete a key |
| RENAME key newkey | Rename a key |
| RENAMENX key newkey | Rename a key, only if the new key does not exist |


## 1 STRING
### 1.1 create
| 命令 | 说明 |
| ---- | --- |
| SET key value [EX seconds] [PX milliseconds] [NX &#124; XX] | Set the string value of a key（增/改） |
| SETNX key value | Set the value of a key, only if the key does not exist |
| SETEX key seconds value | Set the value and expiration of a key |

### 1.2 delete
| 命令 | 说明 |
| ---- | --- |
| DEL key [key ...] | Delete a key |

### 1.3 update
| 命令 | 说明 |
| ---- | --- |
| SETBIT key offset value | Sets or clears the bit at offset in the string value stored at key |
| SETRANGE key offset value | Overwrite part of a string at key starting at the specified offset |

### 1.4 read
| 命令 | 说明 |
| ---- | --- |
| GET key | Get the value of a key |
| GETBIT key offset | Returns the bit value at offset in the string value stored at key |
| GETRANGE key start end | Get a substring of the string stored at a key |
| GETSET key value | Set the string value of a key and return its old value |


## 2 LIST
### 2.1 create
| 命令 | 说明 |
| ---- | --- |
| LPUSH key value [value ...] | Prepend one or multiple values to a list |
| RPUSH key value [value ...] | Prepend one or multiple values to a list |
| LPUSHX key value | Prepend a value to a list, only if the list exists |
| RPUSHX key value | Prepend a value to a list, only if the list exists |
| LINSERT key BEFORE &#124; AFTER pivot value | Insert an element before or after another element in a list |

### 2.2 delete
| 命令 | 说明 |
| ---- | --- |
| LREM key count value | Remove elements from a list |

### 2.3 update
| 命令 | 说明 |
| ---- | --- |
| LSET key index value | Set the value of an element in a list by its index |
| LTRIM key start stop | Trim a list to the specified range（截取保留指定范围元素，其余元素删除） |

### 2.4 read
| 命令 | 说明 |
| ---- | --- |
| LLEN key | Get the length of a list |
| LPOP key | Remove and get the first element in a list |
| RPOP key | Remove and get the first element in a list |
| LINDEX key index | Get an element from a list by its index |
| LRANGE key start stop | Get a range of elements from a list |


## 3 HASH
### 3.1 create
| 命令 | 说明 |
| ---- | --- |
| HSET key field value | Set the string value of a hash field |
| HMSET key field value [field value ...] | Set multiple hash fields to multiple values |
| HSETNX key field value | Set the value of a hash field, only if the field does not exist |

### 3.2 delete
| 命令 | 说明 |
| ---- | --- |
| HDEL key field [field ...] | Delete one or more hash fields |

### 3.3 update
| 命令 | 说明 |
| ---- | --- |
| HINCRBY key field increment | Increment the integer value of a hash field by the given number |
| HINCRBYFLOAT key field increment | Increment the float value of a hash field by the given amount |

### 3.4 read
| 命令 | 说明 |
| ---- | --- |
| HGET key field | Get the value of a hash field |
| HMGET key field [field ...] | Get the values of all the given hash fields |
| HKEYS key | Get all the fields in a hash |
| HVALS key | Get all the values in a hash |
| HGETALL key | Get all the fields and values in a hash |
| HEXISTS key field | Determine if a hash field exists |
| HLEN key | Get the number of fields in a hash |
| HSTRLEN key field | Get the length of the value of a hash field |
| HSCAN key cursor [MATCH pattern] [COUNT count] | Incrementally iterate hash fields and associated values |


## 4 SET
### 4.1 create
| 命令 | 说明 |
| ---- | --- |
| SADD key member [member ...] | Add one or more members to a set |
| SMOVE source destination member | Move a member from one set to another (增&删) |

### 4.2 delete
| 命令 | 说明 |
| ---- | --- |
| SREM key member [member ...] | Remove one or more members from a set |
| SPOP key [count] | Remove and return one or multiple random members from a set |

### 4.3 update
| 命令 | 说明 |
| ---- | --- |
| SDIFF key [key ...] | Subtract multiple sets |

### 4.4 read
| 命令 | 说明 |
| ---- | --- |
| SISMEMBER key member | Determine if a given value is a member of a set |
| SCARD key | Get the number of members in a set |
| SMEMBERS key | Get all the members in a set |
| SRANDMEMBER key [count] | Get one or multiple random members from a set |
| SSCAN key cursor [MATCH pattern] [COUNT count] | Incrementally iterate Set elements |

### 4.5 Intersect交集
| 命令 | 说明 |
| ---- | --- |
| SINTER key [key ...] | Intersect multiple sets |
| SINTERSTORE destination key [key ...] | Intersect multiple sets and store the resulting set in a key |

### 4.6 UNION并集
| 命令 | 说明 |
| ---- | --- |
| SUNION key [key ...] | Add multiple sets |
| SUNIONSTORE destination key [key ...] | Add multiple sets and store the resulting set in a key |

### 4.7 DIFF差集
| 命令 | 说明 |
| ---- | --- |
| SDIFF key [key ...] | Subtract multiple sets |
| SDIFFSTORE destination key [key ...] | Subtract multiple sets and store the resulting set in a key |


## 5 ZSET
### 5.1 create
| 命令 | 说明 |
| ---- | --- |
| ZADD key [NX &#124; XX] [CH] [INCR] score member [score member ...] | Add one or more members to a sorted set, or update its score if it already exists |

### 5.2 delete
| 命令 | 说明 |
| ---- | --- |
| ZREM key member [member ...] | Remove one or more members from a sorted set |
| ZREMRANGEBYRANK key start stop | Remove all members in a sorted set within the given indexes |
| ZREMRANGEBYSCORE key min max | Remove all members in a sorted set within the given scores |
| ZREMRANGEBYLEX key min max | Remove all members in a sorted set between the given lexicographical range |

### 5.3 update
| 命令 | 说明 |
| ---- | --- |
| ZINCRBY key increment member | Increment the score of a member in a sorted set |

### 5.4 read
| 命令 | 说明 |
| ---- | --- |
| ZCARD key | Get the number of members in a sorted set |
| ZSCORE key member | Get the score associated with the given member in a sorted set |
| ZCOUNT key min max | Count the members in a sorted set with scores within the given values |
| ZLEXCOUNT key min max | Count the number of members in a sorted set between a given lexicographical range |
| ZRANK key member | Determine the index of a member in a sorted set |
| ZREVRANK key member | Determine the index of a member in a sorted set, with scores ordered from high to low |
| ZRANGE key start stop [WITHSCORES] | Return a range of members in a sorted set, by index |
| ZREVRANGE key start stop [WITHSCORES] | Return a range of members in a sorted set, by index, with scores ordered from high to low |
| ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count] | Return a range of members in a sorted set, by score |
| ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count] | Return a range of members in a sorted set, by score, with scores ordered from high to low |
| ZRANGEBYLEX key min max [LIMIT offset count] | Return a range of members in a sorted set, by lexicographical range |
| ZREVRANGEBYLEX key max min [LIMIT offset count] | Return a range of members in a sorted set, by lexicographical range, ordered from higher to lower strings. |
| ZSCAN key cursor [MATCH pattern] [COUNT count] | Incrementally iterate sorted sets elements and associated scores |

### 5.5 Intersect交集
| 命令 | 说明 |
| ---- | --- |
| ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM &#124; MIN  &#124;  MAX] | Intersect multiple sorted sets and store the resulting sorted set in a new key |

### 5.6 UNION并集
| 命令 | 说明 |
| ---- | --- |
| ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM &#124; MIN &#124; MAX] | Add multiple sorted sets and store the resulting sorted set in a new key |


## 6 TRANSACTIONS
| 命令 | 说明 |
| ---- | --- |
| MULTI | Mark the start of a transaction block |
| EXEC | Execute all commands issued after MULTI |
| DISCARD | Discard all commands issued after MULTI |
| WATCH key [key ...] | Watch the given keys to determine execution of the MULTI/EXEC block |
| UNWATCH | Forget about all watched keys |
