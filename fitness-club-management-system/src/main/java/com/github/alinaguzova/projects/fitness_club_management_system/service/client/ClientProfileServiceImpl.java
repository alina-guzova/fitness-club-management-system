package com.github.alinaguzova.projects.fitness_club_management_system.service.client;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.ClientMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.ClientSubscriptionMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing the authenticated client's profile and subscriptions.
 * Provides operations for retrieving and updating personal data, as well as listing active or not activated subscriptions.
 * Filters subscriptions to exclude expired entries and calculates expiration dates based on activation and duration.
 * Resolves the current client using {@link SecurityContextHolder} and maps entities via {@link ClientMapper} and {@link ClientSubscriptionMapper}.
 * Throws {@link NoSuchClientException} when the client is not found by username.
 */

@Service
public class ClientProfileServiceImpl implements ClientProfileService{

    public final ClientRepository clientRepository;

    public final ClientSubscriptionRepository clientSubscriptionRepository;

    @Autowired
    public ClientProfileServiceImpl(ClientRepository clientRepository, ClientSubscriptionRepository clientSubscriptionRepository) {
        this.clientRepository = clientRepository;
        this.clientSubscriptionRepository = clientSubscriptionRepository;
    }

    @Override
    public ClientResponseDto getProfile() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return clientRepository.findByUsername(currentUsername)
                .map(ClientMapper::toClientResponseDto)
                .orElseThrow(() -> new NoSuchClientException(currentUsername));
    }

    @Override
    public ClientResponseDto updateProfile(ClientPatchDto dto) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NoSuchClientException(currentUsername));

        ClientMapper.patchEntity(client, dto);
        Client updated = clientRepository.save(client);

        return ClientMapper.toClientResponseDto(updated);
    }

    @Override
    public List<ClientSubscriptionResponseDto> getMySubscriptions() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Client client = clientRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new NoSuchClientException(currentUsername));

        List<ClientSubscriptionResponseDto> dto = clientSubscriptionRepository.findByClientId(client.getId()).stream()
                .filter(cs -> {
                    LocalDate activationDate = cs.getActivationDate();
                    if (activationDate == null) return true; // not activated
                    LocalDate expirationDate = activationDate.plusDays(cs.getSubscription().getDurationDays());
                    return expirationDate.isAfter(LocalDate.now()); // is active
                })
                .map(cs -> {
                    Subscription subscription = cs.getSubscription();
                    LocalDate activationDate = cs.getActivationDate();
                    LocalDate expirationDate = activationDate != null ? activationDate.plusDays(subscription.getDurationDays()) : null;
                    return ClientSubscriptionMapper.toClientSubscriptionResponseDto(cs, expirationDate);
                })
                .collect(Collectors.toList());

        return dto;
    }
}
