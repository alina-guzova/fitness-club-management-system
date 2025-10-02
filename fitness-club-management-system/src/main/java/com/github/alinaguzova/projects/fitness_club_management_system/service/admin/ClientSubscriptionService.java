package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientWithSubscriptionsDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionCreateDto;

public interface ClientSubscriptionService {

    void addSubscriptionToClient(ClientSubscriptionCreateDto dto);

    void activateClientSubscription(int clientSubscriptionId);

    void deactivateClientSubscription(int clientSubscriptionId);

    void deleteClientSubscriptionById(int clientSubscriptionId);

    AdminClientWithSubscriptionsDto getClientSubscriptions(int clientId);
}
