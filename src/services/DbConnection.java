package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    static String url="jdbc:mysql://localhost:3306/movieticketbooking";
    static String name="root";
    static String password="Deepakad";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,name,password);
    }
}
