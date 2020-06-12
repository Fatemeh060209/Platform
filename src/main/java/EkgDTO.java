import java.sql.Timestamp;

public class EkgDTO {

    private Timestamp[] tid=new Timestamp[20];
    private int[] Id=new int[20];
    private int[] ekgList = new int[20];


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

    public int[] getEkgList() {
        return ekgList;
    }

    public void setEkgList(int[] ekgList) {
        this.ekgList = ekgList;
    }
}


