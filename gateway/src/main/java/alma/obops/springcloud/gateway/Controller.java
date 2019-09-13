package alma.obops.springcloud.gateway;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * TODO
 */

@RestController
public class Controller {
    @Bean
    public RouteLocator myRoutes( RouteLocatorBuilder builder ) {
        return builder.routes()
                .route( p -> p
                        .path( "/book" )
                        .uri( "http://localhost:7000/book-service" ))
                .build();
    }

//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/get")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://httpbin.org:80"))
//                .route(p -> p
//                        .header( "Fallback", "true" )
//                        .filters(f -> f.hystrix(config -> config
//                                .setName("mycmd")
//                                .setFallbackUri("forward:/fallback")))
//                        .uri("http://httpbin.org:80"))
//                .build();
//    }
//
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just( "fallback\n" );
    }
}
