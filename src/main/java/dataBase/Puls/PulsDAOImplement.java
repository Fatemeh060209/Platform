package dataBase.Puls;

import dataBase.Connector;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PulsDAOImplement implements PulsDAO {

    public void save(PulsDTO pulsDTO) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("INSERT INTO PULS" +
                    " (Patient_id, Puls_measurements, Puls_time) VALUES (?,?,?)");
            preparedStatement.setInt(1, pulsDTO.getPatient_id());
            preparedStatement.setDouble(2, pulsDTO.getPuls_measurements());
            preparedStatement.setTimestamp(3, pulsDTO.getPuls_time());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<PulsDTO> mapResultSetToDTOList(ResultSet resultSet) throws SQLException {
        List<PulsDTO> listPuls = new LinkedList<>();
        while (resultSet.next()) {
            PulsDTO PulsDTO = new PulsDTO();
            PulsDTO.setPatient_id(resultSet.getInt("Patient_id"));
            PulsDTO.setPuls_measurements(resultSet.getDouble("Puls_measurements"));
            PulsDTO.setPuls_time(resultSet.getTimestamp("Puls_time"));
            listPuls.add(PulsDTO);
        }
        return listPuls;
    }

    @Override
    public List<PulsDTO> load(String cpr) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter" +
                    " JOIN PULS AS P on Patienter.ID = P.Patient_id WHERE Cpr=?");
            preparedStatement.setString(1, cpr);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PulsDTO> listPuls = mapResultSetToDTOList(resultSet);
            return listPuls;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PulsDTO> load(String cpr, Timestamp start, Timestamp end) {
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT * FROM Patienter" +
                    " JOIN PULS AS P on Patienter.ID = P.Patient_id WHERE Cpr=? AND Puls_time BETWEEN ? AND ?");
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
