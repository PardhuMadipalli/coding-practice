package javapractice.multithreading;

public class PrintZeroEvenOddsMultiThreads {
    public static void main(String... args) throws InterruptedException {
        PrinterZeroOddEven p = new PrinterZeroOddEven(6);

        Thread t1 = new Thread(() -> {
            try {
                p.printZeros();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                p.printOdds();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                p.printEvens();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}

class PrinterZeroOddEven {

    int n;
    int lastPrintedNumber;
    boolean isLastPrintedZero;
    boolean isNReached;

    PrinterZeroOddEven(int n) {
        this.n = n;
        isNReached=false;
        isLastPrintedZero=false;
    }

    synchronized void printZeros() throws InterruptedException {
        while(!isNReached) {
            if(!isLastPrintedZero) {
                System.out.println(0);
                isLastPrintedZero = true;
            }
            notifyAll();
            wait();
        }
    }

    synchronized void printOdds() throws InterruptedException {
        while(!isNReached) {
            if(isLastPrintedZero && lastPrintedNumber%2==0) {
                System.out.println(++lastPrintedNumber);
                isNReached = lastPrintedNumber == n;
                isLastPrintedZero = false;
            }
            notifyAll();
            if(!isNReached)
                wait();
        }
    }

    synchronized void printEvens() throws InterruptedException {
        while(!isNReached) {
            if(isLastPrintedZero && lastPrintedNumber%2!=0) {
                System.out.println(++lastPrintedNumber);
                isNReached = lastPrintedNumber == n;
                isLastPrintedZero = false;
            }
            notifyAll();
            if(!isNReached)
                wait();
        }
    }

}
