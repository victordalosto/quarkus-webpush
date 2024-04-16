package bmw.poc.old;

import io.vertx.core.http.HttpVersion;
import io.vertx.ext.web.RoutingContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/push2")
public class NewPush {

    @GET
    public void pushMessage(@Context RoutingContext routingContext) throws InterruptedException {
        var request = routingContext.request();
        var response = routingContext.response();
        // Check if HTTP/2 is being used
        if (request.version() == HttpVersion.HTTP_2) {
            log.info("HTTP/2 is being used");

            var code = response.getStatusCode();
            log.info("Code: " + code);

            response.write("1: HTTP/2 is being used");
            log.info("Pushing message...");

            response.write("\n2: HTTP/2 is really beign used");
            log.info("Message pushed");


        } else {
            log.error("HTTP/2 is required for server push");
            request.response().setStatusCode(400).end("HTTP/2 is required for server push");
        }
    }


    @GET
    @Path("/1")
    public String get1() {
        log.info("GET 1");
        return "ONE1.0";
    }
}