package alma.obops.springcloud.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {


    private static String lastMeteo = "{}";

    private static TypeReference<HashMap<String, Object>> typeRef
            = new TypeReference<HashMap<String, Object>>() {};
    private static ObjectMapper mapper = new ObjectMapper();

    static Map<String,Object> jsonToMap(String json) {
        try {
            return mapper.readValue( json, typeRef );
        }
        catch (IOException e) {
            throw new RuntimeException( e );
        }
    }

    static String getLastMeteo() {
        return lastMeteo;
    }

    static void setLastMeteo(String lastMeteo) {
        Utils.lastMeteo = lastMeteo;
    }
}
