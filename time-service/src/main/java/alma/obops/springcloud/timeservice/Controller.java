package alma.obops.springcloud.timeservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
public class Controller {

    @GetMapping( "/now" )
    public String currentDateTime() {
        return toISOString( new Date() );
    }

    String toISOString( Date date ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
