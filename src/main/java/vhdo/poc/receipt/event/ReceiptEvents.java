package vhdo.poc.receipt.event;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.MessageConsumer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.sse.SseEventSink;
import vhdo.poc.receipt.domain.AddressDictionary;
import vhdo.poc.receipt.domain.AddressObject;
import vhdo.poc.receipt.domain.PushMessage;
import vhdo.poc.receipt.domain.PushObject;
import vhdo.poc.zold.logging.MyLogger;


@ApplicationScoped
public class ReceiptEvents   {

    private static final AddressDictionary addresses = new AddressDictionary();

    @Inject
    private MyLogger log;

    @Inject
    private EventBus eventBus;



    public void publish(final String id, final PushMessage message) {
        log.info(">> publishing msg : " + message);
        eventBus.publish(id, message.toJson());
    }



    public void registerListenerInEventBus (final String id, final PushObject pushObject) {
        log.info(">> registering listener for: " + id);
        var consumer = eventBus.<JsonObject>consumer(id);
        var address = new AddressObject(pushObject, consumer);
        consumerEvent(address);
        addresses.registerAddress(id, address);
    }



    public void unregisterListenerInEventBus(final String id) {
        addresses.getAddresssById(id).forEach(this::unregister);
    }



    public void cleanDeadListener() {
        addresses.getAllAddresses()
                 .stream()
                 .filter(AddressObject::isConnectionClosed)
                 .forEach(this::unregister);
    }



    private void consumerEvent(final AddressObject address) {
        address.message().handler(event -> {
            if (address.isConnectionClosed()) {
                unregister(address);
            } else {
                address.sendToClient("Consumed-resposta: " + event.body());
            }
        });
    }



    private void unregister(final AddressObject address) {
        if (!address.isConnectionClosed()) {
            address.sendToClient("Closed connection")
                   .thenRun(address::closeConnection);
        }

        var message = address.message();
        message.unregister().subscribe().with(
            success -> log.info ("Unregistered listener: " + message.address()),
            failure -> log.error("Error unregistering consumer" + failure.getMessage())
        );
    }


}
