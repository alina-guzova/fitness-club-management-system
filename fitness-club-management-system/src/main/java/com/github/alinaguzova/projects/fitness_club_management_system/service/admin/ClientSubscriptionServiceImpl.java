package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.AdminClientWithSubscriptionsDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.ClientSubscriptionMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientSubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for administrative management of client subscriptions.
 * Provides operations for assigning, activating, deactivating, and deleting subscriptions linked to specific clients.
 * Also retrieves client subscription history with expiration-aware sorting: unactivated entries first, followed by active subscriptions in descending order.
 * Converts entities to structured DTOs via {@link ClientSubscriptionMapper} and throws domain-specific exceptions when entities are missing.
 */

@Service
public class ClientSubscriptionServiceImpl implements ClientSubscriptionService{

    private final ClientRepository clientRepository;
    private final ClientSubscriptionRepository clientSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ClientSubscriptionServiceImpl(ClientRepository clientRepository, ClientSubscriptionRepository clientSubscriptionRepository, SubscriptionRepository subscriptionRepository) {
        this.clientRepository = clientRepository;
        this.clientSubscriptionRepository = clientSubscriptionRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public void addSubscriptionToClient(ClientSubscriptionCreateDto dto) {
        ClientSubscription clientSubscription = new ClientSubscription();
        clientSubscription.setClient(clientRepository.findById(dto.getClientId()).
                orElseThrow(() -> new NoSuchClientException(dto.getClientId())));

        clientSubscription.setSubscription(subscriptionRepository.findById(dto.getSubscriptionId()).
                orElseThrow(() -> new NoSuchSubscriptionException(dto.getSubscriptionId())));

        clientSubscriptionRepository.save(clientSubscription);
    }

    @Override
    public void activateClientSubscription(int clientSubscriptionId) {
        ClientSubscription subscription = clientSubscriptionRepository.findById(clientSubscriptionId)
                .orElseThrow(() -> new NoSuchClientSubscriptionException(clientSubscriptionId));
        subscription.setActivationDate(LocalDate.now());
        clientSubscriptionRepository.save(subscription);
    }

    @Override
    public void deactivateClientSubscription(int clientSubscriptionId) {
        ClientSubscription subscription = clientSubscriptionRepository.findById(clientSubscriptionId)
                .orElseThrow(() -> new NoSuchClientSubscriptionException(clientSubscriptionId));
        subscription.setActivationDate(null);
        clientSubscriptionRepository.save(subscription);
    }

    @Override
    public void deleteClientSubscriptionById(int clientSubscriptionId) {
        if(!clientSubscriptionRepository.existsById(clientSubscriptionId)){
            throw new NoSuchClientSubscriptionException(clientSubscriptionId);
        }
        clientSubscriptionRepository.deleteById(clientSubscriptionId);
    }

    @Override
    public AdminClientWithSubscriptionsDto getClientSubscriptions(int clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()-> new NoSuchClientException(clientId));


        List<AdminClientSubscriptionResponseDto> dto = clientSubscriptionRepository.findByClientId(clientId).stream()
                .map(cs -> {
                    Subscription subscription = cs.getSubscription();
                    LocalDate activationDate = cs.getActivationDate();
                    LocalDate expirationDate = activationDate != null ? activationDate.plusDays(subscription.getDurationDays()) : null;

                    return ClientSubscriptionMapper.toAdminClientSubscriptionResponseDto(cs, expirationDate);
                })
                .sorted(Comparator.comparing(
                        AdminClientSubscriptionResponseDto::getExpirationDate,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());

        return ClientSubscriptionMapper.toAdminClientResponseDto(client,dto);
    }
}
