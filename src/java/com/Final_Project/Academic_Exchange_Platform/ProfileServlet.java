/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

import com.Final_Project.Academic_Exchange_Platform.Database.InstitutionDAO;
import com.Final_Project.Academic_Exchange_Platform.Database.ProfessionalDAO;
import com.Final_Project.Academic_Exchange_Platform.Database.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author judit
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private final ProfessionalDAO professionalDAO = new ProfessionalDAO();
    private final InstitutionDAO institutionDAO = new InstitutionDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null || role == null) {
            request.setAttribute("error", "User not logged in. Please log in.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        // Redirect to appropriate profile page based on the role
        String targetPage = "Professional".equalsIgnoreCase(role) ? "ProfessionalProfile.jsp" : "InstitutionProfile.jsp";
        request.getRequestDispatcher(targetPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null || role == null) {
            request.setAttribute("error", "User not logged in. Please log in.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }

        boolean isProfileSaved = false;

        try {
            if ("Professional".equalsIgnoreCase(role)) {
                isProfileSaved = saveProfessionalProfile(request, userId);
            } else if ("Institution".equalsIgnoreCase(role)) {
                isProfileSaved = saveInstitutionProfile(request, userId);
            }

            if (isProfileSaved) {
                boolean isProfileCompleted = userDAO.isProfileCompleted(userId);
                if (isProfileCompleted) {
                    request.setAttribute("message", "Profile saved successfully!");
                    request.getRequestDispatcher("Dashboard.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "Failed to mark profile as completed. Please try again.");
                    redirectToProfilePage(request, response, role);
                }
            } else {
                request.setAttribute("error", "Failed to save profile. Please try again.");
                redirectToProfilePage(request, response, role);
            }
        } catch (IOException | ServletException e) {
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            redirectToProfilePage(request, response, role);
        }
    }

    private boolean saveProfessionalProfile(HttpServletRequest request, Integer userId) {
        String name = request.getParameter("name");
        String currentInstitution = request.getParameter("current_institution");
        String academicPosition = request.getParameter("academic_position");
        String expertise = request.getParameter("expertise");

        if (name == null || name.isEmpty() || currentInstitution == null || currentInstitution.isEmpty() ||
            academicPosition == null || academicPosition.isEmpty() || expertise == null || expertise.isEmpty()) {
            return false;
        }

        return professionalDAO.createProfile(userId, name, currentInstitution, academicPosition, expertise);
    }

    private boolean saveInstitutionProfile(HttpServletRequest request, Integer userId) {
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        if (name == null || name.isEmpty() || address == null || address.isEmpty()) {
            return false;
        }

        return institutionDAO.createProfile(userId, name, address);
    }

    private void redirectToProfilePage(HttpServletRequest request, HttpServletResponse response, String role) throws ServletException, IOException {
        String targetPage = "Professional".equalsIgnoreCase(role) ? "ProfessionalProfile.jsp" : "InstitutionProfile.jsp";
        request.getRequestDispatcher(targetPage).forward(request, response);
    }
}
