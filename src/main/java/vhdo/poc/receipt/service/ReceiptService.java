package vhdo.poc.receipt.service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vhdo.poc.receipt.domain.PushObject;
import vhdo.poc.receipt.event.ReceiptEvents;


@ApplicationScoped
public class ReceiptService {

    @Inject
    private ReceiptEvents events;


    public void registerForReceipt(
        final String id,
        final PushObject pushObjects
    ) {
        events.registerListenerInEventBus(id, pushObjects);
    }


    public void unregisterReceipt(final String id) {
        events.unregisterListenerInEventBus(id);
    }


}
