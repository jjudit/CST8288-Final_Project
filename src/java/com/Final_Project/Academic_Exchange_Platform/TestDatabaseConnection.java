/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author judit
 */
public class TestDatabaseConnection {
    public static void main(String[] args) {
        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");

            // Connect to the database
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/AEP?useSSL=false&serverTimezone=UTC",
                "aepUser", "password1234"
            );
            System.out.println("Connection successful: " + connection);

        } catch (ClassNotFoundException | SQLException e) {
        }
    }
}