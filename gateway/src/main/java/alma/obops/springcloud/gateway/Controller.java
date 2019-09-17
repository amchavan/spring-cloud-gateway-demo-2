package alma.obops.springcloud.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

//    private static final Logger LOGGER = LoggerFactory.getLogger( Controller.class.getSimpleName() );

    @RequestMapping("/fallback")
    public Mono<Object> fallback() {

        String t = Utils.getLastMeteo();
        Map<String, Object> fallback = t == null ? new HashMap<>() : Utils.jsonToMap( Utils.getLastMeteo() );
        fallback.put( "stale", true );
//        LOGGER.info( ">>> about to return " + fallback );
        return Mono.just( fallback );
    }
}
