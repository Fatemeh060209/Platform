import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EkgDAOTest {

    @org.junit.jupiter.api.Test
    void save() {
        EkgDTO ekgDTO = new EkgDTO();
        ekgDTO.setEKG_time(new Timestamp(System.currentTimeMillis()));
        ekgDTO.setEKG_voltage(999.99);
        ekgDTO.setPatient_Id(1001);
        EkgDAOImplement ekgDAOImplement = new EkgDAOImplement();
        ekgDAOImplement.save(ekgDTO);
        List<EkgDTO> load = ekgDAOImplement.load("010380-0410");
        for (EkgDTO ekgDTO1 : load) {
            System.out.println(ekgDTO1.getEKG_voltage());
        }
    }

    @org.junit.jupiter.api.Test
    void savebatch() {
    }

    @org.junit.jupiter.api.Test
    void load() {
    }

    @org.junit.jupiter.api.Test
    void testLoad() {
    }
}