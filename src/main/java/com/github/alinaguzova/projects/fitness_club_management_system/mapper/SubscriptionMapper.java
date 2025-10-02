package com.github.alinaguzova.projects.fitness_club_management_system.mapper;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.AdminSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.PublicSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;

/**
 * Utility class for transforming {@link Subscription} entity to and from related DTOs.
 * Handles conversions for public viewing and administrative operations such as creation, patching, and detailed inspection.
 * Used across service and controller layers to isolate mapping logic from business code.
 */

public class SubscriptionMapper {

    public static Subscription toEntity(SubscriptionCreateDto dto){
        Subscription subscription = new Subscription();
        subscription.setName(dto.getName());
        subscription.setDescription(dto.getDescription());
        subscription.setPrice(dto.getPrice());
        subscription.setDurationDays(dto.getDurationDays());
        subscription.setVisitCount(dto.getVisitCount());
        subscription.activate();
        return subscription;
    }

    public static AdminSubscriptionResponseDto toAdminSubscriptionResponseDto(Subscription subscription){
        AdminSubscriptionResponseDto dto = new AdminSubscriptionResponseDto();
        dto.setId(subscription.getId());
        dto.setName(subscription.getName());
        dto.setDescription(subscription.getDescription());
        dto.setPrice(subscription.getPrice());
        dto.setDurationDays(subscription.getDurationDays());
        dto.setVisitCount(subscription.getVisitCount());
        dto.setActive(subscription.getIsActive());
        return dto;
    }


    public static PublicSubscriptionResponseDto toPublicSubscriptionResponseDto(Subscription subscription){
        PublicSubscriptionResponseDto dto = new PublicSubscriptionResponseDto();
        dto.setName(subscription.getName());
        dto.setDescription(subscription.getDescription());
        dto.setPrice(subscription.getPrice());
        dto.setDurationDays(subscription.getDurationDays());
        dto.setVisitCount(subscription.getVisitCount());
        return dto;
    }

    public static void patchEntity(Subscription subscription, SubscriptionPatchDto dto){
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            subscription.setName(dto.getName());
        }
        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            subscription.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            subscription.setPrice(dto.getPrice());
        }
        if (dto.getDurationDays() != null) {
            subscription.setDurationDays(dto.getDurationDays());
        }
        if (dto.getVisitCount() != null) {
            subscription.setVisitCount(dto.getVisitCount());
        }
        if (dto.getIsActive() != null) {
            if (dto.getIsActive()) {
                subscription.activate();
            } else {
                subscription.deactivate();
            }
        }

    }

}
