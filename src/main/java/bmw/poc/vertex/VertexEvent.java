package bmw.poc.vertex;
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
@Path("/vertexevent")
@ApplicationScoped
public class VertexEvent {


    @Inject
    private EventBus bus;


    @GET @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> greeting(String name) {
        log.info("> requesting: " + name);
        return bus.<String>request("vertex", name).onItem().transform(Message::body);
    }


    @ConsumeEvent(value = "vertex", blocking = false)
    public Uni<String> service(String name) throws InterruptedException {
        // Wrong implementation...
        log.info(">> consuming: " + name);
        Thread.sleep(3000);
        log.info("<< consumed: " + name);
        return Uni.createFrom().item(() -> "Hello " + name + "!");
    }

}
