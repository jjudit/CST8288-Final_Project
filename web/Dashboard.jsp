<%-- 
    Document   : Dashboard
    Created on : Dec. 2, 2024, 3:18:12 p.m.
    Author     : judit
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%
    // Get the user's name and role from the session
    String name = (String) session.getAttribute("name");
    String role = (String) session.getAttribute("role");

    // If the name is not in the session, provide a default value
    
    if (name == null || name.isEmpty()) {
        name = "User";
    
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #444;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= name %>!</h1>

        <% if ("Professional".equalsIgnoreCase(role)) { %>
            <h2>Professional Section</h2>
            <ul>
                <li><a href="SearchServlet">Explore Teaching Opportunities</a></li>
                <li><a href="manageProfile.jsp">Manage Your Profile</a></li>
                <li><a href="notifications.jsp">View Notifications</a></li>
            </ul>
        <% } else if ("Institution".equalsIgnoreCase(role)) { %>
            <h2>Institution Section</h2>
            <ul>
                <li><a href="ManageCourse.jsp">Manage Courses</a></li>
                <li><a href="viewRequests.jsp">View Professional Requests</a></li>
                <li><a href="notifications.jsp">View Notifications</a></li>
            </ul>
        <% } else { %>
            <p style="color: red;">Invalid user role. Please contact support.</p>
        <% } %>
    </div>
</body>
</html>