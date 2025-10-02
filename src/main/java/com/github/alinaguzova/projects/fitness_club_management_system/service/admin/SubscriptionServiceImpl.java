package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.AdminSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.mapper.SubscriptionMapper;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.specification.SubscriptionSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for administrative management of subscription plans.
 * Provides operations for creating, updating, deactivating, deleting, and retrieving subscriptions.
 * Supports dynamic filtering by active status and minimum price, as well as sorting by arbitrary fields and direction.
 * Converts entities to DTOs via {@link SubscriptionMapper} and applies query specifications via {@link SubscriptionSpecification}.
 * Throws {@link NoSuchSubscriptionException} when subscription is not found.
 */

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<AdminSubscriptionResponseDto> getAllSubscriptions(Boolean active, Integer priceFrom, String direction, String sortBy) {
        Specification<Subscription> spec = (root, query, cb) -> cb.conjunction();
        spec = spec.and(SubscriptionSpecification.hasActive(active))
                .and(SubscriptionSpecification.priceFrom(priceFrom));

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);


        return subscriptionRepository.findAll(spec, sort).stream()
                .map(SubscriptionMapper::toAdminSubscriptionResponseDto)
                .collect(Collectors.toList());
      }

    @Override
    public AdminSubscriptionResponseDto getSubscriptionById(int subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .map(SubscriptionMapper::toAdminSubscriptionResponseDto)
                .orElseThrow(() -> new NoSuchSubscriptionException(subscriptionId));
    }

    @Override
    public AdminSubscriptionResponseDto createSubscription(SubscriptionCreateDto dto) {
        Subscription subscription = SubscriptionMapper.toEntity(dto);
        return SubscriptionMapper.toAdminSubscriptionResponseDto(subscriptionRepository.save(subscription));
    }

    @Override
    public AdminSubscriptionResponseDto updateSubscription(int subscriptionId, SubscriptionPatchDto dto) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchSubscriptionException(subscriptionId));

        SubscriptionMapper.patchEntity(subscription, dto);
        Subscription updated = subscriptionRepository.save(subscription);

        return SubscriptionMapper.toAdminSubscriptionResponseDto(updated);
    }

    @Override
    public void deactivateSubscriptionById(int subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NoSuchSubscriptionException(subscriptionId));
        subscription.deactivate();
        subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteSubscriptionById(int subscriptionId) {
        if (!subscriptionRepository.existsById(subscriptionId)){
            throw new NoSuchSubscriptionException(subscriptionId);
        }
        subscriptionRepository.deleteById(subscriptionId);
    }
}
