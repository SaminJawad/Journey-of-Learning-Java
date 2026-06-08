import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class JobScheduler {
    static class Job {
        String name;
        Runnable task;
        long intervalMs;
        long nextRunTime;

        Job(String name, Runnable task, long intervalMs) {
            this.name = name;
            this.task = task;
            this.intervalMs = intervalMs;
            this.nextRunTime = System.currentTimeMillis();
        }
    }

    private final PriorityQueue<Job> jobQueue;
    private final ScheduledExecutorService executor;
    private final List<Job> jobs = new ArrayList<>();

    public JobScheduler() {
        this.jobQueue = new PriorityQueue<>(Comparator.comparingLong(j -> j.nextRunTime));
        this.executor = Executors.newScheduledThreadPool(2);
    }

    public void schedule(String name, Runnable task, long intervalMs) {
        Job job = new Job(name, task, intervalMs);
        jobs.add(job);
        jobQueue.offer(job);
    }

    public void start() {
        executor.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            while (!jobQueue.isEmpty() && jobQueue.peek().nextRunTime <= now) {
                Job job = jobQueue.poll();
                System.out.println("[" + LocalTime.now().withNano(0) + "] Running: " + job.name);
                executor.submit(job.task);
                job.nextRunTime = now + job.intervalMs;
                jobQueue.offer(job);
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stop() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        JobScheduler scheduler = new JobScheduler();

        scheduler.schedule("HeartbeatJob", () -> System.out.println("  >> Heartbeat ping"), 1000);
        scheduler.schedule("CleanupJob", () -> System.out.println("  >> Cleanup running"), 2000);
        scheduler.schedule("ReportJob", () -> System.out.println("  >> Report generated"), 3000);

        scheduler.start();
        Thread.sleep(6000);
        scheduler.stop();
        System.out.println("Scheduler stopped.");
    }
}