import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ShardedCounter {
    private final AtomicLong[] shards;
    private final int shardCount;

    public ShardedCounter(int shardCount) {
        this.shardCount = shardCount;
        this.shards = new AtomicLong[shardCount];
        for (int i = 0; i < shardCount; i++)
            shards[i] = new AtomicLong(0);
    }

    public void increment() {
        int shard = ThreadLocalRandom.current().nextInt(shardCount);
        shards[shard].incrementAndGet();
    }

    public long get() {
        long total = 0;
        for (AtomicLong shard : shards)
            total += shard.get();
        return total;
    }

    public void printShards() {
        for (int i = 0; i < shardCount; i++)
            System.out.println("  Shard " + i + ": " + shards[i].get());
    }

    public static void main(String[] args) throws InterruptedException {
        ShardedCounter counter = new ShardedCounter(4);
        AtomicLong naiveCounter = new AtomicLong(0);

        ExecutorService executor = Executors.newFixedThreadPool(8);
        int totalIncrements = 100_000;

        for (int i = 0; i < totalIncrements; i++) {
            executor.submit(() -> {
                counter.increment();
                naiveCounter.incrementAndGet();
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("--- Sharded Counter ---");
        counter.printShards();
        System.out.println("Total (sharded): " + counter.get());
        System.out.println("Total (naive):   " + naiveCounter.get());
        System.out.println("Match: " + (counter.get() == naiveCounter.get()));
    }
}