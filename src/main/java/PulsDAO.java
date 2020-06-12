import java.sql.Timestamp;
import java.util.List;

public interface PulsDAO {
    void save(PulsDTO PulsDTO);
    void savebatch(List<PulsDTO> batch);
    List<PulsDTO> load(String cpr);
    List<PulsDTO> load(String cpr, Timestamp start, Timestamp end);
}

