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
    public static final int LONG_SLEEP = 3000;
    public static final int SHORT_SLEEP = 200;

    @GetMapping( "/next-point" )
    public Meteo currentMeteo() throws InterruptedException {
        long now = (new Date()).getTime();
        long sec = Math.floorDiv( now-startTime, 1000L );

        Meteo meteo = new Meteo();
        meteo.temperature = 25 + (sec * 0.25);
        meteo.humidity = ThreadLocalRandom.current().nextDouble( 0.0, 100.0 );

        long sleep = meteo.humidity > 50.0 ? LONG_SLEEP : SHORT_SLEEP;
        LOGGER.info( "humidity=" + meteo.humidity + ", about to sleep " + sleep + " msec" );
        if( sleep == LONG_SLEEP ) LOGGER.info( ">>> Start to sleep at " + (new Date()));
        Thread.sleep( sleep );
        if( sleep == LONG_SLEEP ) LOGGER.info( ">>>   End of sleep at " + (new Date()));
        return meteo;
    }
}
