public class App {
    public static void main(String[] args) throws Exception {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            int a = 156156 % 2;
        }
        long e = System.currentTimeMillis();
        System.out.println(e - s);
        s = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            int a = 156156 ^ 1;
        }
        e = System.currentTimeMillis();
        System.out.println(e - s);
    }
}
