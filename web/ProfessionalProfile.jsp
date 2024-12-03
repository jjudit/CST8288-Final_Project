<%-- 
    Document   : ProfessionalProfile
    Created on : Dec. 2, 2024, 2:32:24 p.m.
    Author     : judit
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Professional Profile</title>
    <style>
        /* General Reset */
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            color: #333;
        }

        /* Container Styling */
        .form-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Heading */
        h1 {
            text-align: center;
            color: #444;
            margin-bottom: 20px;
        }

        /* Form Styling */
        .profile-form {
            display: flex;
            flex-direction: column;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        textarea {
            resize: none;
            height: 100px;
        }

        input:focus, textarea:focus {
            border-color: #007BFF;
            outline: none;
        }

        /* Button Styling */
        .submit-btn {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .submit-btn:hover {
            background-color: #0056b3;
        }

        /* Responsive Design */
        @media (max-width: 600px) {
            .form-container {
                margin: 20px;
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>Complete Your Professional Profile</h1>
        <form action="ProfileServlet" method="POST" class="profile-form">
            <input type="hidden" name="userType" value="professional" />
            
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required placeholder="Enter your name" />
            </div>

            <div class="form-group">
                <label for="institution">Current Institution:</label>
                <input type="text" id="institution" name="current_institution" placeholder="Enter your current institution" />
            </div>

            <div class="form-group">
                <label for="position">Academic Position:</label>
                <input type="text" id="position" name="academic_position" placeholder="Enter your academic position" />
            </div>

            <div class="form-group">
                <label for="expertise">Area of Expertise:</label>
                <textarea id="expertise" name="expertise" placeholder="Describe your area of expertise"></textarea>
            </div>

            <button type="submit" class="submit-btn">Save Profile</button>
        </form>
    </div>
</body>
</html>
