package com.github.alinaguzova.projects.fitness_club_management_system.mapper;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientWithSubscriptionsDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;

import java.time.LocalDate;
import java.util.List;

/**
 * Utility class for transforming {@link ClientSubscription} and related entities into DTOs for client and administrative views.
 * Handles mapping for limited client-facing responses, detailed admin subscription descriptions, and combined client-subscription structures.
 * Used in services to isolate presentation logic and support layered response composition.
 */

public class ClientSubscriptionMapper {

    public static ClientSubscriptionResponseDto toClientSubscriptionResponseDto(ClientSubscription clientSubscription, LocalDate expirationDate){
        ClientSubscriptionResponseDto dto = new ClientSubscriptionResponseDto();
        dto.setName(clientSubscription.getSubscription().getName());
        dto.setDurationDays(clientSubscription.getSubscription().getDurationDays());
        dto.setVisitCount(clientSubscription.getSubscription().getVisitCount());
        dto.setActivationDate(clientSubscription.getActivationDate());
        dto.setExpirationDate(expirationDate);
        return dto;
    }

    public static AdminClientWithSubscriptionsDto toAdminClientResponseDto(Client client, List<AdminClientSubscriptionResponseDto> clientSubscription){
        AdminClientResponseDto clientDto = ClientMapper.toAdminClientResponseDto(client);

        AdminClientWithSubscriptionsDto dto = new AdminClientWithSubscriptionsDto();

        dto.setClient(clientDto);
        dto.setClientSubscriptions(clientSubscription);
        return dto;
    }

    public static AdminClientSubscriptionResponseDto toAdminClientSubscriptionResponseDto(ClientSubscription clientSubscription, LocalDate expirationDate){
        Subscription subscription = clientSubscription.getSubscription();
        AdminClientSubscriptionResponseDto dto = new AdminClientSubscriptionResponseDto();
        dto.setClientSubscriptionId(clientSubscription.getId());
        dto.setName(subscription.getName());
        dto.setDescription(subscription.getDescription());
        dto.setPrice(subscription.getPrice());
        dto.setDurationDays(subscription.getDurationDays());
        dto.setVisitCount(subscription.getVisitCount());
        dto.setActivationDate(clientSubscription.getActivationDate());
        dto.setExpirationDate(expirationDate);
        return dto;
    }

}
