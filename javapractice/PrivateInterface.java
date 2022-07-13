package javapractice;

public class PrivateInterface {
    private interface InnerInterface {
        void f();
    }

    private class InnerClass1 implements InnerInterface {
        public void f() {
            System.out.println("From InnerClass1");
        }
    }

    private class InnerClass2 implements InnerInterface {
        public void f() {
            System.out.println("From InnerClass2");
        }
    }

    public static void main(String[] args) {
        PrivateInterface pi = new PrivateInterface();
        pi.new InnerClass1().f();
        pi.new InnerClass2().f();
    }
}
