package thread;

public class RunStart implements Runnable {
    @Override
    public void run(){
        for(int i=1;i<5;i++){
            try{Thread.sleep(500);}catch(InterruptedException e){System.err.println(e);}
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        RunStart t1=new RunStart();
        RunStart t2=new RunStart();

        t1.run(); // Should not use run
        t2.run();
    }
}
