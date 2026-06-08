import java.util.Scanner;

public class TemperatureConverter {

    static double celsiusToFahrenheit(double c) {
        return (c * 9 / 5) + 32;
    }

    static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double celsius = scanner.nextDouble();
        double fahrenheit = scanner.nextDouble();

        System.out.printf("%.2f C = %.2f F%n", celsius, celsiusToFahrenheit(celsius));
        System.out.printf("%.2f F = %.2f C%n", fahrenheit, fahrenheitToCelsius(fahrenheit));

        scanner.close();
    }
}