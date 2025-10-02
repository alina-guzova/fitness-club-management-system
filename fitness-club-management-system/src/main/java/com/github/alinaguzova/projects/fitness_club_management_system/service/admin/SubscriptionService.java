package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.AdminSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionPatchDto;

import java.util.List;

public interface SubscriptionService {

    List<AdminSubscriptionResponseDto> getAllSubscriptions(Boolean active, Integer priceFrom, String direction, String sortBy);

    AdminSubscriptionResponseDto getSubscriptionById(int subscriptionId);

    AdminSubscriptionResponseDto createSubscription(SubscriptionCreateDto dto);

    AdminSubscriptionResponseDto updateSubscription(int subscriptionId, SubscriptionPatchDto dto);

    void deactivateSubscriptionById(int subscriptionId);

    void deleteSubscriptionById(int subscriptionId);

}
