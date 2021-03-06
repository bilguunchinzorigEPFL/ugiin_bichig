package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by beku on 10/8/2018.
 */

@SpringBootApplication
@EnableCaching
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class,args);
    }
}
