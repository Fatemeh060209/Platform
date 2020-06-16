package dataBase.Puls;

import java.sql.Timestamp;
import java.util.List;

public interface PulsDAO {
    void save(PulsDTO PulsDTO);
    List<PulsDTO> load(String cpr);
    List<PulsDTO> load(String cpr, Timestamp start, Timestamp end);
}

