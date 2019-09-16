package alma.obops.springcloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayApplication {

    static String lastMeteo = null;
    private static final Logger LOGGER = LoggerFactory.getLogger( Controller.class.getSimpleName() );

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( "book1", p -> p
                        .path("/book/**")
                                .and().weight( "a", 5 )
                        .filters(f -> f.rewritePath("/book/(?<id>.*)", "/book-service/book/${id}"))
                        .uri("http://localhost:10000")
                        )
                .route( "book2", p -> p
                        .path("/book/**")
                                .and().weight( "a", 5 )
                        .filters(f -> f.rewritePath( "/book/(?<id>.*)", "/book-service/book/${id}" ))
                        .uri( "http://localhost:10001" )
                        )
                .route( "meteo with fallback", p -> p
                        .path("/next-meteo")
                        .filters(f -> f
                                .hystrix(config -> config.setName( "slow-meteo" )
                                                         .setFallbackUri( "forward:/fallback"))
                                .rewritePath( "/next-meteo", "/meteo-service/next-point" )
                                .modifyResponseBody( String.class, String.class, (exchange, body) -> {
                                    // don't modify, just save the last good one
                                    lastMeteo = body;
                                    LOGGER.info( ">>> Saving: " + body );
                                    return Mono.just( body );
                                })
                        )
                        .uri("http://localhost:10002"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
