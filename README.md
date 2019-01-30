# Spring Cloud Streams

## Kafka Counting


#### Input topic

```
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test-counting
>hello there this is so much fun
>what else do you care
---
>hello hello hello
---
>hello third time time time
>
```

#### Output topic

```
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-counting-out
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"hello","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"there","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"this","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"is","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"so","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"much","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"fun","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"what","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"else","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"do","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"you","count":1}
{"timestamp":"2019-01-30T00:21:30.000+0000","key":"care","count":1}
---
{"timestamp":"2019-01-30T00:22:00.000+0000","key":"hello","count":3}
---
{"timestamp":"2019-01-30T00:23:30.000+0000","key":"hello","count":1}
{"timestamp":"2019-01-30T00:23:30.000+0000","key":"third","count":1}
{"timestamp":"2019-01-30T00:23:30.000+0000","key":"time","count":3}
```

#### History endpoint

```
$ curl -s http://localhost:8080/counts/hello | jq
[
  {
    "timestamp": "2019-01-30T00:21:00.000+0000",
    "key": "hello",
    "count": 1
  },
  {
    "timestamp": "2019-01-30T00:21:30.000+0000",
    "key": "hello",
    "count": 3
  },
  {
    "timestamp": "2019-01-30T00:23:00.000+0000",
    "key": "hello",
    "count": 1
  }
]
$ curl -s http://localhost:8080/counts/time | jq
[
  {
    "timestamp": "2019-01-30T00:23:00.000+0000",
    "key": "time",
    "count": 3
  }
]
$ curl -s http://localhost:8080/counts/third | jq
[
  {
    "timestamp": "2019-01-30T00:23:00.000+0000",
    "key": "third",
    "count": 1
  }
]

```
