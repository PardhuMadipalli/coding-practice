package javapractice;

public class MethodOverloadingDemo {

    int sum(int a, int b) {
        return a+b;
    }

    int sum(int a, double b) {
        return (int) (a+b);
    }

    int sum(int a, int b, int c) {
        return a+b+c;
    }

    // DOES NOT WORK
    // The only difference between this and the first methods is the return type. It is not allowed.
    /*

    double sum(int a, int b) {
        return (double)a+b;
    }

     */

    // Allowed. Because here we changed parameter types too.
    double sum(double a, int b) {
        return a+b;
    }

    void diff(int a, int b) {
        System.out.println("Ints method");
        System.out.println(a-b);
    }

    void diff(long a, long b) {
        System.out.println("longs method");
        System.out.println(a-b);
    }

    void diffCrossArgs(int a, long b) {
        System.out.println("int first, long second");
        System.out.println(a-b);
    }

    void diffCrossArgs(long a, int b) {
        System.out.println("int first, long second");
        System.out.println(a-b);
    }

    public static void main(String... args) {
        MethodOverloadingDemo demo = new MethodOverloadingDemo();

        demo.diff(50, 30); // will invoke ints method

        // Ambiguous compile error
//        demo.diffCrossArgs(40, 30);

        // Unambiguous error
        demo.diffCrossArgs(40, 30L);
        demo.diffCrossArgs(40L, 30);

    }
}
