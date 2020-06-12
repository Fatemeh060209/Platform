import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection conn;

    private Connector() {
    }

    public static Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://db.diplomportal.dk/s190869?" +
                        "user=s190869&password=dI4BwIK9dMJsfrJkyQkLI");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}


