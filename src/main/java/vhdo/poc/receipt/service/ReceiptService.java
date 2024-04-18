package vhdo.poc.receipt.service;
import vhdo.poc.receipt.domain.PushObjects;
import vhdo.poc.receipt.event.ReceiptEvents;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class ReceiptService {

    @Inject
    private ReceiptEvents events;


    public void registerForReceipt(
        final String id,
        final PushObjects pushObjects
    ) {
        events.registerListenerInEventBus(id, pushObjects);
    }


    public void unregisterReceipt(final String id) {
        events.unregisterListenerInEventBus(id);
    }


}
