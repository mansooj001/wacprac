package worldmap.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jason on 6/22/2016.
 */
class BaseDAO {
    static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://127.12.233.130:3306"; // localhost of 127.12.233.130
        String name = "admingeldaqa";
        String password = "nNuWCswgXPv_";
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
