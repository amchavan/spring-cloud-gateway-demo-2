package alma.obops.springcloud.gateway;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @RequestMapping("/fallback")
    public Mono<Object> fallback() {

        String t = Utils.getLastMeteo();
        Map<String, Object> fallback = t == null ? new HashMap<>() : Utils.jsonToMap( Utils.getLastMeteo() );
        fallback.put( "stale", true );
        return Mono.just( fallback );
    }
}
