import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {
    private final Map<String, String> store = new ConcurrentHashMap<>();
    private final String filePath;

    public KeyValueStore(String filePath) throws IOException {
        this.filePath = filePath;
        load();
    }

    public void put(String key, String value) throws IOException {
        store.put(key, value);
        persist();
        System.out.println("PUT " + key + " = " + value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(store.get(key));
    }

    public void delete(String key) throws IOException {
        if (store.remove(key) != null) {
            persist();
            System.out.println("DEL " + key);
        } else {
            System.out.println("DEL " + key + " -> not found");
        }
    }

    public void list() {
        if (store.isEmpty()) {
            System.out.println("Store is empty.");
            return;
        }
        store.forEach((k, v) -> System.out.println("  " + k + " -> " + v));
    }

    private void persist() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : store.entrySet())
                pw.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    private void load() throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2)
                    store.put(parts[0], parts[1]);
            }
        }
        System.out.println("Loaded " + store.size() + " entries from disk.");
    }

    public static void main(String[] args) throws IOException {
        KeyValueStore kv = new KeyValueStore("kvstore.db");

        kv.put("name", "Samin");
        kv.put("city", "Dhaka");
        kv.put("project", "RedDrop");
        kv.put("lang", "Java");

        System.out.println("\nAll entries:");
        kv.list();

        System.out.println("\nGet 'city': " + kv.get("city").orElse("not found"));
        System.out.println("Get 'missing': " + kv.get("missing").orElse("not found"));

        System.out.println();
        kv.delete("city");
        kv.delete("missing");

        System.out.println("\nAfter delete:");
        kv.list();
    }
}