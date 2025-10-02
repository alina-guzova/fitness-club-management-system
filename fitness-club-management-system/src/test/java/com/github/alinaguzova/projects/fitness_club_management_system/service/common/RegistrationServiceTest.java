package com.github.alinaguzova.projects.fitness_club_management_system.service.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Unit tests for RegistrationService")
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    public JdbcTemplate jdbcTemplate;

    @Mock
    public PasswordEncoder passwordEncoder;

    @Mock
    public ClientRepository clientRepository;

    @InjectMocks
    RegistrationService registrationService;

    @Test
    void shouldRegisterClientCorrectly(){
        //Arrange
        ClientCreateDto dto = new ClientCreateDto();
        dto.setUsername("Vera Petrova");
        dto.setEmail("veramoroz@gmail.com");
        dto.setPhone("+375291451165");
        dto.setUsername("vera");
        dto.setPassword("vera");

        when(passwordEncoder.encode("vera")).thenReturn("encoded_password");

        //Act
        registrationService.register(dto);

        //Assert
        verify(clientRepository).save(any(Client.class));

        verify(passwordEncoder).encode("vera");

        verify(jdbcTemplate).update(
                eq("INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)"),
                eq("vera"),
                eq("encoded_password"),
                eq(true)
        );

        verify(jdbcTemplate).update(
                eq("INSERT INTO authorities (username, authority) VALUES (?, ?)"),
                eq("vera"),
                eq("ROLE_CLIENT")
        );

    }

}
