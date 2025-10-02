package com.github.alinaguzova.projects.fitness_club_management_system.dto.client;

import java.time.LocalDateTime;

/**
 * DTO for exposing detailed client information to administrators
 * Includes internal fields such as id, name, email, phone, registration date.
 * Used in admin-facing endpoints for client management and audit purposes
 */

public class AdminClientResponseDto {

    private int id;

    private String name;

    private String email;

    private String phone;

    private LocalDateTime registrationDate;

    public AdminClientResponseDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
