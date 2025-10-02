package com.github.alinaguzova.projects.fitness_club_management_system.service.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.ClientMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for registering new clients via public access.
 * Persists client entity, creates corresponding security user, and assigns default role.
 * Uses {@link JdbcTemplate} for direct SQL access to Spring Security tables and {@link PasswordEncoder} for password hashing.
 * Converts DTO to entity via {@link ClientMapper}.
 * Intended for use in public-facing registration endpoints.
 */

@Service
public class RegistrationService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    public RegistrationService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, ClientRepository clientRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }

    public void register(ClientCreateDto dto){


        Client client = ClientMapper.toEntity(dto);
        clientRepository.save(client);

        jdbcTemplate.update(
                "INSERT INTO users (username, password, enabled) VALUES (?, ?, ?)",
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                true
        );

        jdbcTemplate.update(
                "INSERT INTO authorities (username, authority) VALUES (?, ?)",
                dto.getUsername(),
                "ROLE_CLIENT"
        );
    }

}
