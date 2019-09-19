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

HTTP requests to `book/{id}` are routed to two concurrent _book-service_ implementations, 
running on ports 10000 and 10001. Distribution of requests is random and tries to address either
service with roughly the same number of requests.

The `book-loop.sh` scripts sends sever HTTP requests to the `book` endpoint in rapid succession. 
Make sure both Book Service terminal windows are visible, then launch the script from 
a third terminal with `sh book-loop.sh`: you should see the services logging a roughly 
equal number of responses.


