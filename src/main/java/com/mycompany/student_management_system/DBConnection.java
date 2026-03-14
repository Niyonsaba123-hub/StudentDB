package com.mycompany.student_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management",
                    "root",
                    "Mami@1234"
            );
            
            System.out.println("Database Connected");
            
        } catch (ClassNotFoundException | SQLException e) {
            
            System.out.println("Connection Failed");
            
        }

        return conn;
    }
}