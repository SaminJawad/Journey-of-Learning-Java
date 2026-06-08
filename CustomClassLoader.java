import java.io.*;
import java.nio.file.*;

public class CustomClassLoader extends ClassLoader {

    private final String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String filePath = classPath + File.separator + name.replace('.', File.separatorChar) + ".class";
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class not found: " + name, e);
        }
    }

    public static void main(String[] args) throws Exception {
        CustomClassLoader loader = new CustomClassLoader(".");

        Class<?> clazz = loader.loadClass("HelloWorld");
        Object instance = clazz.getDeclaredConstructor().newInstance();

        System.out.println("Loaded class: " + clazz.getName());
        System.out.println("ClassLoader:  " + clazz.getClassLoader());
        System.out.println("Parent:       " + clazz.getClassLoader().getParent());

        clazz.getMethod("main", String[].class).invoke(instance, (Object) new String[] {});
    }
}