package com.github.alinaguzova.projects.fitness_club_management_system.controller.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientWithSubscriptionsDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.service.admin.ClientSubscriptionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for administrative management of client subscriptions.
 * Provides endpoints for assigning subscriptions to clients, activating, deactivating, deleting them,
 * and retrieving full subscription history of a specific client.
 * Accessible only to users with {@code ROLE_ADMIN}.
 */

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminClientSubscriptionController {

    private final ClientSubscriptionServiceImpl clientSubscriptionService;

    public AdminClientSubscriptionController(ClientSubscriptionServiceImpl clientSubscriptionService) {
        this.clientSubscriptionService = clientSubscriptionService;
    }

    /**
     * Retrieves full subscription history of a specific client.
     * Returns client data along with a list of subscriptions, sorted by expiration date (unactivated first, then descending).
     * Throws {@link NoSuchClientException} if client is not found.
     *
     * @param clientId ID of the client
     * @return structured DTO containing client info and subscriptions
     */
    @GetMapping("/client-subscriptions/client/{clientId}")
    public AdminClientWithSubscriptionsDto getClientSubscriptions(@PathVariable int clientId){
        return clientSubscriptionService.getClientSubscriptions(clientId);
    }

    /**
     * Assigns a subscription to a client.
     * Creates a new {@link ClientSubscription} linking the client and the selected subscription.
     * Throws {@link NoSuchClientException} or {@link NoSuchSubscriptionException} if entities are not found.
     *
     * @param dto DTO containing client ID and subscription ID
     * @return empty response with status 204
     */
    @PostMapping("/client-subscriptions")
    public ResponseEntity<Void> addSubscriptionToClient(@Valid @RequestBody ClientSubscriptionCreateDto dto){
        clientSubscriptionService.addSubscriptionToClient(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Activates a client subscription by setting the current date as activation date.
     * Throws {@link NoSuchClientSubscriptionException} if subscription is not found.
     *
     * @param clientSubscriptionId ID of the client subscription to activate
     * @return empty response with status 204
     */
    @PatchMapping("/client-subscriptions/{clientSubscriptionId}/activate")
    public ResponseEntity<Void> activateClientSubscription(@PathVariable int clientSubscriptionId){
        clientSubscriptionService.activateClientSubscription(clientSubscriptionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deactivates a client subscription by clearing its activation date.
     * Throws {@link NoSuchClientSubscriptionException} if subscription is not found.
     *
     * @param clientSubscriptionId ID of the client subscription to deactivate
     * @return empty response with status 204
     */
    @PatchMapping("/client-subscriptions/{clientSubscriptionId}/deactivate")
    public ResponseEntity<Void> deactivateClientSubscription(@PathVariable int clientSubscriptionId){
        clientSubscriptionService.deactivateClientSubscription(clientSubscriptionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a client subscription by ID.
     * Throws {@link NoSuchClientSubscriptionException} if subscription does not exist.
     *
     * @param clientSubscriptionId ID of the client subscription to delete
     * @return empty response with status 204
     */
    @DeleteMapping("/client-subscriptions/{clientSubscriptionId}")
    public ResponseEntity<Void> deleteClientSubscription(@PathVariable int clientSubscriptionId){
        clientSubscriptionService.deleteClientSubscriptionById(clientSubscriptionId);
        return ResponseEntity.noContent().build();
    }
}
