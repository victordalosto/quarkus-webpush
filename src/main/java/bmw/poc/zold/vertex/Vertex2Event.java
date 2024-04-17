package bmw.poc.zold.vertex;
import java.time.Duration;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/oldvertexevent2")
@ApplicationScoped
public class Vertex2Event {


    @Inject
    EventBus bus;

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> greeting(String name) {
        log.info("> requesting: " + name);
        return bus.<String>request("vertex2", name).onItem().transform(Message::body);
    }



    @ConsumeEvent(value = "vertex2", blocking = false)
    public Uni<String> service(String name) {
        log.info(">> consuming: " + name);
        // Use non-blocking delay
        return Uni.createFrom().item(name)
                .onItem().delayIt().by(Duration.ofSeconds(3))
                .onItem().invoke(n ->
                    log.info("<< consumed: " + n)
                )
                .onItem().transform(n -> "Hell2 " + n + "!");
    }
}
