package com.github.alinaguzova.projects.fitness_club_management_system.service.admin;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.*;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.exception_handling.NoSuchClientException;
import com.github.alinaguzova.projects.fitness_club_management_system.repository.ClientRepository;
import com.github.alinaguzova.projects.fitness_club_management_system.testutil.TestClientFactory;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit tests for ClientServiceImpl")
@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Nested
    @DisplayName("getAllClients()")
    class GetClientsTests {

        @DisplayName("Should return DTO list")
        @Test
        void shouldReturnClientDtoList() {
            //Arrange
            Client client1 = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));
            Client client2 = TestClientFactory.create(2, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", "ivan",
                    LocalDateTime.of(2025, 8, 10, 9, 30, 0));

            List<Client> clients = List.of(client1, client2);
            when(clientRepository.findAll()).thenReturn(clients);
            //Act

            List<AdminClientResponseDto> result = clientService.getAllClients();

            //Assert
            assertThat(result)
                    .extracting(
                            AdminClientResponseDto::getId,
                            AdminClientResponseDto::getName,
                            AdminClientResponseDto::getEmail,
                            AdminClientResponseDto::getPhone,
                            AdminClientResponseDto::getRegistrationDate
                    )
                    .containsExactly(
                            tuple(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", LocalDateTime.of(2025, 8, 9, 2, 52, 12)),
                            tuple(2, "Ivan Petrov", "ivanpetrov@gmail.com", "+375291234567", LocalDateTime.of(2025, 8, 10, 9, 30, 0))
                    );

        }
    }

    @Nested
    @DisplayName("getClientById()")
    class GetClientByIdTests {

        @DisplayName("Should return DTO when client exists")
        @Test
        void shouldReturnClientDto_whenClientExists() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));

            when(clientRepository.findById(1)).thenReturn(Optional.of(client));

            //Act
            AdminClientResponseDto result = clientService.getClientById(1);

            //Assert
            assertThat(List.of(result))
                    .extracting(
                            AdminClientResponseDto::getId,
                            AdminClientResponseDto::getName,
                            AdminClientResponseDto::getEmail,
                            AdminClientResponseDto::getPhone,
                            AdminClientResponseDto::getRegistrationDate
                    )
                    .containsExactly(
                            tuple(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165",
                                    LocalDateTime.of(2025, 8, 9, 2, 52, 12))
                    );

        }

        @DisplayName("Should throw exception when client not found")
        @Test
        void shouldThrowException_whenClientNotFound() {
            when(clientRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(NoSuchClientException.class, () -> clientService.getClientById(1));

        }
    }

    @Nested
    @DisplayName("updateClient()")
    class UpdateClientTests {

        @DisplayName("Should update client and return updated DTO")
        @Test
        void shouldUpdateClient_whenClientExists() {
            //Arrange
            Client client = TestClientFactory.create(1, "Vera Morozova", "veramoroz@gmail.com", "+375291451165", "vera",
                    LocalDateTime.of(2025, 8, 9, 2, 52, 12));
            ClientPatchDto patchDto = new ClientPatchDto();
            patchDto.setName("Vera Petrova");

            when(clientRepository.findById(1)).thenReturn(Optional.of(client));
            when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

            //Act
            AdminClientResponseDto result = clientService.updateClient(1, patchDto);

            //Assert
            assertThat(List.of(result))
                    .extracting(
                            AdminClientResponseDto::getId,
                            AdminClientResponseDto::getName,
                            AdminClientResponseDto::getEmail,
                            AdminClientResponseDto::getPhone,
                            AdminClientResponseDto::getRegistrationDate
                    )
                    .containsExactly(
                            tuple(1, "Vera Petrova", "veramoroz@gmail.com", "+375291451165",
                                    LocalDateTime.of(2025, 8, 9, 2, 52, 12))
                    );
        }

        @DisplayName("Should throw exception when updating non-existent client")
        @Test
        void shouldThrowException_whenClientNotFoundForUpdate() {
            ClientPatchDto patchDto = new ClientPatchDto();
            patchDto.setName("Vera Petrova");

            when(clientRepository.findById(1)).thenReturn(Optional.empty());

            assertThrows(NoSuchClientException.class, () -> clientService.updateClient(1, patchDto));

        }
    }

    @Nested
    @DisplayName("deleteClientById()")
    class DeleteClientByIdTests {

        @DisplayName("Should delete client when client exists")
        @Test
        void shouldDeleteClient_whenClientExists() {
            int clientId = 1;
            when(clientRepository.existsById(clientId)).thenReturn(true);

            clientService.deleteClientById(clientId);

            verify(clientRepository).deleteById(clientId);
        }

        @DisplayName("Should throw exception when client does not exist")
        @Test
        void shouldThrowException_whenClientNotFoundForDelete() {
            int clientId = 1;
            when(clientRepository.existsById(clientId)).thenReturn(false);

            assertThrows(NoSuchClientException.class, () -> clientService.deleteClientById(clientId));
            verify(clientRepository, never()).deleteById(anyInt());
            verifyNoMoreInteractions(clientRepository);
        }
    }

}
