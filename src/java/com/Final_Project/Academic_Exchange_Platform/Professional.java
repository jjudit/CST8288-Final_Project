/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Final_Project.Academic_Exchange_Platform;

/**
 *
 * @author judit
 */
public class Professional {
    private int professionalId;
    private int userId;
    private String name;
    private String currentInstitution;
    private String academicPosition;
    private String expertise;

    // Constructor
    public Professional(int professionalId, int userId, String name, String currentInstitution, String academicPosition, String expertise) {
        this.professionalId = professionalId;
        this.userId = userId;
        this.name = name;
        this.currentInstitution = currentInstitution;
        this.academicPosition = academicPosition;
        this.expertise = expertise;
    }

    // Getters / Setters
    public int getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(int professionalId) {
        this.professionalId = professionalId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentInstitution() {
        return currentInstitution;
    }

    public void setCurrentInstitution(String currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    public String getAcademicPosition() {
        return academicPosition;
    }

    public void setAcademicPosition(String academicPosition) {
        this.academicPosition = academicPosition;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

  
}
