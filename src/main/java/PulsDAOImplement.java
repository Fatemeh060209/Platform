import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PulsDAOImplement implements PulsDAO {

    public void save(PulsDTO pulsDTO) {
        Connection conn = Connector.getConn();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO EKG (Patient_ID, Puls_Målinger, Puls_time) VALUES (?,?,?)");
            preparedStatement.setInt(1, pulsDTO.getPatient_ID());
            preparedStatement.setDouble(2, pulsDTO.getPuls_Målinger());
            preparedStatement.setTimestamp(3, pulsDTO.getPuls_time());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void savebatch(List<PulsDTO> batch) {
        Connection conn = Connector.getConn();
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = conn.prepareStatement("INSERT INTO EKG (Patient_ID, Puls_Målinger, Puls_time) VALUES (?,?,?)");
                for (PulsDTO pulsDTO : batch) {
                    preparedStatement.setInt(1, pulsDTO.getPatient_ID());
                    preparedStatement.setDouble(2, pulsDTO.getPuls_Målinger());
                    preparedStatement.setTimestamp(3, pulsDTO.getPuls_time());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    @Override
    public List<PulsDTO> load(String cpr) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter JOIN PULS AS P on Patienter.ID = P.Patient_id WHERE Cpr=?");
            preparedStatement.setString(1, cpr);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PulsDTO> listPuls = mapResultSetToDTOList(resultSet);
            return listPuls;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<PulsDTO> mapResultSetToDTOList(ResultSet resultSet) throws SQLException {
        List<PulsDTO> listPuls = new LinkedList<>();
        while (resultSet.next()) {
            PulsDTO PulsDTO = new PulsDTO();
            PulsDTO.setPatient_ID(resultSet.getInt("Patient_ID"));
            PulsDTO.setPuls_Målinger(resultSet.getDouble("Puls_Målinger"));
            PulsDTO.setPuls_time(resultSet.getTimestamp("Puls_time"));
            listPuls.add(PulsDTO);
        }
        return listPuls;
    }
    @Override
    public List<PulsDTO> load(String cpr, Timestamp start, Timestamp end) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter JOIN PULS AS P on Patienter.ID = P.Patient_id WHERE Cpr=? AND Puls_time BETWEEN ? AND ?");
            preparedStatement.setString(1, cpr);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PulsDTO> listPuls = mapResultSetToDTOList(resultSet);
            return listPuls;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
