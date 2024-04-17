package bmw.poc.zold.httpnovo;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;



@RequestScoped
public class HttpService {

    @Context
    private HttpServerRequest request;

    @Context
    private HttpServerResponse response;



    public void sendMessage() {
        var future = response.push(HttpMethod.GET, "/hello");
        future.onComplete(ar -> {
            if (ar.succeeded()) {
                ar.failed();
                System.out.println("Pushed resource");
            } else {
                System.out.println("Could not push resource");
                if (ar.cause() != null) {
                    ar.cause().printStackTrace();
                }
            }
        });
        // response.send();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
