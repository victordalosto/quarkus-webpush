package bmw.poc.receipt.event;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import bmw.poc.receipt.domain.PushObjects;
import bmw.poc.receipt.domain.PushType;
import bmw.poc.zold.logging.BmwLogger;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class ReceiptEvents {

    @Inject
    private BmwLogger log;

    @Inject
    private EventBus bus;


    private static final Map<PushType, List<String>> address = Map.of(
        PushType.USER_AGENT, new ArrayList<>(),
        PushType.APPLICATION_SERVER, new ArrayList<>()
    );


    public void registerListener(
        String id,
        PushType type,
        PushObjects pushObjects
    ) {
        log.info(">> requesting data id: " + id);
        address.get(type).add(id);

        bus.consumer(address(id, type)).handler(message -> {
            log.info("Consumed: " + message.body());

            var eventSink = pushObjects.eventSink();
            var sse = pushObjects.sse();
            if (eventSink.isClosed()) {
                unregisterListener(id, type);
            } else {
                eventSink.send(sse.newEventBuilder()
                                .data("Consumed-resposta: " + id + " " + type.value())
                                .build());
            }
        });
    }



    public void unregisterListener(String id, PushType type) {
        log.info(">> unregistering data id: " + id);
        if (address.get(type).contains(id)) {
            address.get(type).remove(id);
        }
        bus.consumer(address(id, type)).unregister();
    }


    public void publish(String id, PushType type) {
        log.info(">> publishing data id: " + id);
        bus.publish(address(id, type), "publisher sent a new msg");
    }


    private String address(String id, PushType type) {
        return type.value() + "-" + id;
    }

}
