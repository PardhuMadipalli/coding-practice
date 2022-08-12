package javapractice.multithreading;


import java.util.stream.IntStream;

public class SyncStatementsDemo {
    private static class Printer {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        private void unrelatedMethod1(String name){
            synchronized (lock1) {
                IntStream.range(0, 5).forEach(a->{
                    System.out.printf("print1 %s - %d\n", name, a);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        private void unrelatedMethod2(String name){
            synchronized (lock2) {
                IntStream.range(0, 5).forEach(a->{
                    System.out.printf("print2 %s - %d\n", name, a);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    private static class TestThread extends Thread {

        boolean sync;
        int num;

        TestThread(String name, boolean sync, int num){
            super(name);
            this.sync = sync;
            this.num = num;
        }

        @Override
        public void run(){
            try {
                if(sync)
                    if(num==1) javapractice.multithreading.Printer.print1(this.getName());
                    else javapractice.multithreading.Printer.print2(this.getName());
                else
                    javapractice.multithreading.Printer.printNonSync(this.getName());

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();
        Thread t1 = new Thread(() -> printer.unrelatedMethod1("t1"));
        Thread t2 = new Thread(() -> printer.unrelatedMethod2("t2"));

        Thread t3 = new Thread(() -> printer.unrelatedMethod1("t3"));
        Thread t4 = new Thread(() -> printer.unrelatedMethod2("t4"));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
        System.out.printf("Main final statement.%n");
    }
}
