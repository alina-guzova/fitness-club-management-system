package com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;

import java.util.List;

/**
 * DTO for displaying client information along with their assigned subscriptions in administrative endpoints.
 * Combines {@link AdminClientResponseDto} with a list of {@link AdminClientSubscriptionResponseDto} entries.
 * Used by administrators to view client profiles and their subscription history.
 */

public class AdminClientWithSubscriptionsDto {

    private AdminClientResponseDto client;
    private List<AdminClientSubscriptionResponseDto> clientSubscriptions;

    public AdminClientWithSubscriptionsDto() {
    }

    public AdminClientResponseDto getClient() {
        return client;
    }

    public void setClient(AdminClientResponseDto client) {
        this.client = client;
    }

    public List<AdminClientSubscriptionResponseDto> getClientSubscriptions() {
        return clientSubscriptions;
    }

    public void setClientSubscriptions(List<AdminClientSubscriptionResponseDto> clientSubscriptions) {
        this.clientSubscriptions = clientSubscriptions;
    }
}
