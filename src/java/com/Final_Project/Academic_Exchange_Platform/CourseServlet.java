/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

import com.Final_Project.Academic_Exchange_Platform.Database.CourseDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author judit
 */
@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer institutionId = (Integer) session.getAttribute("institutionId");

        if (institutionId == null) {
            unauthorizedAccess(request, response);
            return;
        }

        // Fetch and display the courses
        try {
            request.setAttribute("courses", courseDAO.getCoursesByInstitutionId(institutionId));
            request.getRequestDispatcher("ManageCourse.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            log("Error fetching courses: " + e.getMessage(), e);
            request.setAttribute("error", "Failed to load courses. Please try again later.");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer institutionId = (Integer) session.getAttribute("institutionId");

        if (institutionId == null) {
            unauthorizedAccess(request, response);
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            request.setAttribute("error", "Invalid action. Please try again.");
            request.getRequestDispatcher("ManageCourse.jsp").forward(request, response);
            return;
        }

        try {
            switch (action.toLowerCase()) {
                case "create" -> handleCreateCourse(request, institutionId);
                case "delete" -> handleDeleteCourse(request, institutionId);
      
                default -> request.setAttribute("error", "Unknown action: " + action);
            }
        } catch (Exception e) {
            log("Error processing action: " + e.getMessage(), e);
            request.setAttribute("error", "An error occurred while processing your request. Please try again.");
        }

        // Refresh the course list and forward to the management page
        try {
            request.setAttribute("courses", courseDAO.getCoursesByInstitutionId(institutionId));
        } catch (Exception e) {
            log("Error fetching courses after action: " + e.getMessage(), e);
        }
        request.getRequestDispatcher("ManageCourse.jsp").forward(request, response);
    }

    private void handleCreateCourse(HttpServletRequest request, int institutionId) throws Exception {
        String title = request.getParameter("title");
        String courseCode = request.getParameter("course_code");
        String term = request.getParameter("term");
        String schedule = request.getParameter("schedule");
        String deliveryMethod = request.getParameter("delivery_method");
        String preferredQualifications = request.getParameter("preferred_qualifications");
        String compensationStr = request.getParameter("compensation");

        // Validate inputs
        if (title == null || courseCode == null || term == null || schedule == null || deliveryMethod == null || compensationStr == null) {
            throw new IllegalArgumentException("All course fields are required.");
        }

        try {
            double compensation = Double.parseDouble(compensationStr);
            boolean isCreated = courseDAO.createCourse(institutionId, title, courseCode, term, schedule, deliveryMethod, preferredQualifications, compensation);
            request.setAttribute("message", isCreated ? "Course created successfully!" : "Failed to create course.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid compensation format.", e);
        }
    }

    private void handleDeleteCourse(HttpServletRequest request, int institutionId) throws Exception {
        String courseIdStr = request.getParameter("course_id");

        if (courseIdStr == null || courseIdStr.isEmpty()) {
            throw new IllegalArgumentException("Course ID is required for deletion.");
        }

        try {
            int courseId = Integer.parseInt(courseIdStr);
            boolean isDeleted = courseDAO.deleteCourse(courseId, institutionId);
            request.setAttribute("message", isDeleted ? "Course deleted successfully!" : "Failed to delete course.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid course ID format.", e);
        }
    }

    

    private void unauthorizedAccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "Unauthorized access. Please log in.");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
}
