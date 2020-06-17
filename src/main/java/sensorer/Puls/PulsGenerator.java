package sensorer.Puls;

import dataBase.Puls.PulsDTO;
import javaFx.AppGUIController;

import java.sql.Timestamp;

public class PulsGenerator implements PulsObservable, Runnable {

    private PulsListener observer; // En Interface er en slags type, ligesom en klasse.
    // Som et resultat kan du bruge en Interface som type for en variabel, parameter eller metodeleverdi.

    public void run() { // run metoden danner mållinger ved hjælp af vores fantastiske algoritme fra 1. semester
        while (true) { // uendelig while true løkke der generere temperatur målinger
            PulsDTO pulsDTO = new PulsDTO();
            double min = 140;
            double max = 220;
            double value = (Math.random() * ((max - min)) + min);
            double puls = (value * 4.0 / 50.0) + 24.0;
            pulsDTO.setPuls_measurements(puls);
            pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
            if (observer != null) { // når varibalen observeren ikke er null, dvs når der kommer målinger
                observer.PulsNotify(pulsDTO); // der bliver informeret i AppGUIcontroller, at der er kommet noget
                // data/målinger fra sensoren.
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
