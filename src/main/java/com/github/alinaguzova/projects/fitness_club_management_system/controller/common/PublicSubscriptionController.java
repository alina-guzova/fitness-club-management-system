package com.github.alinaguzova.projects.fitness_club_management_system.controller.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.PublicSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.service.common.PublicSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for public access to subscription plans.
 * Provides an open endpoint for retrieving all available subscriptions without authentication.
 * Intended for unauthenticated users browsing available options before registration.
 */

@RestController
@RequestMapping("api/common")
public class PublicSubscriptionController {

    private final PublicSubscriptionService subscriptionService;

    @Autowired
    public PublicSubscriptionController(PublicSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Returns a list of all available subscriptions for public viewing.
     * Accessible without authentication.
     *
     * @return list of public subscription DTOs
     */
    @GetMapping("/subscriptions")
    public List<PublicSubscriptionResponseDto> getAllSubscriptions(){
        return subscriptionService.getAllSubscriptions();
    }

}
