package ekgSensor;

import dataBase.EKG.EkgDTO;

import java.util.LinkedList;
import java.util.List;

public class PC implements EkgObservable {
    private SerialportConnector serialportConnector = new SerialportConnector(0);
    LinkedList<EkgDTO> ekgDTOS = new LinkedList<>();
    int capacity = 400;
    private EkgListener ekgListener;

    public void produce() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (ekgDTOS.size() == capacity)
                    wait();
                List<EkgDTO> values = serialportConnector.getData();
                if (values != null) {
                    for (EkgDTO i : values) {
                        ekgDTOS.add(i);
                        /*System.out.println("Producer produced- "
                                + i.getEKG_voltage());*/
                    }
                }
                notify();
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            LinkedList<EkgDTO> consumedList;
            synchronized (this) {
                while (ekgDTOS.size() < 120)
                    wait();
                consumedList = ekgDTOS;
                ekgDTOS = new LinkedList<>();
                if (ekgListener != null) {
                    ekgListener.EkgNotify(consumedList);
                }
                notify();
            }
            /*for (EkgDTO i : consumedList) {
                System.out.println("Consumer Concumed- " + i.getEKG_voltage());
            }*/
        }
    }

    @Override
    public void registerObserver(EkgListener listener) {
        this.ekgListener = listener;
    }
}