package com.cs440.ski_management.util.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/community_ski_race_ms";
    private static final String USER = "root";
    private static String PASSWORD;
    private static Connection connection = null;

    static {
        try {
            Properties props = new Properties();
            InputStream config = DBConnection.class.getClassLoader().getResourceAsStream("config.properties");            
            props.load(config);
            PASSWORD = props.getProperty("DB_PASSWORD");
        } catch (IOException e) {
            System.err.println("Could not load config.properties");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
//                System.out.println("Database connected successfully");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Connection failed");
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