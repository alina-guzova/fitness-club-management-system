package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.ClientMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for administrative client management.
 * Provides operations for retrieving, updating, and deleting client records.
 * Converts entities to DTOs for admin-facing responses and handles patch logic via {@link ClientMapper}.
 * Throws {@link NoSuchClientException} when client is not found.
 */

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<AdminClientResponseDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toAdminClientResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminClientResponseDto getClientById(int clientId) {
        return clientRepository.findById(clientId)
                .map(ClientMapper::toAdminClientResponseDto)
                .orElseThrow(() -> new NoSuchClientException(clientId));
     }

    @Override
    public AdminClientResponseDto updateClient(int clientId, ClientPatchDto dto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchClientException(clientId));

        ClientMapper.patchEntity(client, dto);
        Client updated = clientRepository.save(client);

        return ClientMapper.toAdminClientResponseDto(updated);
    }

    @Override
    public void deleteClientById(int clientId) {
        if (!clientRepository.existsById(clientId)){
            throw new NoSuchClientException(clientId);
        }
        clientRepository.deleteById(clientId);
    }

}
