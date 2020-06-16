package sensorer;

public class ThreadPC implements Runnable {
    private EkgListener listener;

    public ThreadPC(EkgListener listener) {
        this.listener = listener;
    }

    public void run() {
        // Object of a class that has both produce()
        // and consume() methods
        final PC pc = new PC();
        pc.registerObserver(this.listener);

        // Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        t1.start();
        t2.start();
        // t1 finishes before t2
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}