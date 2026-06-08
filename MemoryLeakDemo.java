import java.util.*;

public class MemoryLeakDemo {

    static class LeakyCache {
        private final Map<String, byte[]> cache = new HashMap<>();

        void add(String key) {
            cache.put(key, new byte[1024 * 1024]);
        }

        int size() {
            return cache.size();
        }
    }

    static class FixedCache {
        private final int maxSize;
        private final Map<String, byte[]> cache;

        FixedCache(int maxSize) {
            this.maxSize = maxSize;
            this.cache = new LinkedHashMap<>(maxSize, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, byte[]> eldest) {
                    return size() > maxSize;
                }
            };
        }

        void add(String key) {
            cache.put(key, new byte[1024 * 1024]);
        }

        int size() {
            return cache.size();
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Leaky Cache (unbounded) ---");
        LeakyCache leaky = new LeakyCache();
        for (int i = 0; i < 20; i++)
            leaky.add("key" + i);
        System.out.println("Leaky cache size: " + leaky.size() + " entries (~20MB held)");

        System.out.println("\n--- Fixed Cache (bounded LRU, max 5) ---");
        FixedCache fixed = new FixedCache(5);
        for (int i = 0; i < 20; i++)
            fixed.add("key" + i);
        System.out.println("Fixed cache size: " + fixed.size() + " entries (~5MB max)");

        System.out.println("\n--- WeakReference Demo ---");
        WeakHashMap<String, byte[]> weakCache = new WeakHashMap<>();
        String key = new String("weakKey");
        weakCache.put(key, new byte[1024]);
        System.out.println("Before GC hint: " + weakCache.size());
        key = null;
        System.gc();
        System.out.println("After GC hint:  " + weakCache.size() + " (may be 0 if GC ran)");
    }
}