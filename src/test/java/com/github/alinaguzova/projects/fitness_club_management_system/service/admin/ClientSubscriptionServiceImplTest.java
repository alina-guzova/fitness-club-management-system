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
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientSubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.SubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestClientFactory;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestClientSubscriptionFactory;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestSubscriptionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Unit tests for ClientSubscriptionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class ClientSubscriptionServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientSubscriptionRepository clientSubscriptionRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    ClientSubscriptionServiceImpl clientSubscriptionService;

    @Captor
    ArgumentCaptor<ClientSubscription> captor;

    @Nested
    @DisplayName("addSubscriptionToClient()")
    class AddSubscriptionToClientTests {

        @DisplayName("Should add subscription to client when client and subscription exist")
        @Test
        void shouldAddSubscriptionToClientSuccessfully() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            when(clientRepository.findById(1)).thenReturn(Optional.of(client));
            when(subscriptionRepository.findById(1)).thenReturn(Optional.of(subscription));

            ClientSubscriptionCreateDto dto = new ClientSubscriptionCreateDto();
            dto.setClientId(1);
            dto.setSubscriptionId(1);

            //Act
            clientSubscriptionService.addSubscriptionToClient(dto);

            //Assert
            verify(clientSubscriptionRepository).save(captor.capture());
            ClientSubscription saved = captor.getValue();
            assertThat(saved.getClient()).isEqualTo(client);
            assertThat(saved.getSubscription()).isEqualTo(subscription);

        }

        @DisplayName("Should throw exception when client not found")
        @Test
        void shouldThrowException_whenClientNotFound() {
            //Arrange
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            when(clientRepository.findById(1)).thenReturn(Optional.empty());

            ClientSubscriptionCreateDto dto = new ClientSubscriptionCreateDto();
            dto.setClientId(1);
            dto.setSubscriptionId(1);

            //Act & Assert
            assertThrows(NoSuchClientException.class, () -> clientSubscriptionService.addSubscriptionToClient(dto));
            verify(clientRepository).findById(anyInt());
            verify(subscriptionRepository, never()).findById(anyInt());
            verifyNoMoreInteractions(clientSubscriptionRepository);

        }

        @DisplayName("Should throw exception when subscription not found")
        @Test
        void shouldThrowException_whenSubscriptionNotFound() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));

            when(clientRepository.findById(1)).thenReturn(Optional.of(client));
            when(subscriptionRepository.findById(1)).thenReturn(Optional.empty());

            ClientSubscriptionCreateDto dto = new ClientSubscriptionCreateDto();
            dto.setClientId(1);
            dto.setSubscriptionId(1);

            //Act & Assert
            assertThrows(NoSuchSubscriptionException.class, () -> clientSubscriptionService.addSubscriptionToClient(dto));
            verify(clientRepository).findById(anyInt());
            verify(subscriptionRepository).findById(anyInt());
            verifyNoMoreInteractions(clientSubscriptionRepository);

        }
    }

    @Nested
    @DisplayName("activateClientSubscription()")
    class ActivateClientSubscriptionTests {

        @DisplayName("Should activate client subscription when client subscription exists")
        @Test
        void shouldActivateClientSubscription_whenClientSubscriptionExists() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);
            LocalDate date = LocalDate.of(2025, 8, 29);
            ClientSubscription clientSubscription = TestClientSubscriptionFactory.create(1, client, subscription, date);

            when(clientSubscriptionRepository.findById(1)).thenReturn(Optional.of(clientSubscription));

            //Act
            clientSubscriptionService.activateClientSubscription(1);

            //Assert
            verify(clientSubscriptionRepository).findById(anyInt());
            verify(clientSubscriptionRepository).save(captor.capture());
            ClientSubscription result = captor.getValue();
            assertThat(result.getActivationDate()).isNotEqualTo(date);

        }

        @DisplayName("Should throw exception when client subscription not found")
        @Test
        void shouldThrowException_whenClientSubscriptionNotFound() {
            //Arrange
            when(clientSubscriptionRepository.findById(1)).thenReturn(Optional.empty());

            //Act & Assert
            assertThrows(NoSuchClientSubscriptionException.class, () -> clientSubscriptionService.activateClientSubscription(1));
            verifyNoMoreInteractions(clientSubscriptionRepository);

        }

    }

    @Nested
    @DisplayName("deactivateClientSubscription()")
    class DeactivateClientSubscriptionTests {

        @DisplayName("Should deactivate client subscription when client subscription exists")
        @Test
        void shouldDeactivateClientSubscription_whenClientSubscriptionExists() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));
            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);
            LocalDate date = LocalDate.of(2025, 8, 29);
            ClientSubscription clientSubscription = TestClientSubscriptionFactory.create(1, client, subscription, date);

            when(clientSubscriptionRepository.findById(1)).thenReturn(Optional.of(clientSubscription));

            //Act
            clientSubscriptionService.deactivateClientSubscription(1);

            //Assert
            verify(clientSubscriptionRepository).findById(anyInt());
            verify(clientSubscriptionRepository).save(captor.capture());
            ClientSubscription result = captor.getValue();
            assertThat(result.getActivationDate()).isNull();

        }

        @DisplayName("Should throw exception when client subscription not found")
        @Test
        void shouldThrowException_whenClientSubscriptionNotFound() {
            //Arrange
            when(clientSubscriptionRepository.findById(1)).thenReturn(Optional.empty());

            //Act & Assert
            assertThrows(NoSuchClientSubscriptionException.class, () -> clientSubscriptionService.deactivateClientSubscription(1));
            verifyNoMoreInteractions(clientSubscriptionRepository);

        }

    }

    @Nested
    @DisplayName("deleteClientSubscriptionById()")
    class DeleteClientSubscriptionByIdTests {

        @DisplayName("Should delete client subscription when client subscription exists")
        @Test
        void shouldDeleteClientSubscription_whenClientSubscriptionExists() {
            when(clientSubscriptionRepository.existsById(1)).thenReturn(true);

            clientSubscriptionService.deleteClientSubscriptionById(1);

            verify(clientSubscriptionRepository).deleteById(1);
        }

        @DisplayName("Should throw exception when client subscription not found")
        @Test
        void shouldThrowException_whenClientSubscriptionNotFoundForDelete() {
            when(clientSubscriptionRepository.existsById(1)).thenReturn(false);

            assertThrows(NoSuchClientSubscriptionException.class, () -> clientSubscriptionService.deleteClientSubscriptionById(1));
            verify(clientSubscriptionRepository, never()).deleteById(anyInt());
            verifyNoMoreInteractions(clientSubscriptionRepository);

        }

    }

    @Nested
    @DisplayName("getClientSubscriptions()")
    class GetClientSubscriptionsTests {

        @DisplayName("Should return DTO when client exists")
        @Test
        void shouldReturnSortedDto_whenClientExists() {
            //Arrange
            Client client = TestClientFactory.create(1, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", "ivan",
                    LocalDateTime.of(2025, 1, 10, 9, 30, 0));

            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            ClientSubscription clientSubscription1 = TestClientSubscriptionFactory.create(1, client, subscription, LocalDate.now());
            ClientSubscription clientSubscription2 = TestClientSubscriptionFactory.create(2, client, subscription, LocalDate.of(2025, 7,1));
            ClientSubscription clientSubscription3 = TestClientSubscriptionFactory.create(3, client, subscription, null);
            List<ClientSubscription> subscriptionList = List.of(clientSubscription1, clientSubscription2, clientSubscription3);

            when(clientRepository.findById(1)).thenReturn(Optional.of(client));
            when(clientSubscriptionRepository.findByClientId(1)).thenReturn(subscriptionList);

            //Act
            AdminClientWithSubscriptionsDto result = clientSubscriptionService.getClientSubscriptions(1);

            //Assert
            List<Integer> actualIds = result.getClientSubscriptions().stream()
                    .map(AdminClientSubscriptionResponseDto::getClientSubscriptionId)
                    .collect(Collectors.toList());
            assertThat(actualIds)
                    .containsExactly(3, 1, 2);

        }


        @DisplayName("Should throw exception when client not found")
        @Test
        void shouldThrowException_whenClientNotFoundForDelete() {
            when(clientRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(NoSuchClientException.class, () -> clientSubscriptionService.getClientSubscriptions(1));
            verify(clientSubscriptionRepository, never()).findByClientId(anyInt());
        }

    }
}


