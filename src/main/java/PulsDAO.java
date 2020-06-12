import java.util.List;

public interface PulsDAO {
    String save(PulsDTO PulsDTO);
    void savebatch(List<PulsDTO> batch);
    PulsDTO load(PulsDTO PulsDTO);
}

