package wenda;


import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.function.Consumer;

class MyThread extends Thread{
    private int tid;
    public MyThread(int tid){
        this.tid=tid;
    }

    @Override
    public void run() {
        try {
            for (int i=0;i<10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d",tid,i));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
public class MultiThreadTests {
    public static void testThread(){
        for (int i=0;i<10;i++){
            new MyThread(i).start();
        }
    }

    private static Object obj=new Object();
    public static void testSynchronized1(){
        synchronized (obj){
            try {
                for (int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T3 %d",i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testSynchronized2(){
        synchronized (obj){
            try {
                for (int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T4 %d",i));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static  void testSynchronized(){
        for (int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }

    static class Consumer implements Runnable{
        private BlockingQueue<String> q;
        public Consumer(BlockingQueue<String> q){
            this.q=q;
        }

        @Override
        public void run() {
            try {
                while (true){
                    System.out.println(Thread.currentThread().getName()+":"+q.take());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Producer implements Runnable{
        private BlockingQueue<String> q;
        public Producer(BlockingQueue<String> q){
            this.q=q;
        }
        @Override
        public void run() {
            try {
                for (int i=0;i<100;i++){
                    Thread.sleep(10);
                    q.put(String.valueOf(i));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void testBlockingDeque(){
        BlockingQueue<String> q=new ArrayBlockingQueue<String>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q), "Consumer1").start();
        new Thread(new Consumer(q), "Consumer2").start();
    }

    private static ThreadLocal<Integer> threadLocalUserIds=new ThreadLocal<>();
    private static int userId;
    public static void testThreadLocal(){
        for(int i=0;i<10;i++){
            final int finalI=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        threadLocalUserIds.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal:"+threadLocalUserIds.get());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void testExecutor(){
        //ExecutorService service=Executors.newSingleThreadExecutor();
        ExecutorService service=Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor1:"+i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });



        service.submit(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor2:"+i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

       // service.shutdown();
        while (!service.isTerminated()){
            try{
                Thread.sleep(1000);
                System.out.println("Wait for termination");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void mainx(String[] argv){
        //testThread();
        //testBlockingDeque();
        //testThreadLocal();
        testExecutor();
    }
}
