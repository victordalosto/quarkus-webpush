package bmw.poc.event;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.MessageConsumer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/event")
@ApplicationScoped
public class EventBusResource {

    @Inject
    private EventBus bus;


    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> greeting(String id) {
        log.info(">> requesting data id: " + id);

        MessageConsumer<String> consumer = bus.consumer("event-address");
        consumer.handler(message -> {
            log.info("Consumed: " + message.body());
            message.reply("Consumer-resposta: " + Resposta.build().toString());
        });

        // consumer.unregister();
        return null;
    }


    @GET
    @Path("send")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> send() {
        return bus.request("event-address", "producer sent: " + Resposta.build())
                  .onItem().transform(message -> {
                      log.info("Producer response: " + message.body());
                      return "Message sent successfully: " + message.body();
                  })
                  .onFailure().recoverWithItem(failure -> {
                      log.error("Failed to get response", failure.getMessage());
                      return "2Failed to get response";
                  });
    }

}
