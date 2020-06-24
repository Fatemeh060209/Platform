package dataBase.Patienter;

import dataBase.Connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PatienterDAO {

    public List<PatienterDTO> load() {
        List<PatienterDTO> cpr = new LinkedList<>();
        try {
            PreparedStatement preparedStatement = Connector.getConn().prepareStatement("SELECT ID,Cpr FROM Patienter");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PatienterDTO patienterDTO = new PatienterDTO();
                patienterDTO.setCpr(resultSet.getString("Cpr"));
                patienterDTO.setID(resultSet.getInt("ID"));
                cpr.add(patienterDTO);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return cpr;
    }
}
