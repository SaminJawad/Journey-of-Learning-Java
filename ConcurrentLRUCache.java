import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConcurrentLRUCache {
    private final Map<Integer, Integer> cache;

    public ConcurrentLRUCache(int capacity) {
        this.cache = Collections.synchronizedMap(
                new LinkedHashMap<>(capacity, 0.75f, true) {
                    @Override
                    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                        return size() > capacity;
                    }
                });
    }

    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cache.put(key, value);
    }

    public void print() {
        System.out.println("Cache: " + cache);
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLRUCache lru = new ConcurrentLRUCache(3);

        Thread t1 = new Thread(() -> {
            lru.put(1, 10);
            lru.put(2, 20);
        });
        Thread t2 = new Thread(() -> {
            lru.put(3, 30);
            lru.put(4, 40);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        lru.print();
        System.out.println("Get 1: " + lru.get(1));
        System.out.println("Get 3: " + lru.get(3));
    }
}