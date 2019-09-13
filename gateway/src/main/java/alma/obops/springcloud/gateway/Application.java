package alma.obops.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    @Bean

    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get/**")
                        .filters(f -> f.rewritePath("/get/(?<id>.*)", "/book-service/book/${id}"))
                        .uri("http://localhost:10000"))
                .route(p -> p
                        .header( "Fallback", "true" )
                        .filters(f -> f.hystrix(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }

//    public RouteLocator myRoutes(RouteLocatorBuilder builder ) {

//        return builder.routes()
//                .route( p -> p
//                        .path( "/book" )
////                        .uri( "http://localhost:7000/book-service" ))
//                        .uri( "http://httpbin.org:80" ))
//                .build();
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
