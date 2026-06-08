public class StaticVsInstance {
    static int count = 0;
    int id;

    public StaticVsInstance() {
        count++;
        this.id = count;
    }

    public static void main(String[] args) {
        StaticVsInstance o1 = new StaticVsInstance();
        StaticVsInstance o2 = new StaticVsInstance();
        StaticVsInstance o3 = new StaticVsInstance();

        System.out.println("Total instances: " + StaticVsInstance.count);
        System.out.println("o1 id: " + o1.id);
        System.out.println("o2 id: " + o2.id);
        System.out.println("o3 id: " + o3.id);
    }
}