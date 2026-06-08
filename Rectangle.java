public class Rectangle {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double area() {
        return length * width;
    }

    public double perimeter() {
        return 2 * (length + width);
    }

    @Override
    public String toString() {
        return "Rectangle[length=" + length + ", width=" + width + "]";
    }

    public static void main(String[] args) {
        Rectangle r = new Rectangle(5, 3);
        System.out.println(r);
        System.out.println("Area: " + r.area());
        System.out.println("Perimeter: " + r.perimeter());
    }
}