package sensorer.Puls;

import dataBase.Puls.PulsDTO;
import javaFx.AppGUIController;

import java.sql.Timestamp;

public class PulsGenerator implements PulsObservable, Runnable {

    private PulsListener observer;

    public void run() {
        while (true) {
            PulsDTO pulsDTO = new PulsDTO();
            double min = 140;
            double max = 220;
            double value = (Math.random() * ((max - min)) + min);
            double puls = (value * 4.0 / 50.0) + 54.0;
            pulsDTO.setPuls_measurements(puls);
            pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
            if (observer != null) {
                observer.PulsNotify(pulsDTO);
            }
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerObserver(AppGUIController listener) {
        this.observer = listener;
    }
}
