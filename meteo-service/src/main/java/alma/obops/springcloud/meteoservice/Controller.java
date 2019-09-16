package alma.obops.springcloud.meteoservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger( Controller.class.getSimpleName() );
    private static final long startTime = (new Date()).getTime();

    @GetMapping( "/meteo" )
    public Meteo currentMeteo() throws InterruptedException {
        long now = (new Date()).getTime();
        long sec = Math.floorDiv( now-startTime, 1000L );

        Meteo meteo = new Meteo();
        meteo.temperature = 25 + (sec * 0.25);
        meteo.humidity = ThreadLocalRandom.current().nextInt( 0, 100 );

        Thread.sleep( meteo.humidity > 50 ? 7000 : 500 );
        return meteo;
    }
}
