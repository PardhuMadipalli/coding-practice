package javapractice;

public class DemoInterface1 {

    private int num;

    DemoInterface1(int num) {
        this.num = num;
    }

    void increment()  {
        this.num++;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getId() + " " + this.num);
    }
}
