import java.util.concurrent.atomic.AtomicInteger;

public class ThreadSafeCounter {
    static int unsafeCount = 0;
    static AtomicInteger atomicCount = new AtomicInteger(0);
    static int syncCount = 0;

    static synchronized void incrementSync() {
        syncCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 1000;
        Thread[] threads = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                unsafeCount++;
                atomicCount.incrementAndGet();
                incrementSync();
            });
            threads[i].start();
        }

        for (Thread t : threads)
            t.join();

        System.out.println("Unsafe count:    " + unsafeCount);
        System.out.println("Atomic count:    " + atomicCount.get());
        System.out.println("Sync count:      " + syncCount);
    }
}