import java.util.*;
import java.util.concurrent.*;

public class LRUTTLCache<K, V> {
    private static class Entry<V> {
        V value;
        long expiryTime;

        Entry(V value, long ttlMs) {
            this.value = value;
            this.expiryTime = System.currentTimeMillis() + ttlMs;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final int capacity;
    private final long ttlMs;
    private final Map<K, Entry<V>> cache;
    private final ScheduledExecutorService cleaner;

    public LRUTTLCache(int capacity, long ttlMs) {
        this.capacity = capacity;
        this.ttlMs = ttlMs;
        this.cache = Collections.synchronizedMap(
                new LinkedHashMap<>(capacity, 0.75f, true) {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<K, Entry<V>> eldest) {
                        return size() > capacity;
                    }
                });
        this.cleaner = Executors.newSingleThreadScheduledExecutor();
        this.cleaner.scheduleAtFixedRate(this::evictExpired, ttlMs, ttlMs, TimeUnit.MILLISECONDS);
    }

    public void put(K key, V value) {
        cache.put(key, new Entry<>(value, ttlMs));
    }

    public Optional<V> get(K key) {
        Entry<V> entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            return Optional.empty();
        }
        return Optional.of(entry.value);
    }

    private void evictExpired() {
        cache.entrySet().removeIf(e -> e.getValue().isExpired());
        System.out.println("[Cleaner] Evicted expired entries. Cache size: " + cache.size());
    }

    public int size() {
        return cache.size();
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        LRUTTLCache<String, String> cache = new LRUTTLCache<>(3, 2000);

        cache.put("a", "Apple");
        cache.put("b", "Banana");
        cache.put("c", "Cherry");

        System.out.println("Get a: " + cache.get("a"));
        System.out.println("Get b: " + cache.get("b"));

        cache.put("d", "Date");
        System.out.println("After LRU eviction, Get c: " + cache.get("c"));

        System.out.println("\nWaiting for TTL expiry...");
        Thread.sleep(3000);

        System.out.println("Get a after TTL: " + cache.get("a"));
        System.out.println("Get b after TTL: " + cache.get("b"));
        System.out.println("Cache size: " + cache.size());

        cache.shutdown();
    }
}