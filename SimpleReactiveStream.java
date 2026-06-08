import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class SimpleReactiveStream {

    interface Publisher<T> {
        void subscribe(Subscriber<T> subscriber);
    }

    interface Subscriber<T> {
        void onNext(T item);

        void onComplete();

        void onError(Throwable t);
    }

    static class BufferedPublisher<T> implements Publisher<T> {
        private final BlockingQueue<T> buffer;
        private final List<Subscriber<T>> subscribers = new ArrayList<>();

        BufferedPublisher(int capacity) {
            this.buffer = new ArrayBlockingQueue<>(capacity);
        }

        void emit(T item) {
            if (!buffer.offer(item)) {
                subscribers.forEach(s -> s.onError(new RuntimeException("Buffer full — backpressure!")));
                return;
            }
            subscribers.forEach(s -> s.onNext(item));
        }

        void complete() {
            subscribers.forEach(Subscriber::onComplete);
        }

        @Override
        public void subscribe(Subscriber<T> subscriber) {
            subscribers.add(subscriber);
        }
    }

    public static void main(String[] args) {
        BufferedPublisher<Integer> publisher = new BufferedPublisher<>(5);

        publisher.subscribe(new Subscriber<>() {
            public void onNext(Integer item) {
                System.out.println("Subscriber-1 received: " + item);
            }

            public void onComplete() {
                System.out.println("Subscriber-1: stream complete.");
            }

            public void onError(Throwable t) {
                System.out.println("Subscriber-1 error: " + t.getMessage());
            }
        });

        publisher.subscribe(new Subscriber<>() {
            public void onNext(Integer item) {
                System.out.println("Subscriber-2 received: " + (item * 2) + " (doubled)");
            }

            public void onComplete() {
                System.out.println("Subscriber-2: stream complete.");
            }

            public void onError(Throwable t) {
                System.out.println("Subscriber-2 error: " + t.getMessage());
            }
        });

        for (int i = 1; i <= 5; i++)
            publisher.emit(i);
        publisher.emit(99);
        publisher.complete();
    }
}