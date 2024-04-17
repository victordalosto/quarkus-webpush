package bmw.poc.receipt.controller;
import bmw.poc.receipt.domain.PushObjects;
import bmw.poc.receipt.domain.PushType;
import bmw.poc.receipt.service.ReceiptService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.server.PathParam;
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
    public void receiptUserAgent(
        @PathParam("id") String id,
        @Context SseEventSink eventSink,
        @Context Sse sse
    ) {
        receiptService.registerForReceipt(id, PushType.USER_AGENT, new PushObjects(sse, eventSink));
    }


    @GET
    @Path("/receipt-subscription/{id}")
    public void receiptApplicationServer(
        @PathParam("id") String id,
        @Context SseEventSink eventSink,
        @Context Sse sse
    ) {
        receiptService.registerForReceipt(id, PushType.APPLICATION_SERVER, new PushObjects(sse, eventSink));
    }


}
