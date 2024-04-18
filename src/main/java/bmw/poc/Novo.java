package bmw.poc;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteFilter;
import io.smallrye.mutiny.Multi;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
public class Novo {

    @RouteFilter(100)
    void filter(RoutingContext context) {
        context.response().putHeader("filter", "passei");
        context.next();
    }



    @GET
    @Route(path = "/novo", methods = Route.HttpMethod.GET)
    public void novo(
        RoutingContext context
    ) throws Exception {
        var response = context.response();
        log.info("lorem.txt");
        response.push(HttpMethod.GET, "/lorem.txt");
        log.info("hellow");
        response.push(HttpMethod.GET, "/hellow");
        response.end();
    }



    @Route(path = "/hellow", produces = ReactiveRoutes.EVENT_STREAM, methods = Route.HttpMethod.GET)
    Multi<JsonObject> hellos() {
        return Multi.createFrom()
            .items(new JsonObject().put("nome", "victor"),
                   new JsonObject().put("nome", "hugo"),
                   new JsonObject().put("nome", "Dalosto"),
                   new JsonObject().put("nome", "de"),
                   new JsonObject().put("nome", "Oliveira")
            );
    }

}
