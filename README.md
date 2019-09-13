# spring-cloud-gateway-mvc demo

Use the _spring-cloud-gateway-mvc_ to build a simple
gateway on top of MVC. This application allows to browse
books databases.

`git checkout basic`  
Basic version, all requests go to embedded server and produce bogus books

`git checkout failing`  
First attempt at accessing an external service, fails
with _HttpRequest blocked by CORS policy_ error

`git checkout final`  
Fully functional version
