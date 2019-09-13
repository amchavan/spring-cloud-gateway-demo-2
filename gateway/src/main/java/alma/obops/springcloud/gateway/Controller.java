package alma.obops.springcloud.gateway;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * TODO
 */

@RestController
public class Controller {

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just( "fallback\n" );
    }
}
