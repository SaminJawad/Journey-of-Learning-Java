import org.objectweb.asm.*;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.*;

public class BytecodeManipulation {

    static byte[] generateClass() {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, "GeneratedGreeter", null, "java/lang/Object", null);

        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        MethodVisitor greet = cw.visitMethod(Opcodes.ACC_PUBLIC, "greet", "(Ljava/lang/String;)V", null, null);
        greet.visitCode();
        greet.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        greet.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        greet.visitInsn(Opcodes.DUP);
        greet.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        greet.visitLdcInsn("Hello from bytecode, ");
        greet.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        greet.visitVarInsn(Opcodes.ALOAD, 1);
        greet.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        greet.visitLdcInsn("!");
        greet.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        greet.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;",
                false);
        greet.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        greet.visitInsn(Opcodes.RETURN);
        greet.visitMaxs(3, 2);
        greet.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        byte[] bytecode = generateClass();
        Files.write(Paths.get("GeneratedGreeter.class"), bytecode);
        System.out.println("Generated class: " + bytecode.length + " bytes");

        ClassLoader loader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) {
                return defineClass(name, bytecode, 0, bytecode.length);
            }
        };

        Class<?> clazz = loader.loadClass("GeneratedGreeter");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method greetMethod = clazz.getMethod("greet", String.class);

        greetMethod.invoke(instance, "Samin");
        greetMethod.invoke(instance, "World");

        System.out.println("Class loaded from: " + clazz.getClassLoader());
    }
}