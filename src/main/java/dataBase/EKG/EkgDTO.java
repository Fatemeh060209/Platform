package dataBase.EKG;

import java.sql.Timestamp;

public class EkgDTO {

    private int Patient_id;
    private double EKG_voltage;
    private Timestamp EKG_time;

    public int getPatient_id() {
        return Patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.Patient_id = patient_id;
    }

    public double getEKG_voltage() {
        return EKG_voltage;
    }

    public void setEKG_voltage(double EKG_voltage) {
        this.EKG_voltage = EKG_voltage;
    }

    public Timestamp getEKG_time() {
        return EKG_time;
    }

    public void setEKG_time(Timestamp EKG_time) {
        this.EKG_time = EKG_time;
    }

}


