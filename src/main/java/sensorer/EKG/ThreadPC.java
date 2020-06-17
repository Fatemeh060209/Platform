package sensorer.EKG;

public class ThreadPC implements Runnable {
    private EkgListener listener;

    public ThreadPC(EkgListener listener) {
        this.listener = listener;
    }

    public void run() {
        final PC pc = new PC();
        pc.registerObserver(this.listener);
        Thread t1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}