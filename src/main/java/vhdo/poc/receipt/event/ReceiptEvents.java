package vhdo.poc.receipt.event;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vhdo.poc.receipt.domain.AddressDictionary;
import vhdo.poc.receipt.domain.AddressObject;
import vhdo.poc.receipt.domain.PushMessage;
import vhdo.poc.receipt.domain.PushObject;
import vhdo.poc.zold.logging.BmwLogger;


@ApplicationScoped
public class ReceiptEvents   {

    private static final AddressDictionary addresses = new AddressDictionary();

    @Inject
    private BmwLogger log;

    @Inject
    private EventBus eventBus;



    public void publish(final String id, final PushMessage message) {
        log.info(">> publishing msg : " + message);
        eventBus.publish(id, message.toJson());
    }



    public void registerListenerInEventBus (
        final String id,
        final PushObject pushObject
    ) {
        log.info(">> registering listener for: " + id);
        var eventSink = pushObject.eventSink();
        var consumer = eventBus.<JsonObject>consumer(id);
        var address = new AddressObject(pushObject, consumer);

        consumer.handler(event -> {
            if (address.isConnectionClosed()) {
                unregister(address);
            } else {
                eventSink.send(pushObject.sse()
                                         .newEventBuilder()
                                         .data("Consumed-resposta: " + id)
                                         .build());
                log.info("Consumed: " + event.body());
            }
        });

        addresses.registerAddress(id, address);
    }



    public void unregisterListenerInEventBus(final String id) {
        addresses.getAddresssById(id).forEach(this::unregister);
    }



    public void cleanDeadListener() {
        addresses.getAllAddresses()
            .forEach(address -> {
                if (address.isConnectionClosed()) {
                    unregister(address);
                }
            });
    }



    private void unregister(
        final AddressObject address
    ) {
        var message = address.message();
        var eventSink = address.pushObject().eventSink();
        if (!eventSink.isClosed()) {
            eventSink.send(address.pushObject()
                            .sse()
                                .newEventBuilder()
                                .data("Closed connection")
                                .build());
            eventSink.close();
        }

        message.unregister().subscribe().with(
            success -> log.info ("Unregistered listener: " + message.address()),
            failure -> log.error("Error unregistering consumer" + failure.getMessage())
        );
    }


}
