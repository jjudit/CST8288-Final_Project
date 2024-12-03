/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform.Database;

import com.Final_Project.Academic_Exchange_Platform.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author judit
 */
public class CourseDAO {
    private static final Logger logger = Logger.getLogger(CourseDAO.class.getName());

    public boolean createCourse(int institutionId, String title, String courseCode, String term, String schedule, String deliveryMethod, String preferredQualifications, double compensation) {
        String query = "INSERT INTO courses (institution_id, title, course_code, term, schedule, delivery_method, preferred_qualifications, compensation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, institutionId);
            ps.setString(2, title);
            ps.setString(3, courseCode);
            ps.setString(4, term);
            ps.setString(5, schedule);
            ps.setString(6, deliveryMethod);
            ps.setString(7, preferredQualifications);
            ps.setDouble(8, compensation);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating course: " + e.getMessage(), e);
            return false;
        }
    }

    public boolean updateCourse(int courseId, int institutionId, String title, String courseCode, String term, String schedule, String deliveryMethod, String preferredQualifications, double compensation) {
        String query = "UPDATE courses SET title = ?, course_code = ?, term = ?, schedule = ?, delivery_method = ?, preferred_qualifications = ?, compensation = ? WHERE course_id = ? AND institution_id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, courseCode);
            ps.setString(3, term);
            ps.setString(4, schedule);
            ps.setString(5, deliveryMethod);
            ps.setString(6, preferredQualifications);
            ps.setDouble(7, compensation);
            ps.setInt(8, courseId);
            ps.setInt(9, institutionId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating course: " + e.getMessage(), e);
            return false;
        }
    }

   public boolean deleteCourse(int courseId, int institutionId) {
    String query = "DELETE FROM courses WHERE course_id = ? AND institution_id = ?";
    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        ps.setInt(1, courseId);
        ps.setInt(2, institutionId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.err.println("Error deleting course: " + e.getMessage());
        return false;
    }
}

   
    public List<Course> getCoursesByInstitutionId(int institutionId) {
        String query = "SELECT * FROM courses WHERE institution_id = ?";
        List<Course> courses = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, institutionId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getInt("institution_id"),
                        rs.getString("title"),
                        rs.getString("course_code"),
                        rs.getString("term"),
                        rs.getString("schedule"),
                        rs.getString("delivery_method"),
                        rs.getString("preferred_qualifications"),
                        rs.getDouble("compensation")
                    );
                    courses.add(course);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching courses: " + e.getMessage(), e);
        }
        return courses;
    }
    
   public List<Course> searchCourses(String institution, String courseCode, String courseTitle, String term, String schedule, String deliveryMethod) {
    String query = "SELECT * FROM courses c " +
                   "JOIN institutions i ON c.institution_id = i.institution_id " +
                   "WHERE (? IS NULL OR i.name = ?) AND " +
                   "(? IS NULL OR c.course_code = ?) AND " +
                   "(? IS NULL OR c.title = ?) AND " +
                   "(? IS NULL OR c.term = ?) AND " +
                   "(? IS NULL OR c.schedule = ?) AND " +
                   "(? IS NULL OR c.delivery_method = ?)";
    List<Course> courses = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        // Bind parameters
        ps.setString(1, institution);
        ps.setString(2, institution);
        ps.setString(3, courseCode);
        ps.setString(4, courseCode);
        ps.setString(5, courseTitle);
        ps.setString(6, courseTitle);
        ps.setString(7, term);
        ps.setString(8, term);
        ps.setString(9, schedule);
        ps.setString(10, schedule);
        ps.setString(11, deliveryMethod);
        ps.setString(12, deliveryMethod);

        System.out.println("Executing query: " + ps.toString());
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course course = new Course(
                    rs.getInt("course_id"),
                    rs.getInt("institution_id"),
                    rs.getString("title"),
                    rs.getString("course_code"),
                    rs.getString("term"),
                    rs.getString("schedule"),
                    rs.getString("delivery_method"),
                    rs.getString("preferred_qualifications"),
                    rs.getDouble("compensation")
                );
                courses.add(course);
            }
        }
    } catch (SQLException e) {
    }

    return courses;
}
    
    public List<String> getAllTerms() {
    String query = "SELECT DISTINCT term FROM courses";
    List<String> terms = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            terms.add(rs.getString("term"));
        }
    } catch (SQLException e) {
        System.err.println("Error fetching terms: " + e.getMessage());
    }

    return terms;
}
    
    
    public List<String> getAllInstitutions() {
    String query = "SELECT name FROM institutions";
    List<String> institutions = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            institutions.add(rs.getString("name"));
        }
    } catch (SQLException e) {
        System.err.println("Error fetching institutions: " + e.getMessage());
    }

    return institutions;
}
}