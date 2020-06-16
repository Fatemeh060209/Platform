package dataBase;

import java.sql.Timestamp;

public class PulsDTO {

    private Timestamp Puls_time;
    private int Patient_ID;
    private double Puls_Målinger;

    public Timestamp getPuls_time() {
        return Puls_time;
    }

    public void setPuls_time(Timestamp puls_time) {
        Puls_time = puls_time;
    }

    public int getPatient_ID() {
        return Patient_ID;
    }

    public void setPatient_ID(int patient_ID) {
        Patient_ID = patient_ID;
    }

    public double getPuls_Målinger() {
        return Puls_Målinger;
    }

    public void setPuls_Målinger(double puls_Målinger) {
        Puls_Målinger = puls_Målinger;
    }
}




