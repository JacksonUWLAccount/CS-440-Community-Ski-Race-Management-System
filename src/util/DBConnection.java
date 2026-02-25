package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/community_ski_race_ms";
    private static final String USER = "root";
    private static String PASSWORD;
    private static Connection connection = null;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties"));
            PASSWORD = props.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            System.err.println("Could not load config.properties - make sure it exists in project root");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found. Did you add the JAR?");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Connection failed. Check URL, username, and password.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}