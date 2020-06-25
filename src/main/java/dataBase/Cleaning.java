/** @author Fatemeh & Osama & Majd*/
package dataBase;

import java.sql.SQLException;
import java.sql.Statement;

public class Cleaning {

    public static void createEkg() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("CREATE TABLE EKG (Patient_id INT," +
                    "EKG_voltage DOUBLE,EKG_time timestamp(3),FOREIGN KEY (Patient_id) REFERENCES Patienter(ID))");
            Connector.getConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEkg() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("DROP TABLE EKG");
            Connector.getConn().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createPuls() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("CREATE TABLE PULS (Patient_id INT," +
                    "Puls_measurements DOUBLE,Puls_time timestamp(3),FOREIGN KEY (Patient_id) REFERENCES Patienter(ID))");
            Connector.getConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePuls() {
        try {
            Statement statement = Connector.getConn().createStatement();
            statement.executeUpdate("DROP TABLE PULS");
            Connector.getConn().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        deleteEkg();
        createEkg();
        deletePuls();
        createPuls();
    }
}
