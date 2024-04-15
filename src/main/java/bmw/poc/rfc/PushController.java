package bmw.poc.rfc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jboss.resteasy.reactive.RestStreamElementType;
import bmw.poc.domain.Message;
import io.smallrye.mutiny.Multi;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/push")
@ApplicationScoped
public class PushController {


    private List<Message> messageList = Collections.synchronizedList(new ArrayList<>());

    @GET
    public List<Message> list() {
        return messageList;
    }


    // @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response receive(Message message) {
    //     messages.add(message);
    //     return Response.ok().build();
    // }


    @POST
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public void receiveMessages(Message message) {
        log.info("Received message: " + message);
        messageList.add(message);
    }

}
