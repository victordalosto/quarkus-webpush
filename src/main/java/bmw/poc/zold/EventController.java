package bmw.poc.zold;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;


@Path("/old")
public class EventController {

    @Inject
    private EventService eventService;

    static Integer counter = 0;

    @GET
    @Path("/{id}")
    public void streamEvents(@PathParam("id") String id, @Context SseEventSink eventSink, @Context Sse sse) {
        eventService.getEvents(id)
            .onItem().invoke(data -> {
                counter++;
                OutboundSseEvent event = sse.newEventBuilder()
                    .id(counter.toString())
                    .data(data)
                    .build();
                eventSink.send(event);
            })
            .onTermination().invoke(eventSink::close)
            .subscribe().with(data -> {}, failure -> eventSink.close());
    }

}