package bmw.poc.receipt.service;
import bmw.poc.receipt.domain.PushObjects;
import bmw.poc.receipt.domain.PushType;
import bmw.poc.receipt.event.ReceiptEvents;
import bmw.poc.zold.logging.BmwLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class ReceiptService {

    @Inject
    private BmwLogger log;

    @Inject
    private ReceiptEvents events;


    public void registerForReceipt(String id, PushType type, PushObjects pushObjects) {
        log.info("Registering receipt for: " + id);
        events.registerListener(id, type, pushObjects);

        while (!pushObjects.eventSink().isClosed()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Error while waiting for event sink to close");
            }
        }
    }


}
