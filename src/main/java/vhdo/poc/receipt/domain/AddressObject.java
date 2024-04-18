package vhdo.poc.receipt.domain;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.MessageConsumer;


public record AddressObject (

    PushObject pushObject,
    MessageConsumer<JsonObject> message


) {


    public boolean isConnectionClosed() {
        return pushObject.eventSink().isClosed();
    }

}