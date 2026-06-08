import java.io.IOException;
import java.nio.file.*;

public class FileWatcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path dir = Paths.get(".");
        WatchService watchService = FileSystems.getDefault().newWatchService();

        dir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);

        System.out.println("Watching directory: " + dir.toAbsolutePath());

        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event: " + event.kind() + " | File: " + event.context());
            }
            if (!key.reset())
                break;
        }
    }
}