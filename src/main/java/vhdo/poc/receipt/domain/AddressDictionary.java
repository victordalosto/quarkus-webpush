package vhdo.poc.receipt.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.MessageConsumer;


/**
 * This class is responsible for managing the addresses and their listeners.
 * It holds the listeners references for each address for this local pod.
 */
public class AddressDictionary {


    private final Map<String, List<MessageConsumer<JsonObject>>>
        addresses = Collections.synchronizedMap(new HashMap<>()
    );



    public synchronized void registerAddress(
        final String id,
        final MessageConsumer<JsonObject> message
    ) {
        addresses.computeIfAbsent(id, k -> new ArrayList<>())
                 .add(message);
    }


    public synchronized void unregisterAddress(final String id) {
        addresses.remove(id);
    }


    public List<MessageConsumer<JsonObject>> getListeners(final String id) {
        return addresses.getOrDefault(id, Collections.emptyList());
    }


}
