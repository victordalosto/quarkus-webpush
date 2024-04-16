package bmw.poc.old;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ApplicationScoped
public class EventService {

    List<String> events = new ArrayList<>();

    public Uni<String> getEvents(String id) {
        log.info("Requesting data for ID: " + id);
        return Uni.createFrom().item(() -> "Data for ID: " + id)
            .onItem().delayIt().by(Duration.ofSeconds(5)); // Simulating delay for example purposes
    }

}
