package bmw.poc.receipt.domain;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;


public record PushObjects (

    Sse sse,
    SseEventSink eventSink

) {

}
