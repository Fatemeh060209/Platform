package dataBase.Puls;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

class PulsDAOImplementTest {

    @Test
    void save() {//and load
        PulsDTO pulsDTO = new PulsDTO();
        pulsDTO.setPuls_measurements(80.22);
        pulsDTO.setPuls_time(new Timestamp(System.currentTimeMillis()));
        pulsDTO.setPatient_id(1001);
        PulsDAOImplement pulsDAOImplement = new PulsDAOImplement();
        pulsDAOImplement.save(pulsDTO);
        List<PulsDTO> load = pulsDAOImplement.load("010380-0410");
        for (PulsDTO pulsDTOS : load) {
            System.out.println(pulsDTOS.getPuls_measurements());
            System.out.println(pulsDTOS.getPatient_id());
            System.out.println(pulsDTOS.getPuls_time());
        }
    }
}