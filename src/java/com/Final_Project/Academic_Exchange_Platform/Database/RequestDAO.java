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
public class RequestDAO {
    private static final Logger logger = Logger.getLogger(RequestDAO.class.getName());

    public boolean submitRequest(int courseId, int professionalId) {
    String query = "INSERT INTO teaching_requests (course_id, professional_id, status) VALUES (?, ?, 'Pending')";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        // Bind parameters
        ps.setInt(1, courseId);
        ps.setInt(2, professionalId);

        // Execute the query
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error submitting teaching request: " + e.getMessage());
        return false;
    }
}

    public boolean updateRequestStatus(int requestId, String status) {
        String query = "UPDATE requests SET status = ? WHERE request_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, requestId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating request status: " + e.getMessage(), e);
            return false;
        }
    }
}
