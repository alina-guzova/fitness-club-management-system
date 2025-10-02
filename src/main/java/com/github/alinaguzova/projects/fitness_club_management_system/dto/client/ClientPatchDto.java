package com.github.alinaguzova.projects.fitness_club_management_system.dto.client;

import com.github.alinaguzova.projects.fitness_club_management_system.validation.PhoneNumber;
import com.github.alinaguzova.projects.fitness_club_management_system.validation.ValidEmail;
import jakarta.validation.constraints.Size;

/**
 * DTO for partial updates to client profile data via PATCH request
 * Used by both clients and administrators to modify selected fields such as name, email, phone.
 * Validated using standard and custom annotations to ensure format, length and presence.
 * Used in endpoints supporting profile editing and administrative client management.
 */

public class ClientPatchDto {

    @Size(max = 30, message = "Имя должно содержать не более 30 символов")
    private String name;

    @ValidEmail(message = "Некорректный формат email")
    @Size(max = 50, message = "Email должен содержать не более 50 символов")
    private String email;

    @PhoneNumber(message = "Некорректный формат номера телефона")
    private String phone;

    public ClientPatchDto() {
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
