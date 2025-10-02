package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;

import java.util.List;

public interface ClientService {

    List<AdminClientResponseDto> getAllClients();

    AdminClientResponseDto getClientById(int clientId);

    AdminClientResponseDto updateClient(int clientId, ClientPatchDto dto);

    void deleteClientById(int clientId);

}
