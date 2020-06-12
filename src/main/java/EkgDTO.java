import java.sql.Timestamp;

public class EkgDTO {

    private Timestamp EKG_time;
    private int Patient_Id;
    private double EKG_voltage;

    public Timestamp getEKG_time() {
        return EKG_time;
    }

    public void setEKG_time(Timestamp EKG_time) {
        this.EKG_time = EKG_time;
    }

    public int getPatient_Id() {
        return Patient_Id;
    }

    public void setPatient_Id(int patient_Id) {
        Patient_Id = patient_Id;
    }

    public double getEKG_voltage() {
        return EKG_voltage;
    }

    public void setEKG_voltage(double EKG_voltage) {
        this.EKG_voltage = EKG_voltage;
    }
}


