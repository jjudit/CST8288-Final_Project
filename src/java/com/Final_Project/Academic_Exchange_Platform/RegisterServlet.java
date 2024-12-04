/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

import com.Final_Project.Academic_Exchange_Platform.Database.UserDAO;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author judit
 */

public class RegisterServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the registration page for GET requests
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (email == null || email.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        String result = userDAO.registerUser(email, password, role);

        if ("success".equals(result)) {
            request.setAttribute("message", "Registration successful! Please log in.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else if ("duplicate_email".equals(result)) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "An unexpected error occurred. Try again later.");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }
}






