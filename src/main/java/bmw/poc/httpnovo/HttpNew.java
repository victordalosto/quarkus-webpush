package bmw.poc.httpnovo;
import io.vertx.core.http.HttpServerResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/http2")
@ApplicationScoped
public class HttpNew {

    @GET
    @Path("/")
    public void getNovo(
        @Context HttpServerResponse response
    ) throws InterruptedException {
        response.write("3: HTTP/2 is being used");
        response.write("\n4: HTTP/2 is really beign used");
        response.send();

        Thread.sleep(3000);
        response.reset();

        response.write("\n5: HTTP/2 is being used");
        response.write("\n6: HTTP/2 is really beign used");

        log.info("Done...");

    }

}
