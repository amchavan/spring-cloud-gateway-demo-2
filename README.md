# Spring Cloud Gateway Demo 2

This project demonstrates some of the features of Spring Cloud Gateway:
load balancing, circuit breaking and rate limiting.

It makes use of four components, three services
([book service](book-service/README.md), 
[meteo service](meteo-service/README.md), 
and 
[time service](time-service/README.md)) 
plus a 
[gateway](gateway/README.md). 
Any HTTP client can be used to exercise it, 
we'll be using `curl`.

## Getting started

### Prerequisites

1. This project requires a 
[Redis server](https://redis.io/)
(standard, out of the box installation)
is up and running.

2. For convenience we're using `ttab -wd <directpry-pathname> <cmd>` in the following instructions. 
If you're not on a Mac or you cannot install _ttab_ (`npm install -g ttab`) 
you can instead open new terminal windows and `cd` manually.

### Building and launching the system

After cloning this project you can `cd` to its top-level directory,
then build and launch the gateway and the services. Note that the book
service is launched twice to demonstrate load balancing.
```bash
ttab -wd gateway "mvn spring-boot:run"
ttab -wd book-service "mvn spring-boot:run"
ttab -wd book-service "mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=10001'"
ttab -wd meteo-service "mvn spring-boot:run"
ttab -wd time-service "mvn spring-boot:run"
```

The gateway will be launched on port 9999, two book services on 
ports 10000 and 10001, the meteo service on port 10002 and the time
service on port 10010

## Basic routing

Try the basic routing functions:

* `curl http://localhost:9999/book/22` → http://localhost:10000/book-service/book/22
 
* `curl http://localhost:9999/next-meteo` → http://localhost:10002/meteo-service/next-point
  
* `curl http://localhost:9999/current-datetime`  → http://localhost:10010/time-service/now

## Load balancing

HTTP requests to `book/{id}` are routed to two concurrent _book-service_ implementations, 
running on ports 10000 and 10001. Distribution of requests is random and tries to address either
service with roughly the same number of requests.

The `book-loop.sh` script sends several HTTP requests to the `book` endpoint in rapid succession. 
Make sure both Book Service terminal windows are visible, then launch the script from 
a third terminal with `bash book-loop.sh`: you should see the services logging a roughly 
equal number of responses.

## Circuit breaker

_meteo-service_ is unreliable: half of the time its responses come quickly,
otherwise they come with an unacceptable delay. To avoid that the entire
system hangs waiting for it, the gateway will timeout after 1000 
milliseconds ("break the circuit") 
and return the last value it received from the service,
flagging it as `stale`.

The `meteo-loop.sh` script sends sever HTTP requests to the `next-meteo`
endpoint. Make sure the Meteo Service terminal window is visible, 
then launch the script from another terminal with `bash meteo-loop.sh`:
you should see the service behaving unreliably (that is, delaying 
responses roughly half of the time) and the client receiving 
correspondingly "stale" replies.

## Rate limiting

_time-service_ is a popular service and is at risks to be overused. The gateway introduces a
rate limiter so that no user can issue more than one request per second; an initial burst of
up to five requests is allowed for the first second.  
Excess requests are rejected
with a `429 Too Many Requests` status.

The `time-loop.sh` script sends an HTTP requests to the `current-datetime`
endpoint every 250 msec. 
The response is logged on the console as a dot if the request was
accepted (HTTP status 200) and with an exclamation mark if it
was rejected.  
Launch the script from a terminal with `bash time-loop.sh`: 
you should see an initial series of 4-5 dots followed by `!`
and `.` in an approximate three-to-one ratio, for instance:  
`.....!!!.!!!!.!!!.!!!!.!!!.!!!!.!!!.!!!!.!!!.!!!!.
`
