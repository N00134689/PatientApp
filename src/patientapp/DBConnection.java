package patientapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection sConnection;

    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        String host, db, user, password;
        
        host = "daneel";
        db = "n00134689";
        user = "N00134689";
        password = "N00134689";
        
        if (sConnection == null || sConnection.isClosed()) {
            String url = "jdbc:mysql://" + host + "/" + db;
            Class.forName("com.mysql.jdbc.Driver");
            sConnection = DriverManager.getConnection(url, user, password);
        }

        return sConnection;
    }
}
