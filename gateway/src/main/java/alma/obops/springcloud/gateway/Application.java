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
                .route( "book1", p -> p
                        .path("/book/**").and()
                        .weight( "a", 5 )
                        .filters(f -> f.rewritePath("/book/(?<id>.*)", "/book-service/book/${id}"))
                        .uri("http://localhost:10000")
                        )
                .route( "book2", p -> p
                        .path("/book/**")
                                .and().weight( "a", 5 )
                        .filters(f -> f.rewritePath("/book/(?<id>.*)", "/book-service/book/${id}"))
                        .uri("http://localhost:10001")
                        )
//                .route(p -> p
//                        .header( "Fallback", "true" )
//                        .filters(f -> f.hystrix(config -> config
//                                .setName("mycmd")
//                                .setFallbackUri("forward:/fallback")))
//                        .uri("http://httpbin.org:80"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
