package com.github.alinaguzova.projects.fitness_club_management_system.controller.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.AdminSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.service.admin.SubscriptionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for administrative management of subscription plans.
 * Provides endpoints for creating, updating, deactivating, deleting, and retrieving subscriptions.
 * Supports filtering by active status and minimum price, and sorting by arbitrary fields and direction.
 * Accessible only to users with {@code ROLE_ADMIN}.
 */

@RestController
@RequestMapping("api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminSubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    @Autowired
    public AdminSubscriptionController(SubscriptionServiceImpl subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Retrieves a list of subscriptions with optional filtering and sorting.
     * Allows filtering by active status and minimum price, and sorting by any field in ascending or descending order.
     *
     * @param active      whether to include only active/inactive subscriptions
     * @param priceFrom   minimum price threshold
     * @param direction   sort direction: "asc" or "desc"
     * @param sortBy      field to sort by (e.g., "id", "price", "name")
     * @return list of matching subscriptions
     */
    @GetMapping("/subscriptions")
    public List<AdminSubscriptionResponseDto> getAllSubscriptions(@RequestParam(required = false) Boolean active,
                                                                  @RequestParam(required = false) Integer priceFrom,
                                                                  @RequestParam(required = false, defaultValue = "asc") String direction,
                                                                  @RequestParam(required = false, defaultValue = "id") String sortBy){

        return subscriptionService.getAllSubscriptions(active, priceFrom, direction, sortBy);
    }

    @GetMapping("/subscriptions/{subscriptionId}")
    public AdminSubscriptionResponseDto getSubscriptionById(@PathVariable int subscriptionId){
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    @PostMapping("/subscriptions")
    public AdminSubscriptionResponseDto createSubscription(@Valid @RequestBody SubscriptionCreateDto dto){
        return subscriptionService.createSubscription(dto);
    }

    /**
     * Partially updates subscription data by ID.
     * Accepts patch DTO with optional fields and applies changes if present.
     * Throws {@link NoSuchSubscriptionException} if subscription is not found.
     *
     * @param subscriptionId ID of the subscription to update
     * @param dto DTO containing fields to update
     * @return updated subscription DTO for admin view
     */
    @PatchMapping("/subscriptions/{subscriptionId}")
    public AdminSubscriptionResponseDto updateSubscription(@PathVariable int subscriptionId, @Valid @RequestBody SubscriptionPatchDto dto){
        return subscriptionService.updateSubscription(subscriptionId, dto);
    }

    /**
     * Deactivates subscription by ID without deleting it.
     * Sets the subscription as inactive and returns {@code 204 No Content}.
     * Throws {@link NoSuchSubscriptionException} if subscription is not found.
     *
     * @param subscriptionId ID of the subscription to deactivate
     * @return empty response with status 204
     */
    @PatchMapping("/subscriptions/{subscriptionId}/deactivate")
    public ResponseEntity<Void> deactivateSubscriptionById(@PathVariable int subscriptionId){
        subscriptionService.deactivateSubscriptionById(subscriptionId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes subscription by ID.
     * Returns {@code 204 No Content} if deletion is successful.
     * Throws {@link NoSuchSubscriptionException} if subscription does not exist.
     *
     * @param subscriptionId ID of the subscription to delete
     * @return empty response with status 204
     */
    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable int subscriptionId){
        subscriptionService.deleteSubscriptionById(subscriptionId);
        return ResponseEntity.noContent().build();
    }

}
