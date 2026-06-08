import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
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

    public static void main(String[] args) {
        LRUCache lru = new LRUCache(3);
        lru.put(1, 10);
        lru.put(2, 20);
        lru.put(3, 30);
        lru.print();

        lru.get(1);
        lru.put(4, 40);
        lru.print();

        System.out.println("Get 2: " + lru.get(2));
        System.out.println("Get 1: " + lru.get(1));
    }
}