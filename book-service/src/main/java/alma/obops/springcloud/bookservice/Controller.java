package alma.obops.springcloud.bookservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger( Controller.class.getSimpleName() );

    @GetMapping( "/book/{id}" )
    public alma.obops.springcloud.bookservice.Book findById(@PathVariable String id ) {
        Book u = new Book();
        u.id = id;
        u.title = "This is book " + id;
        u.author = "A. U. Thor (" + id + ")";

        int year = ThreadLocalRandom.current().nextInt( 1900, 2019 );
        u.year = Integer.toString( year );
        LOGGER.info( "id=" + id + " ==> " + u.toString() );
        return u;
    }
}
