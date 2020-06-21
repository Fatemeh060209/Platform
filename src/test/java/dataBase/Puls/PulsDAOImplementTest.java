package dataBase.Puls;

import dataBase.Cleaning;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

class PulsDAOImplementTest {

    @Test
    void save() {//and load
        Cleaning.main(null);
        PulsDTO pulsDTO = new PulsDTO();
        pulsDTO.setPuls_measurements(80.22);
        pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
        pulsDTO.setPatient_id(1001);
        PulsDAOImplement pulsDAOImplement = new PulsDAOImplement();
        pulsDAOImplement.save(pulsDTO);
        List<PulsDTO> load = pulsDAOImplement.load("010380-0410");
        for (int i = 0; i < load.size(); i++) {
            PulsDTO pulsDTO1 = load.get(i);
            Assertions.assertEquals(pulsDTO1.getPatient_id(), pulsDTO.getPatient_id());
            Assertions.assertEquals(pulsDTO1.getPuls_measurements(), pulsDTO.getPuls_measurements());
            Assertions.assertEquals(pulsDTO1.getPuls_time().getTime(), pulsDTO.getPuls_time().getTime());
        }
    }
}