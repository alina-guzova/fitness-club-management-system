package com.github.alinaguzova.projects.fitness_club_management_system.service.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.PublicSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.SubscriptionMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for retrieving subscription plans available to all users, including unauthenticated visitors.
 * Provides public access to basic subscription information without filtering or business restrictions.
 * Converts entities to public-facing DTOs via {@link SubscriptionMapper}.
 * Used in public-facing controllers to support open browsing of available plans.
 */

@Service
public class PublicSubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public PublicSubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<PublicSubscriptionResponseDto> getAllSubscriptions(){
        return subscriptionRepository.findAll().stream()
                .map(SubscriptionMapper::toPublicSubscriptionResponseDto)
                .collect(Collectors.toList());

    }

}
