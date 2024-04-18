package vhdo.poc.zold;

import java.nio.charset.StandardCharsets;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.streams.ReadStream;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


@Path("/oldvertx")
@ApplicationScoped
public class VertxResource {

    private final Vertx vertx;

    private final WebClient client;

    @Inject
    public VertxResource(Vertx vertx) {
        this.vertx = vertx;
        this.client = WebClient.create(vertx);
    }


    @GET
    @Path("/lorem")
    public Uni<String> readShortFile() {
        return vertx.fileSystem()
                    .readFile("lorem.txt")
                    .onItem()
                    .transform(content -> content.toString(StandardCharsets.UTF_8));
    }




    private static int PAGE = 0;
    @GET
    @Path("/book")
    // Stream the content of a large file into chunks
    public Multi<String> readLargeFile() {
        return vertx.fileSystem()
                    .open("book.txt", new OpenOptions().setRead(true))
                    .onItem().transformToMulti(ReadStream::toMulti)
                    .onItem().transform(content -> content.toString(StandardCharsets.UTF_8) + "\n-----"+(++PAGE)+"-------\n");
    }



    private static final String URL = "https://en.wikipedia.org/w/api.php?action=parse&page=Quarkus&format=json&prop=langlinks";
    @GET
    @Path("/web")
    public Uni<JsonArray> retrieveDataFromWikipedia() {
        return client.getAbs(URL)
                     .send()
                     .onItem().transform(HttpResponse::bodyAsJsonObject)
                     .onItem().transform(json -> json.getJsonObject("parse")
                              .getJsonArray("langlinks"));
    }



}