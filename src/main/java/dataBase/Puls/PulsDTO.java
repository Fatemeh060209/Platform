package dataBase.Puls;

import java.sql.Timestamp;

public class PulsDTO {

    private Timestamp Puls_time;
    private int Patient_ID;
    private double Puls_Measurements;

    public Timestamp getPuls_time() {
        return Puls_time;
    }

    public void setPuls_time(Timestamp puls_time) {
        this.Puls_time = puls_time;
    }

    public int getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(int patient_ID) {
        this.Patient_ID = patient_ID;
    }

    public double getPuls_Measurements() {
        return Puls_Measurements;
    }

    public void setPuls_Measurements(double puls_Measurements) {
        this.Puls_Measurements = puls_Measurements;
    }
}




