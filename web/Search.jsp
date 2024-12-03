<%-- 
    Document   : Search
    Created on : Dec. 3, 2024, 2:39:26 p.m.
    Author     : judit
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, com.Final_Project.Academic_Exchange_Platform.Course" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Explore Teaching Opportunities</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
            background: #f9f9f9;
            border-radius: 5px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background: #eee;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Explore Teaching Opportunities</h1>
        <form method="post" action="SearchServlet">
            <input type="hidden" name="action" value="search">
            <label for="institution">Institution:</label>
            <select name="institution" id="institution">
                <option value="">All</option>
                <% List<String> institutions = (List<String>) request.getAttribute("institutions");
                   if (institutions != null) {
                       for (String institution : institutions) { %>
                           <option value="<%= institution %>"><%= institution %></option>
                       <% }
                   } %>
            </select>
            <label for="course_code">Course Code:</label>
            <input type="text" name="course_code" id="course_code">
            <label for="course_title">Course Title:</label>
            <input type="text" name="course_title" id="course_title">
            <label for="term">Term:</label>
            <select name="term" id="term">
                <option value="">All</option>
                <% List<String> terms = (List<String>) request.getAttribute("terms");
                   for (String term : terms) { %>
                       <option value="<%= term %>"><%= term %></option>
                   <% } %>
            </select>
            <label for="schedule">Schedule:</label>
            <select name="schedule" id="schedule">
                <option value="">All</option>
                <% List<String> schedules = (List<String>) request.getAttribute("schedules");
                   for (String schedule : schedules) { %>
                       <option value="<%= schedule %>"><%= schedule %></option>
                   <% } %>
            </select>
            <label for="delivery_method">Delivery Method:</label>
            <select name="delivery_method" id="delivery_method">
                <option value="">All</option>
                <% List<String> deliveryMethods = (List<String>) request.getAttribute("deliveryMethods");
                   for (String method : deliveryMethods) { %>
                       <option value="<%= method %>"><%= method %></option>
                   <% } %>
            </select>
            <button type="submit">Search</button>
        </form>

        <h2>Search Results</h2>
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Institution</th>
                    <th>Term</th>
                    <th>Schedule</th>
                    <th>Delivery Method</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% List<Course> courses = (List<Course>) request.getAttribute("courses");
                   if (courses != null && !courses.isEmpty()) {
                       for (Course course : courses) { %>
                           <tr>
                               <td><%= course.getTitle() %></td>
                               <td><%= course.getInstitutionId() %></td>
                               <td><%= course.getTerm() %></td>
                               <td><%= course.getSchedule() %></td>
                               <td><%= course.getDeliveryMethod() %></td>
                               <td>
                                   <form method="post" action="SearchServlet">
                                       <input type="hidden" name="action" value="requestToTeach">
                                       <input type="hidden" name="course_id" value="<%= course.getCourseId() %>">
                                       <button type="submit">Request to Teach</button>
                                   </form>
                               </td>
                           </tr>
                       <% }
                   } else { %>
                       <tr>
                           <td colspan="6">No courses found.</td>
                       </tr>
                   <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
