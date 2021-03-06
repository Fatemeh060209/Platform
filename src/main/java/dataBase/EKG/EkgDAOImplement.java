/** @author Osama & Mohammed & Fatemeh*/
package dataBase.EKG;

import dataBase.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class EkgDAOImplement implements EkgDAO {

    public void save(EkgDTO ekgDTO) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("INSERT INTO EKG" +
                    " (Patient_id, EKG_voltage, EKG_time) VALUES (?,?,?)");
            preparedStatement.setInt(1, ekgDTO.getPatient_id());
            preparedStatement.setDouble(2, ekgDTO.getEKG_voltage());
            preparedStatement.setTimestamp(3, ekgDTO.getEKG_time());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void savebatch(List<EkgDTO> batch) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("INSERT INTO EKG" +
                    " (Patient_id, EKG_voltage, EKG_time) VALUES (?,?,?)");
            for (EkgDTO ekgDTO : batch) {
                preparedStatement.setInt(1, ekgDTO.getPatient_id());
                preparedStatement.setDouble(2, ekgDTO.getEKG_voltage());
                preparedStatement.setTimestamp(3, ekgDTO.getEKG_time());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<EkgDTO> mapResultSetToDTOList(ResultSet resultSet) throws SQLException {
        List<EkgDTO> listEkg = new LinkedList<>();
        while (resultSet.next()) {
            EkgDTO ekgDTO = new EkgDTO();
            ekgDTO.setPatient_id(resultSet.getInt("Patient_id"));
            ekgDTO.setEKG_voltage(resultSet.getDouble("EKG_voltage"));
            ekgDTO.setEKG_time(resultSet.getTimestamp("EKG_time"));
            listEkg.add(ekgDTO);
        }
        return listEkg;
    }

    @Override
    public List<EkgDTO> load(String cpr) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter" +
                    " JOIN EKG AS E on Patienter.ID = E.Patient_id WHERE Cpr=? LIMIT 540");
            preparedStatement.setString(1, cpr);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EkgDTO> listEkg = mapResultSetToDTOList(resultSet);
            return listEkg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<EkgDTO> load(String cpr, Timestamp start, Timestamp end) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter" +
                    " JOIN EKG AS E on Patienter.ID = E.Patient_id WHERE Cpr=? AND EKG_time BETWEEN ? AND ?");
            preparedStatement.setString(1, cpr);
            preparedStatement.setTimestamp(2, start);
            preparedStatement.setTimestamp(3, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EkgDTO> listEkg = mapResultSetToDTOList(resultSet);
            return listEkg;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


