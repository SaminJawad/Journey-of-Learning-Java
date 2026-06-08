import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ObjectPool<T> {
    private final BlockingQueue<T> pool;
    private final AtomicInteger created = new AtomicInteger(0);
    private final int maxSize;
    private final Supplier<T> factory;

    public ObjectPool(int maxSize, Supplier<T> factory) {
        this.maxSize = maxSize;
        this.factory = factory;
        this.pool = new ArrayBlockingQueue<>(maxSize);
        for (int i = 0; i < maxSize; i++) {
            pool.offer(factory.get());
            created.incrementAndGet();
        }
    }

    public T acquire() throws InterruptedException {
        T obj = pool.poll(2, TimeUnit.SECONDS);
        if (obj == null)
            throw new RuntimeException("Pool exhausted — timeout.");
        System.out.println("Acquired: " + obj + " | Pool size: " + pool.size());
        return obj;
    }

    public void release(T obj) {
        pool.offer(obj);
        System.out.println("Released: " + obj + " | Pool size: " + pool.size());
    }

    public int poolSize() {
        return pool.size();
    }

    public int totalCreated() {
        return created.get();
    }

    static class DBConnection {
        private static final AtomicInteger counter = new AtomicInteger(0);
        private final int id = counter.incrementAndGet();

        @Override
        public String toString() {
            return "DBConnection#" + id;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ObjectPool<DBConnection> pool = new ObjectPool<>(3, DBConnection::new);

        System.out.println("Pool created with " + pool.totalCreated() + " connections.\n");

        DBConnection c1 = pool.acquire();
        DBConnection c2 = pool.acquire();
        DBConnection c3 = pool.acquire();

        System.out.println("\nAll connections in use. Releasing c1...\n");
        pool.release(c1);

        DBConnection c4 = pool.acquire();
        pool.release(c2);
        pool.release(c3);
        pool.release(c4);

        System.out.println("\nFinal pool size: " + pool.poolSize());
    }
}