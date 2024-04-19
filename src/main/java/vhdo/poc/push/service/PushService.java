package vhdo.poc.push.service;
import vhdo.poc.receipt.domain.PushMessage;
import vhdo.poc.receipt.event.ReceiptEvents;
import vhdo.poc.zold.logging.MyLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class PushService {

    @Inject
    private ReceiptEvents events;


    @Inject
    private MyLogger log;


    public void pushMessage(String id) {
        log.info("Pushing message for: " + id);

        PushMessage message = new PushMessage(id, "Pushing message for: " + id);
        events.publish(id, message);
    }


}
