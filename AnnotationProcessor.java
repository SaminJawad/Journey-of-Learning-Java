import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
}

class UserService {
    @NotNull
    private String username = "samin";

    @NotNull
    private String email = null;

    @LogExecutionTime
    public void processUser() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Processing user: " + username);
    }

    @LogExecutionTime
    public void fetchData() throws InterruptedException {
        Thread.sleep(50);
        System.out.println("Fetching data...");
    }
}

public class AnnotationProcessor {

    static void validateNotNull(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                if (field.get(obj) == null)
                    System.out.println("Validation failed: @NotNull field '" + field.getName() + "' is null.");
                else
                    System.out.println("Validation passed: '" + field.getName() + "' = " + field.get(obj));
            }
        }
    }

    static void invokeLogged(Object obj) throws Exception {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.currentTimeMillis();
                method.invoke(obj);
                long end = System.currentTimeMillis();
                System.out.println("@LogExecutionTime -> " + method.getName() + " took " + (end - start) + "ms");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        UserService service = new UserService();

        System.out.println("--- @NotNull Validation ---");
        validateNotNull(service);

        System.out.println("\n--- @LogExecutionTime ---");
        invokeLogged(service);
    }
}