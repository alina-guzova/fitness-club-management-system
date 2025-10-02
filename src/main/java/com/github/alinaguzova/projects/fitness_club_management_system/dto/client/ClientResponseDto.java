package com.github.alinaguzova.projects.fitness_club_management_system.dto.client;

/**
 * DTO for displaying detailed client profile information to client.
 * Contains personal fields such as name, email, phone.
 * Used in client-facing endpoints to view or return updated profile data after modification.
 */

public class ClientResponseDto {

    private String name;

    private String email;

    private String phone;

    public ClientResponseDto() {
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

}
