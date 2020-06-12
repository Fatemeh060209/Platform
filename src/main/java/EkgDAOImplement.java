import java.sql.*;
import java.util.List;

public class EkgDAOImplement implements EkgDAO {

    Connection conn = Connector.getConn();

    public String save(EkgDTO ekgDTO) {
        try {
            int[] id = new int[20];
            id = ekgDTO.getId();
            int[] ekg = new int[20];
            ekg = ekgDTO.getEkgList();
            Timestamp[] tid = new Timestamp[20];
            tid = ekgDTO.getTid();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Målinger (ID,Ekg,Tid) values (?,?,?)");
            for (int i = 0; i < ekg.length; i++) {
                statement.setInt(1, id[i]);
                statement.setInt(2, ekg[i]);
                statement.setTimestamp(3, tid[i]);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void savebatch(List<EkgDTO> batch) {
    }

    public EkgDTO load(EkgDTO EkgDTO) {
        try {
            Statement statement = conn.createStatement();
            ResultSet show_tables = statement.executeQuery("SELECT * FROM Patienter, EKG WHERE Patienter.ID=EKG.Patient_ID AND Patienter.Cpr=?");
            while (show_tables.next()) {
                EkgDTO.setEkgList(new int[]{show_tables.getInt("EKG")});
            }
            return EkgDTO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


