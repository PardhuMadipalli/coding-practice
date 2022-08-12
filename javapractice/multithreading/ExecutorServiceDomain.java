package javapractice.multithreading;

class Printer {
    synchronized static void print1(String name) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.printf("print1 %s - %d\n", name, i);
            Thread.sleep(1000);
        }
    }

    synchronized static void print2(String name) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.printf("print2 %s - %d\n", name, i);
            Thread.sleep(1000);
        }
    }

    static void printNonSync(String name) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            if (i%4==0) System.out.printf("Non syc Thread %s - %d\n", name, i);
            Thread.sleep(20);
        }
    }

}

class TestThread extends Thread {

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
                if(num==1) Printer.print1(this.getName());
                else Printer.print2(this.getName());
            else
                Printer.printNonSync(this.getName());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


public class ExecutorServiceDomain {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new TestThread("first", true, 1);
        Thread t2 = new TestThread("second", true, 2);

        Thread t3 = new TestThread("Third", false, 1);
        Thread t4 = new TestThread("fourth", false, 1);
        t4.setPriority(10);
        t3.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }
}