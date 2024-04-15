package bmw.poc;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class Controller {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public JsonObject hello() {
        return new JsonObject().put("Nome", "Victor").put("Idade", 22);
    }

}
