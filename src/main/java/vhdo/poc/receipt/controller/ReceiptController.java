package vhdo.poc.receipt.controller;
import vhdo.poc.receipt.domain.PushObject;
import vhdo.poc.receipt.service.ReceiptService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;


@Path("/")
@ApplicationScoped
@Produces(MediaType.SERVER_SENT_EVENTS)
public class ReceiptController {

    @Inject
    private ReceiptService receiptService;


    @GET
    @Path("/receipt/{id}")
    public void receiptApplicationServer(
        @PathParam("id") String id,
        @Context SseEventSink eventSink,
        @Context Sse sse
    ) {
        receiptService.registerForReceipt(id, new PushObject(sse, eventSink));
    }



    @DELETE
    @Path("/receipt/{id}")
    public void unregisterReceiptApplicationServer(
        @PathParam("id") String id
    ) {
        receiptService.unregisterReceipt(id);
    }

}
