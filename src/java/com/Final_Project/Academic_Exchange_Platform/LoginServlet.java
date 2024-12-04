package com.Final_Project.Academic_Exchange_Platform;

import com.Final_Project.Academic_Exchange_Platform.Database.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the login page for GET requests
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        int userId = userDAO.validateLogin(email, password);
        if (userId != -1) {
            String role = userDAO.getUserRole(userId);
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("role", role);

            // Fetch and set the user's name in the session
            String name = userDAO.getUserName(userId);
            session.setAttribute("name", name);

            if ("Institution".equalsIgnoreCase(role)) {
                Integer institutionId = userDAO.getInstitutionIdByUserId(userId);
                session.setAttribute("institutionId", institutionId);
            }

            if (!userDAO.isProfileCompleted(userId)) {
                request.getRequestDispatcher(role.equalsIgnoreCase("Professional") ? "ProfessionalProfile.jsp" : "InstitutionProfile.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("Dashboard.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}
