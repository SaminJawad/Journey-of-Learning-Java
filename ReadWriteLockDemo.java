import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private static int sharedData = 0;
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    static void read(String threadName) {
        lock.readLock().lock();
        try {
            System.out.println(threadName + " reads: " + sharedData);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.readLock().unlock();
        }
    }

    static void write(String threadName, int value) {
        lock.writeLock().lock();
        try {
            sharedData = value;
            System.out.println(threadName + " writes: " + sharedData);
            Thread.sleep(150);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread w1 = new Thread(() -> write("Writer-1", 42));
        Thread r1 = new Thread(() -> read("Reader-1"));
        Thread r2 = new Thread(() -> read("Reader-2"));
        Thread w2 = new Thread(() -> write("Writer-2", 99));
        Thread r3 = new Thread(() -> read("Reader-3"));

        w1.start();
        r1.start();
        r2.start();
        w2.start();
        r3.start();
        w1.join();
        r1.join();
        r2.join();
        w2.join();
        r3.join();
    }
}