package ekgSensor;

public interface EkgObservable {
    void registerObserver(EkgListener listener);
}
