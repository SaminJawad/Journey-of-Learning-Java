
// Plugin interface — save as Plugin.java
public interface Plugin {
    String name();
    void execute();
}

// --- Plugin implementations ---

// HelloPlugin.java
public class HelloPlugin implements Plugin {
    @Override
    public String name() {
        return "HelloPlugin";
    }

    @Override
    public void execute() {
        System.out.println("[HelloPlugin] Hello from plugin!");
    }
}

// MathPlugin.java
public class MathPlugin implements Plugin {
    @Override
    public String name() {
        return "MathPlugin";
    }

    @Override
    public void execute() {
        System.out.println("[MathPlugin] PI = " + Math.PI);
    }
}

// TimePlugin.java
import java.time.LocalDateTime;

public class TimePlugin implements Plugin {
    @Override public String name()    { return "TimePlugin"; }
    @Override public void execute()   { System.out.println("[TimePlugin] Now: " + LocalDateTime.now()); }
}

// --- Main loader ---
// Register plugins in: META-INF/services/Plugin (one class name per line)

import java.util.ServiceLoader;

public class PluginSystem {
    public static void main(String[] args) {
        ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class);

        List<Plugin> plugins = new ArrayList<>();
        loader.forEach(plugins::add);

        if (plugins.isEmpty()) {
            System.out.println("No plugins found.");
            return;
        }

        System.out.println("Discovered " + plugins.size() + " plugin(s):\n");
        for (Plugin plugin : plugins) {
            System.out.println("Running: " + plugin.name());
            plugin.execute();
            System.out.println();
        }
    }
}