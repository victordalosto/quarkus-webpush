package bmw.poc.sockjs;

import java.util.concurrent.atomic.AtomicInteger;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class SockJsExample {

    @Inject
    private Vertx vertx;

    public void init(@Observes Router router) {
        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        Router bridge = sockJSHandler.bridge(new SockJSBridgeOptions()
                                     .addOutboundPermitted(new PermittedOptions().setAddress("ticks")));
        router.route("/eventbus/*").subRouter(bridge);

        AtomicInteger counter = new AtomicInteger();
        vertx.setPeriodic(1000,
                ignored -> vertx.eventBus().publish("ticks", counter.getAndIncrement()));
    }

}