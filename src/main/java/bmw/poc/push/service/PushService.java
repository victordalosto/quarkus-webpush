package bmw.poc.push.service;
import bmw.poc.receipt.domain.PushType;
import bmw.poc.receipt.event.ReceiptEvents;
import bmw.poc.zold.logging.BmwLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class PushService {

    @Inject
    private ReceiptEvents events;


    @Inject
    private BmwLogger log;


    public void pushMessage(String id) {
        log.info("Pushing message for: " + id);
        events.publish(id, PushType.USER_AGENT);
    }


}