package com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for assigning a subscription to a specific client via administrative POST request.
 * Used by administrators to create a client-subscription link by specifying client and subscription IDs.
 * Both fields are mandatory and validated for presence.
 * Consumed by admin-facing endpoint: : {@code POST /api/admin/client-subscriptions}.
 */

public class ClientSubscriptionCreateDto {

    @NotNull(message = "id клиента не указан")
    private Integer clientId;
    @NotNull(message = "id абонемента не указан")
    private Integer subscriptionId;

    public ClientSubscriptionCreateDto() {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
