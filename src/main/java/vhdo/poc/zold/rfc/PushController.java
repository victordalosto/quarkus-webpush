package vhdo.poc.zold.rfc;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;


@Path("/oldpush")
@ApplicationScoped
public class PushController {


    // private List<Message> messageList = Collections.synchronizedList(new ArrayList<>());

    // @GET
    // public List<Message> list() {
    //     return messageList;
    // }


    // @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response receive(Message message) {
    //     messages.add(message);
    //     return Response.ok().build();
    // }


    // @POST
    // @RestStreamElementType(MediaType.APPLICATION_JSON)
    // public void receiveMessages(Message message) {
    //     log.info("Received message: " + message);
    //     messageList.add(message);
    // }

}
