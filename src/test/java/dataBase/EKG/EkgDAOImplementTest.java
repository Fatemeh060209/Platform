package dataBase.EKG;

import dataBase.Cleaning;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

class EkgDAOImplementTest {

    @Test
    void savebatch() {//and load
            Cleaning.main(null);
            List<EkgDTO> ekgDTOS = new LinkedList<>();
            for (int i = 0; i < 10; i++) {
                EkgDTO ekgDTO = new EkgDTO();
                ekgDTO.setPatient_id(1001);
                ekgDTO.setEKG_time(new Timestamp(System.currentTimeMillis()));
                ekgDTO.setEKG_voltage(i);
                ekgDTOS.add(ekgDTO);
            }
            EkgDAOImplement ekgDAOImplement = new EkgDAOImplement();
            ekgDAOImplement.savebatch(ekgDTOS);
            List<EkgDTO> load = ekgDAOImplement.load("010380-0410");
            for (int i = 0; i < load.size(); i++) {
                EkgDTO ekgDTO1 = load.get(i);
                EkgDTO ekgDTO = ekgDTOS.get(i);
                Assertions.assertEquals(ekgDTO1.getPatient_id(), ekgDTO.getPatient_id());
                Assertions.assertEquals(ekgDTO1.getEKG_voltage(), ekgDTO.getEKG_voltage());
                Assertions.assertEquals(ekgDTO1.getEKG_time().getTime(), ekgDTO.getEKG_time().getTime());
            }
        }
    }
