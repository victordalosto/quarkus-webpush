package bmw.poc.receipt.domain;

import io.vertx.core.json.JsonObject;

public record PushMessage (

    String id,
    String message

) {


    public JsonObject toJson() {
        return new JsonObject()
            .put("id", id)
            .put("message", message);
    }

}
