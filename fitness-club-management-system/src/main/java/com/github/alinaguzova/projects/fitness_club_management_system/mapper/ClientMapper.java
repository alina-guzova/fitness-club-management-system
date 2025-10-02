package com.github.alinaguzova.projects.fitness_club_management_system.mapper;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.AdminClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;

/**
 * Utility class for converting between {@link Client} entity and related DTOs.
 * Handles transformations for public client creation, partial updates, and data views for both clients and administrators.
 * Used across service and controller layers to isolate mapping logic from business code.
 */

public class ClientMapper {

    public static Client toEntity(ClientCreateDto dto){
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setUsername(dto.getUsername());
        return client;
    }

    public static AdminClientResponseDto toAdminClientResponseDto(Client client){
        AdminClientResponseDto dto = new AdminClientResponseDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setRegistrationDate(client.getRegistrationDate());
        return dto;
    }

    public static ClientResponseDto toClientResponseDto(Client client){
        ClientResponseDto dto = new ClientResponseDto();
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        return dto;
    }

    public static void patchEntity(Client client, ClientPatchDto dto){
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            client.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            client.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null && !dto.getPhone().trim().isEmpty()) {
            client.setPhone(dto.getPhone());
        }
    }
}
