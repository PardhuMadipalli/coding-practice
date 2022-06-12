// ABC class implements the interface Runnable  
class ABC implements Runnable
{
    public void run()
    {

// try-catch block  
        try
        {
// moving thread t2 to the state timed waiting  
            Thread.sleep(100);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }


        System.out.println("Executing: " + Thread.currentThread().getName());
        System.out.println("At this time " + ThreadState.t1.getName() + " is " + ThreadState.t1.getState());

// try-catch block  
        try
        {
            System.out.println("before sleeping " + Thread.currentThread().getName());
            Thread.sleep(200);
            System.out.println("invoking notify in " + Thread.currentThread().getName());
            this.notify();
            System.out.println("INVOKED notify");
            Thread.sleep(400);
            System.out.println("after sleeping " + Thread.currentThread().getName());
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
    }
}

// ThreadState class implements the interface Runnable  
public class ThreadState implements Runnable
{
    public static Thread t1;
    public static ThreadState obj;

    // main method
    public static void main(String[] argvs)
    {
// creating an object of the class ThreadState  
        obj = new ThreadState();
        t1 = new Thread(obj);

// thread t1 is spawned   
// The thread t1 is currently in the NEW state.  
        System.out.println("The state of thread t1: " + t1.getName() + " after spawning it - " + t1.getState());

// invoking the start() method on   
// the thread t1  
        t1.start();

// thread t1 is moved to the Runnable state  
        System.out.println("The state of thread t1 after invoking the method start() on it - " + t1.getState());
    }

    @Override
    public void run()
    {
        ABC myObj = new ABC();
        Thread t2 = new Thread(myObj);

// thread t2 is created and is currently in the NEW state.  
        System.out.println("The state of thread t2: " + t2.getName() + " after spawning it - " + t2.getState());
        t2.start();

// thread t2 is moved to the runnable state  
        System.out.println("the state of thread t2 after calling the method start() on it - " + t2.getState());

// try-catch block for the smooth flow of the  program  
        try
        {
// moving the thread t1 to the state timed waiting   
            Thread.sleep(200);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }

        System.out.println("The state of thread:"  + t2.getName() + " after invoking the method sleep() on it - "+ t2.getState() );

// try-catch block for the smooth flow of the  program  
        try
        {
// waiting for thread t2 to complete its execution
            System.out.println(Thread.currentThread().getName() + " is waiting for " + t2.getName());
            t2.join();
            System.out.println(Thread.currentThread().getName() + " is DONE waiting for " + t2.getName());
        }
        catch (InterruptedException ie)
        {
            System.err.println(ie.getMessage());
        }
        System.out.println("The state of thread t2 when it has completed it's execution - " + t2.getState());
    }

}  