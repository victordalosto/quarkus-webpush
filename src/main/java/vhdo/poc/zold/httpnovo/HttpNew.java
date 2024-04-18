package vhdo.poc.zold.httpnovo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/oldhttp2")
@ApplicationScoped
public class HttpNew {

    @Inject
    private HttpService service;

    @GET
    @Path("/")
    public void novo() {
        log.info(">> novo");
        service.sendMessage();
        log.info(">> novo");
        service.sendMessage();
        log.info(">> novo");
        service.sendMessage();
    }

}
