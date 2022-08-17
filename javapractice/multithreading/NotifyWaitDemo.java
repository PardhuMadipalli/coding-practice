package javapractice.multithreading;

// Java program to Illustrate notify() method in Thread
// Synchronization.

import java.util.concurrent.TimeUnit;

// Class 1
// Thread1
// Helper class extending Thread class
public class NotifyWaitDemo {

    // Main driver method
    public static void main(String[] args) throws InterruptedException {

        // Creating object(thread) of class 2
        Thread2 objB = new Thread2();

        // Starting the thread
        objB.start();

        synchronized (objB)
        {

            // Try block to check for exceptions
            try {

                // Display message only
                System.out.println(
                        "Waiting for Thread 2 to complete...");

                // wait() method for thread to be in waiting
                // state
                objB.wait();
            }

            // Catch block to handle the exceptions
            catch (Exception e) {

                // Print the exception along with line
                // number using printStackTrace() method
                e.printStackTrace();
            }

            // Print and display the total threads on the
            // console
            System.out.println("Total is: " + objB.total);
        }
    }
}
// Class 2
// Thread2
// Helper class extending Thread class
class Thread2 extends Thread {

    int total;

    // run() method which is called automatically when
    // start() is initiated for the same
    // @Override
    public void run()
    {

        synchronized (this)
        {
            System.out.println("Inside run method");

            // iterating over using the for loo
            for (int i = 0; i < 10; i++) {

//                System.out.println("total: " + total);
                total += i;
            }

            // Waking up the thread in waiting state
            // using notify() method
            this.notify();
            System.out.println("sleeping for 5 seconds after notify");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Done waiting for 5 seconds");
        }
    }
}

