package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.AdminSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionCreateDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription.SubscriptionPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchSubscriptionException;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestSubscriptionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
@DisplayName("Unit tests for SubscriptionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Nested
    @DisplayName("getAllSubscriptions()")
    class GetSubscriptionsTests {

        @DisplayName("Should return DTO list")
        @Test
        void shouldReturnMappedSubscriptions_whenFiltersAndSortAreProvided() {
            //Arrange
            Subscription subscription1 = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);
            Subscription subscription2 = TestSubscriptionFactory.create(2, "Йога Премиум", "Йога + сауна", new BigDecimal("59.99"), 45, 8, false);
            Subscription subscription3 = TestSubscriptionFactory.create(3, "VIP Безлимит", "Все зоны клуба", new BigDecimal("149.99"), 90, Integer.MAX_VALUE, true);
            List<Subscription> subscriptions = List.of(subscription1, subscription2, subscription3);

            when(subscriptionRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(subscriptions);

            //Act
            List<AdminSubscriptionResponseDto> result = subscriptionService.getAllSubscriptions(null, null, "asc", "id");

            //Assert
            assertThat(result)
                    .extracting(
                            AdminSubscriptionResponseDto::getId,
                            AdminSubscriptionResponseDto::getName,
                            AdminSubscriptionResponseDto::getPrice,
                            AdminSubscriptionResponseDto::getIsActive
                    )
                    .containsExactly(
                            tuple(1, "Базовый", new BigDecimal("29.99"), true),
                            tuple(2, "Йога Премиум", new BigDecimal("59.99"), false),
                            tuple(3, "VIP Безлимит", new BigDecimal("149.99"), true)
                    );
        }
    }

    @Nested
    @DisplayName("getSubscriptionById()")
    class GetSubscriptionByIdTests {

        @DisplayName("Should return DTO when subscription exists")
        @Test
        void shouldReturnSubscriptionDto_whenSubscriptionExists() {
            //Arrange
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            when(subscriptionRepository.findById(1)).thenReturn(Optional.of(subscription));

            //Act
            AdminSubscriptionResponseDto result = subscriptionService.getSubscriptionById(1);

            //Assert
            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getName()).isEqualTo("Базовый");
            assertThat(result.getDescription()).isEqualTo("Зал без групповых");
            assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("29.99"));
            assertThat(result.getDurationDays()).isEqualTo(30);
            assertThat(result.getVisitCount()).isEqualTo(12);
            assertThat(result.getIsActive()).isTrue();

        }

        @DisplayName("Should throw exception when subscription not found")
        @Test
        void shouldThrowException_whenSubscriptionNotFound() {
            when(subscriptionRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(NoSuchSubscriptionException.class, () -> subscriptionService.getSubscriptionById(1));
        }
    }

    @Nested
    @DisplayName("createSubscription()")
    class CreateSubscriptionTests {

        @DisplayName("Should create subscription and return DTO")
        @Test
        void shouldCreateSubscription() {
            //Arrange
            SubscriptionCreateDto createDto = new SubscriptionCreateDto();
            createDto.setName("Базовый");
            createDto.setDescription("Зал без групповых");
            createDto.setPrice(new BigDecimal("29.99"));
            createDto.setDurationDays(30);
            createDto.setVisitCount(12);

            Subscription savedSubscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            when(subscriptionRepository.save(any(Subscription.class))).thenReturn(savedSubscription);

            //Act
            AdminSubscriptionResponseDto result = subscriptionService.createSubscription(createDto);

            //Assert
            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getName()).isEqualTo("Базовый");
            assertThat(result.getDescription()).isEqualTo("Зал без групповых");
            assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("29.99"));
            assertThat(result.getDurationDays()).isEqualTo(30);
            assertThat(result.getVisitCount()).isEqualTo(12);
            assertThat(result.getIsActive()).isTrue();

            verify(subscriptionRepository).save(any(Subscription.class));
        }
    }

    @Nested
    @DisplayName("updateSubscription()")
    class UpdateSubscriptionTests {

        @DisplayName("Should update subscription and return DTO")
        @Test
        void shouldUpdateSubscription_whenSubscriptionExists() {
            //Arrange
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            SubscriptionPatchDto patchDto = new SubscriptionPatchDto();
            patchDto.setName("VIP Безлимит");
            patchDto.setPrice(new BigDecimal("49.99"));

            when(subscriptionRepository.findById(1)).thenReturn(Optional.of(subscription));
            when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

            //Act
            AdminSubscriptionResponseDto result = subscriptionService.updateSubscription(1, patchDto);

            //Assert
            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getName()).isEqualTo("VIP Безлимит");
            assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("49.99"));
            assertThat(result.getDescription()).isEqualTo("Зал без групповых");
            assertThat(result.getDurationDays()).isEqualTo(30);
            assertThat(result.getVisitCount()).isEqualTo(12);
            assertThat(result.getIsActive()).isTrue();
        }

        @DisplayName("Should throw exception when updating non-existent subscription")
        @Test
        void shouldThrowException_whenSubscriptionNotFoundForUpdate() {
            SubscriptionPatchDto patchDto = new SubscriptionPatchDto();
            patchDto.setName("VIP Безлимит");
            patchDto.setPrice(new BigDecimal("49.99"));

            when(subscriptionRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(NoSuchSubscriptionException.class, () -> subscriptionService.updateSubscription(1, patchDto));

        }
    }

    @Nested
    @DisplayName("deactivateSubscriptionById()")
    class DeactivateSubscriptionTests {

        @DisplayName("Should deactivate subscription when subscription exists")
        @Test
        void shouldDeactivateSubscription_whenSubscriptionExists() {
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            when(subscriptionRepository.findById(1)).thenReturn(Optional.of(subscription));
            when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

            subscriptionService.deactivateSubscriptionById(1);

            assertFalse(subscription.getIsActive());
            verify(subscriptionRepository).save(subscription);

        }

        @DisplayName("Should throw exception when subscription does not exist")
        @Test
        void shouldThrowException_whenSubscriptionNotFoundForDeactivate() {
            int subscriptionId = 1;

            when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.empty());

            assertThrows(NoSuchSubscriptionException.class, () -> subscriptionService.deactivateSubscriptionById(subscriptionId));
            verify(subscriptionRepository, never()).save(any(Subscription.class));

        }
    }

    @Nested
    @DisplayName("deleteSubscriptionById()")
    class DeleteSubscriptionTests {

        @DisplayName("Should delete subscription when subscription exists")
        @Test
        void shouldDeleteSubscription_whenSubscriptionExists() {
            int subscriptionId = 1;

            when(subscriptionRepository.existsById(subscriptionId)).thenReturn(true);

            subscriptionService.deleteSubscriptionById(subscriptionId);

            verify(subscriptionRepository).deleteById(subscriptionId);

        }

        @DisplayName("Should throw exception when subscription does not exist")
        @Test
        void shouldThrowException_whenSubscriptionNotFoundForDelete() {
            int subscriptionId = 1;

            when(subscriptionRepository.existsById(subscriptionId)).thenReturn(false);

            assertThrows(NoSuchSubscriptionException.class, () -> subscriptionService.deleteSubscriptionById(subscriptionId));
            verify(subscriptionRepository, never()).deleteById(anyInt());
            verifyNoMoreInteractions(subscriptionRepository);
        }
    }

}
