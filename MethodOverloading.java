public class MethodOverloading {

    static double area(double radius) {
        return Math.PI * radius * radius;
    }

    static double area(double length, double width) {
        return length * width;
    }

    static double area(double base, double height, boolean triangle) {
        return 0.5 * base * height;
    }

    public static void main(String[] args) {
        System.out.printf("Circle area: %.2f%n", area(5));
        System.out.printf("Rectangle area: %.2f%n", area(4, 6));
        System.out.printf("Triangle area: %.2f%n", area(3, 8, true));
    }
}