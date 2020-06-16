package sensorer.EKG;

public interface EkgObservable {
    void registerObserver(EkgListener listener);
}
