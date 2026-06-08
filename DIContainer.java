import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
@interface Inject {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Component {
}

@Component
class EmailService {
    public void send(String msg) {
        System.out.println("[EmailService] Sending: " + msg);
    }
}

@Component
class UserRepository {
    public String findUser(int id) {
        return "User#" + id;
    }
}

@Component
class UserService {
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Inject
    public UserService(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void processUser(int id) {
        String user = userRepository.findUser(id);
        System.out.println("[UserService] Processing: " + user);
        emailService.send("Welcome, " + user + "!");
    }
}

public class DIContainer {
    private final Map<Class<?>, Object> registry = new HashMap<>();

    public <T> void register(Class<T> clazz) throws Exception {
        if (registry.containsKey(clazz))
            return;

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Constructor<?> target = Arrays.stream(constructors)
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .findFirst()
                .orElse(constructors[0]);

        Class<?>[] paramTypes = target.getParameterTypes();
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            register(paramTypes[i]);
            params[i] = registry.get(paramTypes[i]);
        }

        Object instance = target.newInstance(params);
        registry.put(clazz, instance);
        System.out.println("[DI] Registered: " + clazz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        return (T) registry.get(clazz);
    }

    public static void main(String[] args) throws Exception {
        DIContainer container = new DIContainer();

        System.out.println("--- Registering Components ---");
        container.register(UserService.class);

        System.out.println("\n--- Running Application ---");
        UserService userService = container.get(UserService.class);
        userService.processUser(1);
        userService.processUser(2);
    }
}