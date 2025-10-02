package com.github.alinaguzova.projects.fitness_club_management_system.service.common;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.PublicSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestSubscriptionFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Unit tests for PublicSubscriptionService")
@ExtendWith(MockitoExtension.class)
public class PublicSubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    PublicSubscriptionService subscriptionService;

    @Nested
    @DisplayName("getAllSubscriptions()")
    class GetSubscriptionsTests{

        @DisplayName("Should return DTO list")
        @Test
        void shouldReturnSubscriptions(){
            //Arrange
            Subscription subscription1 = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);
            Subscription subscription2 = TestSubscriptionFactory.create(2, "Йога Премиум", "Йога + сауна", new BigDecimal("59.99"), 45, 8, false);
            Subscription subscription3 = TestSubscriptionFactory.create(3, "VIP Безлимит", "Все зоны клуба", new BigDecimal("149.99"), 90, Integer.MAX_VALUE, true);
            List<Subscription> subscriptions = List.of(subscription1, subscription2, subscription3);

            when(subscriptionRepository.findAll()).thenReturn(subscriptions);

            //Act
            List<PublicSubscriptionResponseDto> result = subscriptionService.getAllSubscriptions();

            //Assert
            assertThat(result)
                    .extracting(
                            PublicSubscriptionResponseDto::getName,
                            PublicSubscriptionResponseDto::getDescription,
                            PublicSubscriptionResponseDto::getPrice,
                            PublicSubscriptionResponseDto::getDurationDays,
                            PublicSubscriptionResponseDto::getVisitCount
                    )
                    .containsExactly(
                            tuple( "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12),
                            tuple( "Йога Премиум", "Йога + сауна", new BigDecimal("59.99"), 45, 8),
                            tuple( "VIP Безлимит", "Все зоны клуба", new BigDecimal("149.99"), 90, Integer.MAX_VALUE)
                    );


        }



    }



}
