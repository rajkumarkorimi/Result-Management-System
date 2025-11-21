package com.result;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/ORCLPDB1",
                "hiber",   // username
                "hiber"    // password
            );

            System.out.println("✅ Database connection established");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
            throw e; // important to throw so controllers know connection failed
        }
        return con;
    }
}
