/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform.Database;


import com.Final_Project.Academic_Exchange_Platform.Professional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author judit
 */
public class ProfessionalDAO {
    private static final Logger logger = Logger.getLogger(ProfessionalDAO.class.getName());

    public boolean createProfile(int userId, String name, String institution, String position, String expertise) {
        String query = "INSERT INTO professionals (user_id, name, current_institution, academic_position, expertise) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, name);
            ps.setString(3, institution);
            ps.setString(4, position);
            ps.setString(5, expertise);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating professional profile: " + e.getMessage(), e);
            return false;
        }
    }

    public Professional getProfile(int userId) {
        String query = "SELECT * FROM professionals WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Professional(
                        rs.getInt("professional_id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("current_institution"),
                        rs.getString("academic_position"),
                        rs.getString("expertise")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching professional profile: " + e.getMessage(), e);
        }
        return null; // Return null if profile not found
    }
}
