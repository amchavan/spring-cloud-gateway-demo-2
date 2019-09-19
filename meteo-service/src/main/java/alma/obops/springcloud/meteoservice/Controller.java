package alma.obops.springcloud.meteoservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger( Controller.class.getSimpleName() );
    private static final long startTime = (new Date()).getTime();
    private static final int LONG_SLEEP = 3000;
    private static final int SHORT_SLEEP = 200;
    private static int direction = 1;
    private static Double lastTemperature = 0.0;

    /**
     * Over-clever implementation of a service to generate pseudo-plausible meteo points.
     */
    @GetMapping( "/next-point" )
    public Meteo currentMeteo() throws InterruptedException {

        Date currentDate = new Date();

        // Keep current temperature between 0 and 100 degrees
        Double currentTemperature = lastTemperature + (direction * 0.25);
        if( currentTemperature > 100.0 ) {
            currentTemperature = 100.0;
            direction = -1;
        }
        if( currentTemperature < 0.0 ) {
            currentTemperature = 0.0;
            direction = 1;
        }

        // Conjure random humidity and decide how long to wait
        // based on that value
        int currentHumidity = ThreadLocalRandom.current().nextInt( 0, 100 );
        long sleep = currentHumidity > 50 ? LONG_SLEEP : SHORT_SLEEP;
        LOGGER.info( "humidity=" + currentHumidity + ", about to sleep " + sleep + " msec" );
//        if( sleep == LONG_SLEEP ) LOGGER.info( ">>> Start to sleep at " + (new Date()));
        Thread.sleep( sleep );
//        if( sleep == LONG_SLEEP ) LOGGER.info( ">>>   End of sleep at " + (new Date()));

        Meteo meteo = new Meteo();
        meteo.timestamp = toISOString( currentDate );
        meteo.temperature = lastTemperature = currentTemperature;
        meteo.humidity = currentHumidity;
        return meteo;
    }

    String toISOString( Date date ) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }
}
