package com.github.alinaguzova.projects.fitness_club_management_system.testutil;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class TestClientFactory {

    public static Client create(int id, String name, String email, String phone, String username, LocalDateTime date){

        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        client.setUsername(username);
        ReflectionTestUtils.setField(client, "id", id);
        ReflectionTestUtils.setField(client, "registrationDate", date);

        return client;



    }



}
