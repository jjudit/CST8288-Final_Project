<%-- 
    Document   : ManageCourse
    Created on : Dec. 2, 2024, 6:59:10 p.m.
    Author     : judit
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Final_Project.Academic_Exchange_Platform.Course" %>

<%
    // Retrieve the list of courses and any success/error messages
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    String successMessage = (String) request.getAttribute("successMessage");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Courses</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            text-align: center;
            color: #333;
        }
        .message {
            text-align: center;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
            color: #333;
        }
        .form-container {
            margin-top: 30px;
            padding: 20px;
            border-radius: 5px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
        }
        .form-container input, .form-container select, .form-container textarea, .form-container button {
            display: block;
            width: 100%;
            margin: 10px 0;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }
        .form-container button {
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
        }
        .form-container button:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function confirmDeletion() {
            return confirm("Are you sure you want to delete this course?");
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Manage Courses</h1>

        <!-- Display success or error messages -->
        <% if (successMessage != null) { %>
            <div class="message success"><%= successMessage %></div>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="message error"><%= errorMessage %></div>
        <% } %>

        <!-- Section to Create a New Course -->
        <div class="form-container">
            <h2>Create New Course</h2>
            <form action="CourseServlet" method="post">
                <input type="hidden" name="action" value="create">

                <label for="title">Course Title:</label>
                <input type="text" id="title" name="title" placeholder="Enter course title" required>

                <label for="course_code">Course Code:</label>
                <input type="text" id="course_code" name="course_code" placeholder="Enter course code" required>

                <label for="term">Term:</label>
                <input type="text" id="term" name="term" placeholder="e.g., Fall 2024" required>

                <label for="schedule">Schedule:</label>
                <select id="schedule" name="schedule" required>
                    <option value="">-- Select Schedule --</option>
                    <option value="Morning">Morning</option>
                    <option value="Afternoon">Afternoon</option>
                    <option value="Evening">Evening</option>
                </select>

                <label for="delivery_method">Delivery Method:</label>
                <select id="delivery_method" name="delivery_method" required>
                    <option value="">-- Select Method --</option>
                    <option value="In-Person">In-Person</option>
                    <option value="Remote">Remote</option>
                    <option value="Hybrid">Hybrid</option>
                </select>

                <label for="preferred_qualifications">Preferred Qualifications:</label>
                <textarea id="preferred_qualifications" name="preferred_qualifications" rows="3" placeholder="Enter qualifications" required></textarea>

                <label for="compensation">Compensation (in CAD):</label>
                <input type="number" id="compensation" name="compensation" step="0.01" placeholder="Enter compensation" required>

                <button type="submit">Create Course</button>
            </form>
        </div>

        <!-- Section to List Existing Courses -->
        <h2>Existing Courses</h2>
        <table>
            <tr>
                <th>Title</th>
                <th>Course Code</th>
                <th>Term</th>
                <th>Schedule</th>
                <th>Delivery Method</th>
                <th>Preferred Qualifications</th>
                <th>Compensation</th>
                <th>Actions</th>
            </tr>
            <% if (courses != null && !courses.isEmpty()) {
                for (Course course : courses) { %>
                    <tr>
                        <td><%= course.getTitle() %></td>
                        <td><%= course.getCourseCode() %></td>
                        <td><%= course.getTerm() %></td>
                        <td><%= course.getSchedule() %></td>
                        <td><%= course.getDeliveryMethod() %></td>
                        <td><%= course.getPreferredQualifications() %></td>
                        <td><%= course.getCompensation() %></td>
                        <td>
                            <!-- Edit and Delete buttons -->
                            <form action="CourseServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="course_id" value="<%= course.getCourseId() %>">
                                <button type="submit">Edit</button>
                            </form>
                            <form action="CourseServlet" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="course_id" value="<%= course.getCourseId() %>">
                                <button type="submit" onclick="return confirmDeletion();">Delete</button>
                            </form>
                        </td>
                    </tr>
            <% } } else { %>
                <tr>
                    <td colspan="8" style="text-align:center;">No courses available.</td>
                </tr>
            <% } %>
        </table>
    </div>
</body>
</html>