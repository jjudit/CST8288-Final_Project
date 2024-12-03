/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

/**
 *
 * @author judit
 */
public class Course {
    
    private int courseId;
    private int institutionId;
    private String title;
    private String courseCode;
    private String term;
    private String schedule;
    private String deliveryMethod;
    private String preferredQualifications;
    private double compensation;

    // Constructor
    public Course(int courseId, int institutionId, String title, String courseCode, String term, String schedule,
                  String deliveryMethod, String preferredQualifications, double compensation) {
        this.courseId = courseId;
        this.institutionId = institutionId;
        this.title = title;
        this.courseCode = courseCode;
        this.term = term;
        this.schedule = schedule;
        this.deliveryMethod = deliveryMethod;
        this.preferredQualifications = preferredQualifications;
        this.compensation = compensation;
    }

    // Getter and Setter Methods
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getPreferredQualifications() {
        return preferredQualifications;
    }

    public void setPreferredQualifications(String preferredQualifications) {
        this.preferredQualifications = preferredQualifications;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }

    
}
    
    

