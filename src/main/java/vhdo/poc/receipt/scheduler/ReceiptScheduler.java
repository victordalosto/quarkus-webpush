package vhdo.poc.receipt.scheduler;
import java.util.concurrent.TimeUnit;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.Scheduled.ConcurrentExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vhdo.poc.receipt.event.ReceiptEvents;


@ApplicationScoped
public class ReceiptScheduler {

    @Inject
    private ReceiptEvents event;


     /**
     * This methods call the event listener cleaner every 1 hour,
     * looking for dead listeners.
     */
    @Scheduled(every="3600s",
               delay=5, delayUnit = TimeUnit.MINUTES,
               concurrentExecution = ConcurrentExecution.SKIP)
    void interactWithNodes() {
        event.cleanDeadListener();
    }

}
