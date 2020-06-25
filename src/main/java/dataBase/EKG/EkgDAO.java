/** @author Mohammed */
package dataBase.EKG;

import java.sql.Timestamp;
import java.util.List;

public interface EkgDAO {
    void save(EkgDTO EkgDTO);

    void savebatch(List<EkgDTO> batch);

    List<EkgDTO> load(String cpr);

    List<EkgDTO> load(String cpr, Timestamp start, Timestamp end);
}

