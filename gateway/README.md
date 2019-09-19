# Gateway

This component implements an example gateway.

Runs by default on port 9999 (with no context path);
another port can be configured on the command line.

Run this with `mvn spring-boot:run`.  
If you need to configure a different port than the default, you can either invoke  
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9987"`  
or   
`mvn install; java -jar target/book-service-*.jar --server.port=9987`

## Routing

Basic routing is at the core of the Gateway pattern. This
implementation routes incoming HTTP requests as follows:
* `book/{id}` is routed to `http://localhost:10000/book-service/book/{id}` or `http://localhost:10001/book-service/book/{id}`
* `next-meteo` is routed to `http://localhost:10002/meteo-service/next-point`
* `current-datetime` is routed to `http://localhost:10010/time-service/now`

## Load balancing

HTTP requests to `book/{id}` are routed by the gateway to two concurrent _book-service_ implementations, 
running on ports 10000 and 10001. Distribution of requests is random and tries to address either
service with roughly the same number of requests.  
See [RouteConfig.java](src/main/java/alma/obops/springcloud/gateway/RouteConfig.java)

## Circuit breaker

_meteo-service_ is unreliable: half of the time its responses come quickly,
otherwise they come with an unacceptable delay. To avoid that the entire
system hangs waiting for it, the gateway will timeout after 1000 
milliseconds ("break the circuit") 
and return the last value it received from the service,
flagging it as `stale`.  
See [RouteConfig.java](src/main/java/alma/obops/springcloud/gateway/RouteConfig.java)

## Rate limiting

_time-service_ is a popular service and is at risks to be overused. The gateway introduces a
rate limiter so that no user can issue more than one request per second; an initial burst of
up to five requests is allowed for the first second.    
Excess requests are rejected
with a `429 Too Many Requests` status.  
See [application.yaml](src/main/resources/application.yaml)
