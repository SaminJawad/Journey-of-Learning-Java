import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private final int maxTokens;
    private final long refillIntervalMs;
    private AtomicInteger tokens;
    private long lastRefillTime;

    public RateLimiter(int maxTokens, long refillIntervalMs) {
        this.maxTokens = maxTokens;
        this.refillIntervalMs = refillIntervalMs;
        this.tokens = new AtomicInteger(maxTokens);
        this.lastRefillTime = System.currentTimeMillis();
    }

    synchronized boolean allowRequest() {
        refill();
        if (tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        if (now - lastRefillTime >= refillIntervalMs) {
            tokens.set(maxTokens);
            lastRefillTime = now;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new RateLimiter(3, 1000);

        for (int i = 1; i <= 8; i++) {
            System.out.println("Request " + i + ": " + (limiter.allowRequest() ? "ALLOWED" : "BLOCKED"));
            Thread.sleep(200);
        }
    }
}