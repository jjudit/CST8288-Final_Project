-- Create Database
CREATE DATABASE AEP;

-- Use the Database
USE AEP;

-- Users Table
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('Professional', 'Institution') NOT NULL
);

-- Professionals Table
CREATE TABLE Professionals (
    professional_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    current_institution VARCHAR(255),
    academic_position VARCHAR(255),
    expertise TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Institutions Table
CREATE TABLE Institutions (
    institution_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Courses Table
CREATE TABLE Courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    institution_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    course_code VARCHAR(50) NOT NULL,
    term VARCHAR(50) NOT NULL,
    schedule ENUM('Morning', 'Afternoon', 'Evening') NOT NULL,
    delivery_method ENUM('In-Person', 'Remote', 'Hybrid') NOT NULL,
    preferred_qualifications TEXT,
    compensation DECIMAL(10,2),
    FOREIGN KEY (institution_id) REFERENCES Institutions(institution_id)
);

-- Requests Table
CREATE TABLE Requests (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    professional_id INT NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') NOT NULL,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id),
    FOREIGN KEY (professional_id) REFERENCES Professionals(professional_id)
);

-- Notifications Table
CREATE TABLE Notifications (
    notification_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
