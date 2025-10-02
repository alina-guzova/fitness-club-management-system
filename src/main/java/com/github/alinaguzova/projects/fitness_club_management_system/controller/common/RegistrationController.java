package com.github.alinaguzova.projects.fitness_club_management_system.controller.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.service.common.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for public client registration.
 * Provides an open endpoint for creating a new client account, including security credentials and role assignment.
 * Intended for unauthenticated users initiating registration.
 */

@RestController
@RequestMapping("api/common")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Registers a new client account.
     * Persists client entity, creates a corresponding security user, and assigns the {@code ROLE_CLIENT}.
     * Accessible without authentication.
     *
     * @param dto DTO containing client registration data
     */
    @PostMapping("/register")
    public void register(@Valid @RequestBody ClientCreateDto dto){
        registrationService.register(dto);
    }

}
