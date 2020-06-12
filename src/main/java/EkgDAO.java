import java.util.List;

public interface EkgDAO{
    String save(EkgDTO EkgDTO);
    void savebatch(List<EkgDTO> batch);
    EkgDTO load(EkgDTO EkgDTO);
}

