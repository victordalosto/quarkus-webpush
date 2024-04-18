package vhdo.poc.receipt.domain;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;


public record PushObject (

    Sse sse,
    SseEventSink eventSink

) {

}
