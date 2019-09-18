package alma.obops.springcloud.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfig {

    @Bean
    public KeyResolver hostAddressKeyResolver() {
        return exchange -> {
            final var hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            return Mono.just( hostAddress );
        };
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        return builder.routes()
                
                // The following two routes share the load for the book service
                // between two servers
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

                // This route leads to an unreliable meteo service, with
                // fallback to the last cached value
                .route( "meteo with fallback", p -> p
                        .path("/next-meteo")
                        .filters(f -> f
                                .hystrix(config -> config.setName( "slow-meteo" )
                                        .setFallbackUri( "forward:/fallback"))
                                .rewritePath( "/next-meteo", "/meteo-service/next-point" )
                                .modifyResponseBody( String.class, String.class, (exchange, body) -> {
                                    if( ! body.contains( "stale" )) {       // is this coming back from fallback?
                                        Utils.setLastMeteo( body );         // NO, this is coming from the meteo service,
                                                                            //     save it for the next fallbacks
                                    }
                                    return Mono.just( body );
                                })
                        )
                        .uri("http://localhost:10002"))

                .build();
    }
}
