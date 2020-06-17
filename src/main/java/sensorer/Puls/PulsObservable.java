package sensorer.Puls;

import javaFx.AppGUIController;

public interface PulsObservable {
    void registerObserver(AppGUIController listener);
}
