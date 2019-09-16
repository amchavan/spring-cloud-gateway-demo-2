package alma.obops.springcloud.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 */

@RestController
public class Controller {

    private TypeReference<HashMap<String, Double>> typeRef
            = new TypeReference<HashMap<String, Double>>() {};
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping("/fallback")
    public Mono<Object> fallback() throws IOException {
        Map<String, Object> map;
        map = mapper.readValue( GatewayApplication.lastMeteo, typeRef );
        map.put( "stale", true );

        return Mono.just( map );
    }
}
