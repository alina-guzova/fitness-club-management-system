package com.github.alinaguzova.projects.fitness_club_management_system.controller.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.service.admin.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for administrative management of client accounts.
 * Provides endpoints for retrieving, updating, and deleting client data.
 * Accessible only to users with {@code ROLE_ADMIN}.
 * Uses {@link ClientServiceImpl} to delegate business logic and returns structured DTOs for admin views.
 */

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public AdminClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/clients")
    public List<AdminClientResponseDto> getAllClients(){
        return clientService.getAllClients();
    }

    @GetMapping("/clients/{clientId}")
    public AdminClientResponseDto getClientById(@PathVariable int clientId){
        return clientService.getClientById(clientId);

    }

    /**
     * Partially updates client data by ID.
     * Accepts patch DTO with optional fields and applies changes if present.
     * Throws {@link NoSuchClientException} if client is not found.
     *
     * @param clientId ID of the client to update
     * @param patchDto DTO containing fields to update
     * @return updated client DTO for admin view
     */
    @PatchMapping("/clients/{clientId}")
    public AdminClientResponseDto updateClient(@PathVariable int clientId, @Valid @RequestBody ClientPatchDto patchDto){
        return clientService.updateClient(clientId, patchDto);
    }

    /**
     * Deletes client by ID.
     * Returns {@code 204 No Content} if deletion is successful.
     * Throws {@link NoSuchClientException} if client does not exist.
     *
     * @param clientId ID of the client to delete
     * @return empty response with status 204
     */
    @DeleteMapping("/clients/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable int clientId){
        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }

}
