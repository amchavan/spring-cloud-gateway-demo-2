# Meteo Service

A pretend meteorology service, returning the current temperature
and humidity level. The service is engineered to return the meteo
information after either 3000 or 200 milliseconds: the delay
is randomly chosen with a 50% probability of either result.  
The API is `/next-point`

Runs by default on port 10002 with the `/meteo-service` context path;
another port can be configured on the command line.

Run this with `mvn spring-boot:run`.  
If you need to configure a different port than the default, you 
can either invoke  
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9987"`  
or   
`mvn install; java -jar target/meteo-service-*.jar --server.port=9987`

Example call:  
`curl http://localhost:10002/meteo-service/next-point`  
Will return:  
_{"temperature":32.25,"humidity":98.0,"timestamp":"2019-09-19T08:21:21.830"}_
