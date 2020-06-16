package sensorer;

public interface EkgObservable {
    void registerObserver(EkgListener listener);
}
