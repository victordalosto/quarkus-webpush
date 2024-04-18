package vhdo.poc.zold.logging;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
public class BmwLogger {


    public void info(String message) {
        log.info(message);
    }


    public void error(String message) {
        log.error(message);
    }

}
