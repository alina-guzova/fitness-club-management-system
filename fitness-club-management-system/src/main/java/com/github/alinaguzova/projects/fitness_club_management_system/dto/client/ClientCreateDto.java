package com.github.alinaguzova.projects.fitness_club_management_system.dto.client;

import com.github.alinaguzova.projects.fitness_club_management_system.validation.PhoneNumber;
import com.github.alinaguzova.projects.fitness_club_management_system.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for client registration via POST request
 * Used to receive personal and authentication data from unauthenticated users.
 * Contains fields required to create a new client account, including name, email, phone, username, and password.
 * Validated using standard and custom annotations to ensure format, length and presence
 * Used in public-facing endpoint: {@code POST /api/common/register}.
 */


public class ClientCreateDto {

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 30, message = "Имя должно содержать не более 30 символов")
    private String name;

    @ValidEmail(message = "Некорректный формат email")
    @Size(max = 50, message = "Email должен содержать не более 50 символов")
    private String email;

    @NotBlank(message = "Телефон не должен быть пустым")
    @PhoneNumber(message = "Некорректный формат номера телефона")
    private String phone;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Size(max = 15, message = "Имя пользователя должно содержать не более 15 символов")
    private String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;


    public ClientCreateDto() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
