package vhdo.poc.zold.httpnovo;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/oldnot")
@ApplicationScoped
public class NotificationWebSocket {

    @GET
    @Path("/")
    public String getNovo(
        @Context HttpServerResponse response

    ) throws InterruptedException {
        // response.write("3: HTTP/2 is being used");
        // response.write("\n4: HTTP/2 is really beign used");
        // response.send();
        var future = response.push(HttpMethod.GET, "/");
        response.push(HttpMethod.GET, "/main.js");
        response.push(HttpMethod.GET, "/lorem.txt");

        future.onComplete(ar -> {
            // ar.cause().printStackTrace();
            if (ar.succeeded()) {
                log.info("Sucess...");
                var pushedResponse = ar.result();
                pushedResponse.putHeader("content-type", "text/plain");
                pushedResponse.write("7: HTTP/2 is being used");
                pushedResponse.end();
            } else {
                log.error("Failed to push...");
            }
        });
        return "Done...";
    }


}
