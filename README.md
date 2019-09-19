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

## Basic routing

Try the basic routing functions:

* `curl http://localhost:9999/book/22`   
  `curl http://localhost:10000/book-service/book/22` 
 
* `curl http://localhost:9999/next-meteo`  
  `curl http://localhost:10002/meteo-service/next-point`
  
* `curl http://localhost:9999/current-datetime`  
  `curl http://localhost:10010/time-service/now`

## Load balancing

The gateway allows for basic load balancing between two Book Service
installations. You can run the 
