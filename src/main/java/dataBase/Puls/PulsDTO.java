/** @author Mohammed */
package dataBase.Puls;

import java.sql.Timestamp;

public class PulsDTO {

    private int Patient_id;
    private double Puls_measurements;
    private Timestamp Puls_time;



    public int getPatient_id() {
        return Patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.Patient_id = patient_id;
    }

    public double getPuls_measurements() {
        return Puls_measurements;
    }

    public void setPuls_measurements(double puls_measurements) {
        this.Puls_measurements = puls_measurements;
    }

    public Timestamp getPuls_time() {
        return Puls_time;
    }

    public void setPuls_time(Timestamp puls_time) {
        this.Puls_time = puls_time;
    }
}




