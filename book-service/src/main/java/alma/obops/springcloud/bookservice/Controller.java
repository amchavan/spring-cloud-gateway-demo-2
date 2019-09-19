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
    public alma.obops.springcloud.bookservice.Book findById(@PathVariable String id ) throws InterruptedException {
        Book book = new Book();
        book.id = id;
        book.title = "This is book " + id;
        book.author = "A. U. Thor (" + id + ")";
        book.year = Integer.parseInt( id );
        LOGGER.info( "id=" + id + " ==> " + book.toString() );
        return book;
    }
}
