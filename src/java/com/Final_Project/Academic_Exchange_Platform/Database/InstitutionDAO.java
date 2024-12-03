/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author judit
 */
public class InstitutionDAO {
    private static final Logger logger = Logger.getLogger(InstitutionDAO.class.getName());

    public boolean createProfile(int userId, String name, String address) {
        String query = "INSERT INTO institutions (user_id, name, address) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, address);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating institution profile: " + e.getMessage(), e);
            return false;
        }
    }
}
