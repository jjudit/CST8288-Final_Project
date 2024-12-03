/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

import com.Final_Project.Academic_Exchange_Platform.Database.CourseDAO;
import com.Final_Project.Academic_Exchange_Platform.Database.RequestDAO;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author judit
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();
    private final RequestDAO requestDAO = new RequestDAO();

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
        // Fetch institutions and terms from the database
        List<String> institutions = courseDAO.getAllInstitutions();
        List<String> terms = courseDAO.getAllTerms();
        List<String> schedules = List.of("Morning", "Afternoon", "Evening");
        List<String> deliveryMethods = List.of("In-Person", "Remote", "Hybrid");

        // Set attributes for the JSP
        request.setAttribute("institutions", institutions != null ? institutions : List.of());
        request.setAttribute("terms", terms != null ? terms : List.of());
        request.setAttribute("schedules", schedules);
        request.setAttribute("deliveryMethods", deliveryMethods);

        // Forward to the search page
        request.getRequestDispatcher("Search.jsp").forward(request, response);
    } catch (IOException | ServletException e) {
        log("Error in fetching search options: " + e.getMessage(), e);
        request.setAttribute("error", "Failed to load search options. Please try again later.");
        request.getRequestDispatcher("Error.jsp").forward(request, response);
    }
}

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    try {
        if ("search".equalsIgnoreCase(action)) {
            System.out.println("Search action initiated");
            handleSearch(request, response);
        } else if ("requestToTeach".equalsIgnoreCase(action)) {
            System.out.println("Request to Teach action initiated");
            handleRequestToTeach(request, response);
        } else {
            System.out.println("Invalid action: " + action);
            request.setAttribute("error", "Invalid action.");
            request.getRequestDispatcher("Error.jsp").forward(request, response);
        }
    } catch (Exception e) {
        request.setAttribute("error", "An unexpected error occurred.");
        request.getRequestDispatcher("Error.jsp").forward(request, response);
    }
}

private void handleSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String institution = request.getParameter("institution");
    String courseCode = request.getParameter("course_code");
    String courseTitle = request.getParameter("course_title");
    String term = request.getParameter("term");
    String schedule = request.getParameter("schedule");
    String deliveryMethod = request.getParameter("delivery_method");

    // Log input parameters
    System.out.println("Institution: " + institution);
    System.out.println("Course Code: " + courseCode);
    System.out.println("Course Title: " + courseTitle);
    System.out.println("Term: " + term);
    System.out.println("Schedule: " + schedule);
    System.out.println("Delivery Method: " + deliveryMethod);

    // Fetch matching courses
    List<Course> courses = courseDAO.searchCourses(institution, courseCode, courseTitle, term, schedule, deliveryMethod);
    System.out.println("Courses fetched: " + courses);

    // Set the courses as a request attribute and forward to JSP
    if (courses == null || courses.isEmpty()) {
        request.setAttribute("message", "No courses found for the given criteria.");
    }
    request.setAttribute("courses", courses);
    request.getRequestDispatcher("Search.jsp").forward(request, response);
}

    private void handleRequestToTeach(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
}