package vhdo.poc.receipt.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is responsible for managing the addresses and their listeners.
 * It holds the listeners references for each address for this local pod.
 */
public class AddressDictionary {


    private final Map<String, List<AddressObject>>
        addresses = Collections.synchronizedMap(new HashMap<>()
    );



    public synchronized void registerAddress(
        final String id,
        final AddressObject addressObject
    ) {
        addresses.computeIfAbsent(id, k -> new ArrayList<>())
                 .add(addressObject);
    }



    public synchronized void unregisterAddress(final String id) {
        addresses.remove(id);
    }



    public List<AddressObject> getAddresssById(final String id) {
        return addresses.getOrDefault(id, List.of());
    }



    public List<AddressObject> getAllAddresses() {
        return addresses.values()
                        .stream()
                        .flatMap(List::stream)
                        .toList();
    }



}
