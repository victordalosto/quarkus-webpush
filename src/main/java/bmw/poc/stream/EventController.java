package bmw.poc.stream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/stream")
@ApplicationScoped
public class EventController {

    private static Integer counter = 0;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void streamEvents(
        @Context SseEventSink eventSink,
        @Context Sse sse)
    {
        try (final SseEventSink sink = eventSink) {
            while (true) {
                Thread.sleep(300);

                log.info("Sending event...");
                counter++;
                OutboundSseEvent event = sse.newEventBuilder()
                        .id(counter.toString())
                        .name("mensagem do servidor")
                        .data("Hello, world!")
                        .comment("Um comentario")
                        .build();
                sink.send(event);
            }
        } catch (final InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}