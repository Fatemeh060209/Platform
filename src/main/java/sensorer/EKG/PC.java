package sensorer.EKG;

import dataBase.EKG.EkgDTO;

import java.util.LinkedList;
import java.util.List;

// This class has a list, producer (adds items to list
// and consumber (removes items).
public class PC implements EkgObservable {
    private SerialportConnector serialportConnector = new SerialportConnector(0);
    // Create a list shared by producer and consumer
    // Size of list is 2.
    LinkedList<EkgDTO> ekgDTOS = new LinkedList<>();
    int capacity = 100;
    private EkgListener ekgListener;

    // Function called by producer thread
    public void produce() throws InterruptedException {

        while (true) {
            synchronized (this) {
                // producer thread waits while list
                // is full
                while (ekgDTOS.size() == capacity)
                    wait();
                List<EkgDTO> values = serialportConnector.getData();
                // to insert the jobs in the list
                if (values != null) {
                    for (EkgDTO i : values) {
                        ekgDTOS.add(i);
                        System.out.println("Producer produced- "
                                + i);
                    }
                }
                // notifies the consumer thread that
                // now it can start consuming
                notify();

                // makes the working of program easier
                // to understand
            }
            Thread.sleep(10);
        }
    }

    // Function called by consumer thread
    public void consume() throws InterruptedException {
        while (true) {
            LinkedList<EkgDTO> consumedList;
            synchronized (this) {
                // consumer thread waits while list
                // is empty
                while (ekgDTOS.size() < 50)
                    wait();

                // to retrive the ifrst job in the list
                //listGUI.removeAll(consumedList);
                consumedList = ekgDTOS;
                ekgDTOS = new LinkedList<EkgDTO>();
                if (ekgListener != null) {
                    ekgListener.EkgNotify(consumedList);
                }
                // Wake up producer thread
                notify();

                // and sleep
            }
            //System.out.println("Consumer GUIconsumed-");
            for (EkgDTO i : consumedList) {
                System.out.println("GUInummer " + i);
            }
            Thread.sleep(10);
        }
    }

    @Override
    public void registerObserver(EkgListener listener) {
        this.ekgListener = listener;
    }
}