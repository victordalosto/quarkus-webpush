package vhdo.poc.receipt.event;
import vhdo.poc.receipt.domain.AddressDictionary;
import vhdo.poc.receipt.domain.PushMessage;
import vhdo.poc.receipt.domain.PushObjects;
import vhdo.poc.zold.logging.vhdoLogger;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.MessageConsumer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class ReceiptEvents   {

    private static final AddressDictionary addresses = new AddressDictionary();

    @Inject
    private vhdoLogger log;

    @Inject
    private EventBus bus;



    public void publish(final String id, final PushMessage message) {
        log.info(">> publishing msg : " + message);
        bus.publish(id, message.toJson());
    }



    public void registerListenerInEventBus (
        final String id,
        final PushObjects pushObjects
    ) {
        log.info(">> registering listener for: " + id);
        var message = bus.<JsonObject>consumer(id);

        message.handler(event -> {
            var eventSink = pushObjects.eventSink();
            if (eventSink.isClosed()) {
                unregister(id, message);
            } else {
                eventSink.send(pushObjects.sse()
                                          .newEventBuilder()
                                          .data("Consumed-resposta: " + id)
                                          .build());
                log.info("Consumed: " + event.body());
            }
        });

        addresses.registerAddress(id, message);
    }



    public void unregisterListenerInEventBus(final String id) {
        addresses.getListeners(id)
                 .forEach(listener -> unregister(id, listener));
        addresses.unregisterAddress(id);
    }



    private void unregister(
        final String id,
        final MessageConsumer<JsonObject> consumer
    ) {
        consumer.unregister().subscribe().with(
            success -> log.info ("Unregistered listener: " + id),
            failure -> log.error("Error unregistering consumer" + failure.getMessage())
        );
    }


}
