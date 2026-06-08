import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private final Map<String, List<Consumer<Object>>> subscribers = new ConcurrentHashMap<>();

    public void subscribe(String event, Consumer<Object> listener) {
        subscribers.computeIfAbsent(event, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void publish(String event, Object data) {
        List<Consumer<Object>> listeners = subscribers.getOrDefault(event, Collections.emptyList());
        for (Consumer<Object> listener : listeners)
            listener.accept(data);
    }

    public void unsubscribe(String event, Consumer<Object> listener) {
        subscribers.getOrDefault(event, Collections.emptyList()).remove(listener);
    }

    public static void main(String[] args) {
        EventBus bus = new EventBus();

        Consumer<Object> emailAlert = msg -> System.out.println("Email Alert:  " + msg);
        Consumer<Object> smsAlert = msg -> System.out.println("SMS Alert:    " + msg);
        Consumer<Object> logAlert = msg -> System.out.println("Log Alert:    " + msg);

        bus.subscribe("user.login", emailAlert);
        bus.subscribe("user.login", logAlert);
        bus.subscribe("user.logout", smsAlert);
        bus.subscribe("user.logout", logAlert);

        System.out.println("--- User Login ---");
        bus.publish("user.login", "samin logged in");

        System.out.println("\n--- User Logout ---");
        bus.publish("user.logout", "samin logged out");

        System.out.println("\n--- After unsubscribe logAlert from login ---");
        bus.unsubscribe("user.login", logAlert);
        bus.publish("user.login", "alice logged in");
    }
}