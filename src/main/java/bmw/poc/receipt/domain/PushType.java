package bmw.poc.receipt.domain;

public enum PushType {

    USER_AGENT("user-agent"),
    APPLICATION_SERVER("application-server");

    private final String value;

    PushType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
