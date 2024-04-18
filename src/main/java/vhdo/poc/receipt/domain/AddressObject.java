package vhdo.poc.receipt.domain;
import java.util.concurrent.CompletionStage;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.MessageConsumer;
import jakarta.ws.rs.sse.OutboundSseEvent;


public record AddressObject (

    PushObject pushObject,
    MessageConsumer<JsonObject> message


) {

    public boolean isConnectionClosed() {
        return pushObject.eventSink().isClosed();
    }


    public CompletionStage<?> sendToClient(final String pushMessage) {
        return pushObject.eventSink()
                         .send(pushObject.sse()
                                  .newEventBuilder()
                                  .data(pushMessage)
                                  .build());
    }


    public void closeConnection() {
        pushObject.eventSink().close();
    }

}