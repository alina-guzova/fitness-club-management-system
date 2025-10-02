package com.github.alinaguzova.projects.fitness_club_management_system.controller.client;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.service.client.ClientProfileServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing the authenticated client's profile and subscriptions.
 * Provides endpoints for retrieving and updating personal data, and viewing active or unactivated subscriptions.
 * Accessible only to users with {@code ROLE_CLIENT}.
 */

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientProfileController {

    private final ClientProfileServiceImpl profileService;

    @Autowired
    public ClientProfileController(ClientProfileServiceImpl profileService) {
        this.profileService = profileService;
    }

    /**
     * Returns profile information of the currently authenticated client.
     * Extracts username from security context and retrieves corresponding client data.
     * Throws {@link NoSuchClientException} if client is not found.
     *
     * @return client profile DTO
     */
    @GetMapping("/profile")
    public ClientResponseDto getProfile(){
        return profileService.getProfile();
    }

    /**
     * Partially updates profile information of the authenticated client.
     * Accepts patch DTO with optional fields and applies changes if present.
     * Throws {@link NoSuchClientException} if client is not found.
     *
     * @param patchDto DTO containing fields to update
     * @return updated client profile DTO
     */
    @PatchMapping("/profile")
    public ClientResponseDto updateProfile(@Valid @RequestBody ClientPatchDto patchDto){
        return profileService.updateProfile(patchDto);
    }

    /**
     * Retrieves active or unactivated subscriptions of the authenticated client.
     * Filters out expired subscriptions and calculates expiration dates based on activation and duration.
     *
     * @return list of active or unactivated subscriptions
     */
    @GetMapping("/profile/subscriptions")
    public List<ClientSubscriptionResponseDto> getMySubscriptions(){
        return profileService.getMySubscriptions();
    }

}
