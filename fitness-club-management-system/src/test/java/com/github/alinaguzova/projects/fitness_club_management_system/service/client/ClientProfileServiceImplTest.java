package com.github.alinaguzova.projects.fitness_club_management_system.service.client;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientSubscriptionRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestClientFactory;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestClientSubscriptionFactory;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestSubscriptionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Unit tests for ClientProfileServiceImpl")
@ExtendWith(MockitoExtension.class)
public class ClientProfileServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientSubscriptionRepository clientSubscriptionRepository;

    @InjectMocks
    ClientProfileServiceImpl clientProfileService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    @DisplayName("getProfile()")
    class GetProfileTests {

        @DisplayName("Should return DTO when client exists")
        @Test
        void shouldReturnDto_whenClientExists(){
            //Arrange
            Client client = TestClientFactory.create(1, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", "ivan",
                    LocalDateTime.of(2025, 1, 10, 9, 30, 0));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("ivan", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            when(clientRepository.findByUsername("ivan")).thenReturn(Optional.of(client));

            //Act
            ClientResponseDto result = clientProfileService.getProfile();

            //Assert
            assertThat(result.getName()).isEqualTo(client.getName());
            assertThat(result.getEmail()).isEqualTo(client.getEmail());
            assertThat(result.getPhone()).isEqualTo(client.getPhone());

        }

        @DisplayName("Should throw exception when client not found")
        @Test
        void shouldThrowException_whenClientNotFound() {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("unknown_user", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            when(clientRepository.findByUsername("unknown_user")).thenReturn(Optional.empty());

            assertThrows(NoSuchClientException.class, () -> clientProfileService.getProfile());

        }

    }

    @Nested
    @DisplayName("updateProfile()")
    class UpdateProfileTests {

        @DisplayName("Should update profile and return updated DTO")
        @Test
        void shouldUpdateProfile_whenClientExists(){
            //Arrange
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("ivan", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            Client client = TestClientFactory.create(1, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", "ivan",
                    LocalDateTime.of(2025, 1, 10, 9, 30, 0));
            ClientPatchDto patchDto = new ClientPatchDto();
            patchDto.setName("Ivan Ivanov");

            when(clientRepository.findByUsername("ivan")).thenReturn(Optional.of(client));
            when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

            //Act
            ClientResponseDto result = clientProfileService.updateProfile(patchDto);

            //Assert
            assertThat(result.getName()).isEqualTo(patchDto.getName());
            verify(clientRepository).save(any(Client.class));

        }

        @DisplayName("Should throw exception when updating non-existent client")
        @Test
        void shouldThrowException_whenClientNotFoundForUpdate(){
            //Arrange
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("ivan", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            ClientPatchDto patchDto = new ClientPatchDto();
            patchDto.setName("Ivan Ivanov");

            when(clientRepository.findByUsername("ivan")).thenReturn(Optional.empty());

            //Act & Assert
            assertThrows(NoSuchClientException.class, () -> clientProfileService.updateProfile(patchDto));
            verifyNoMoreInteractions(clientRepository);

        }
    }

    @Nested
    @DisplayName("getMySubscriptions()")
    class GetMySubscriptionsTests {

        @DisplayName("Should return DTO when client exists")
        @Test
        void shouldReturnFilteredDto_whenClientExists() {
            //Arrange
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("ivan", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            Client client = TestClientFactory.create(1, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", "ivan",
                    LocalDateTime.of(2025, 1, 10, 9, 30, 0));

            Subscription subscription = TestSubscriptionFactory.create(1, "Базовый", "Зал без групповых", new BigDecimal("29.99"), 30, 12, true);

            ClientSubscription clientSubscription1 = TestClientSubscriptionFactory.create(1, client, subscription, null);
            ClientSubscription clientSubscription2 = TestClientSubscriptionFactory.create(2, client, subscription, LocalDate.of(2025, 7,1));
            ClientSubscription clientSubscription3 = TestClientSubscriptionFactory.create(3, client, subscription, LocalDate.now());
            List<ClientSubscription> subscriptionList = List.of(clientSubscription1, clientSubscription2, clientSubscription3);

            when(clientRepository.findByUsername("ivan")).thenReturn(Optional.of(client));
            when(clientSubscriptionRepository.findByClientId(client.getId())).thenReturn(subscriptionList);

            //Act
            List<ClientSubscriptionResponseDto> result = clientProfileService.getMySubscriptions();

            //Assert
            List<LocalDate> actualIds = result.stream()
                    .map(ClientSubscriptionResponseDto::getActivationDate)
                    .collect(Collectors.toList());

            assertThat(actualIds)
                    .containsExactly(clientSubscription1.getActivationDate(), clientSubscription3.getActivationDate());

        }

        @DisplayName("Should throw exception when client not found")
        @Test
        void shouldThrowException_whenClientNotFound() {
            //Arrange
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication auth = new UsernamePasswordAuthenticationToken("ivan", null);
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            when(clientRepository.findByUsername("ivan")).thenReturn(Optional.empty());


            assertThrows(NoSuchClientException.class, () -> clientProfileService.getMySubscriptions());
            verifyNoMoreInteractions(clientSubscriptionRepository);
        }

    }

}
