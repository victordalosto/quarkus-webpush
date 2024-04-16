package bmw.poc.old;
import java.util.Random;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;


@ApplicationScoped
public class ScheduledEventEmitter {

    @Inject
    private Event<String> events;

    private final Random random = new Random();


    @Scheduled(every="10s")
    public void emitEvent() {
        String simulatedData = "Simulated Data: " + random.nextInt();
        events.fire(simulatedData);
    }

}