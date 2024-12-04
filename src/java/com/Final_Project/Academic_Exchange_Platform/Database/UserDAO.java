package com.Final_Project.Academic_Exchange_Platform.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles database operations for user accounts.
 */
public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    /**
     * Validates user login credentials.
     *
     * @param email    User email.
     * @param password User password.
     * @return User ID if credentials are valid; -1 otherwise.
     */
    public int validateLogin(String email, String password) {
        String query = "SELECT user_id FROM users WHERE email = ? AND password = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error validating login: " + e.getMessage(), e);
        }
        return -1;
    }

    /**
     * Fetches the role of a user by ID.
     *
     * @param userId User ID.
     * @return Role of the user or null if not found.
     */
    public String getUserRole(int userId) {
        String query = "SELECT role FROM users WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching user role: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Registers a new user in the system.
     *
     * @param email    User email.
     * @param password User password.
     * @param role     User role (Professional or Institution).
     * @return Status of registration (success, error, duplicate_email, invalid_role).
     */
    public String registerUser(String email, String password, String role) {
        String query = "INSERT INTO users (email, password, role) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            if (!role.equalsIgnoreCase("Professional") && !role.equalsIgnoreCase("Institution")) {
                return "invalid_role";
            }
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);

            return ps.executeUpdate() > 0 ? "success" : "error";
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) { // Duplicate email error
                return "duplicate_email";
            }
            logger.log(Level.SEVERE, "Error registering user: " + e.getMessage(), e);
        }
        return "error";
    }

    /**
     * Fetches the user's name by ID.
     *
     * @param userId User ID.
     * @return User's name or null if not found.
     */
    public String getUserName(int userId) {
        String query = "SELECT name FROM users WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching user name: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Fetches the institution ID linked to a user.
     *
     * @param userId User ID.
     * @return Institution ID or null if not found.
     */
    public Integer getInstitutionIdByUserId(int userId) {
        String query = "SELECT institution_id FROM institutions WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("institution_id");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching institution ID: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Checks if the user's profile is complete.
     *
     * @param userId User ID.
     * @return True if profile is complete, false otherwise.
     */
    public boolean isProfileCompleted(int userId) {
        String query = "SELECT CASE " +
                       "WHEN role = 'Professional' THEN EXISTS (SELECT 1 FROM professionals WHERE user_id = ?) " +
                       "WHEN role = 'Institution' THEN EXISTS (SELECT 1 FROM institutions WHERE user_id = ?) " +
                       "END AS profile_completed " +
                       "FROM users WHERE user_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("profile_completed");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking profile completion: " + e.getMessage(), e);
        }
        return false;
    }
}
