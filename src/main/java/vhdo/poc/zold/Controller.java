package vhdo.poc.zold;

import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class Controller {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public JsonObject barra() {
        return new JsonObject().put("Nome", "Victor").put("Idade", 22);
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public JsonObject hello() {
        return new JsonObject().put("Nome", "Victor").put("Idade", 22);
    }

}
