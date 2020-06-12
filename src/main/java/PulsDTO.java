import java.sql.Timestamp;

public class PulsDTO {

    private Timestamp[] tid=new Timestamp[20];
    private int[] Id=new int[20];
    private int[] pulsList = new int[20];


    public Timestamp[] getTid() {
        return tid;
    }

    public void setTid(Timestamp[] tid) {
        this.tid = tid;
    }

    public int[] getId() {
        return Id;
    }

    public void setId(int[] id) {
        Id = id;
    }

    public int[] getPulsList() {
        return pulsList;
    }

    public void setPulsList(int[] pulsList) {
        this.pulsList = pulsList;
    }
}


