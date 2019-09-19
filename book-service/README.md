# Book Service

A pretend Book service, allowing to "search" for books based on their
numerical id. (Books are in fact created on demand.)  
The API is `/book/{id}`

Runs by default on port 10000 with the `/book-service` context path;
another port can be configured on the command line.

Run this with `mvn spring-boot:run`.  
If you need to configure a different port than the default, you can either invoke  
`mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=9987"`  
or   
`mvn install; java -jar target/book-service-*.jar --server.port=9987`

Example call:  
`curl http://localhost:9987/book-service/book/42`  
Will return:  
_{"id":"42","title":"This is book 42", ... }_
