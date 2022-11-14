# Time Service

A time service returning the current UTC date and time as an ISO string.
The API is `/now`

Runs by default on port 10010 with the `/time-service` context path;
another port can be configured on the command line.

Run this with `mvn spring-boot:run`.  
If you need to configure a different port than the default, you 
can either invoke  
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9987"`  
or   
`mvn install; java -jar target/time-service-*.jar --server.port=9987`

Example call:  
`curl http://localhost:10010/time-service/now`  
Will return an ISO (short) datetime string like _2022-11-14T14:57:36.229_
