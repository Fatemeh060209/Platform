package sensorer.Puls;

import javaFx.AppGUIController;

public interface PulsObservable {
    void registerObserver(AppGUIController listener); // AppGUIcontroller registrerer sig som listener(observere) i sensoren
}
