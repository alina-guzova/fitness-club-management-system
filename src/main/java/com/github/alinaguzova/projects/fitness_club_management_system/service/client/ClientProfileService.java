package com.github.alinaguzova.projects.fitness_club_management_system.service.client;

import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientPatchDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client.ClientResponseDto;
import com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription.ClientSubscriptionResponseDto;

import java.util.List;

public interface ClientProfileService {

    ClientResponseDto getProfile();
    ClientResponseDto updateProfile(ClientPatchDto dto);

    List<ClientSubscriptionResponseDto> getMySubscriptions();

    //добавить другие сущности как получитьРасписание, получить списокПодписок и т.д.
    // не как у админа, у клиента все должно быть в одном классе
}
