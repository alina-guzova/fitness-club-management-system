package com.github.alinaguzova.projects.fitness_club_management_system.testutil;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class TestClientSubscriptionFactory {

    public static ClientSubscription create(int id, Client client, Subscription subscription, LocalDate date){

        ClientSubscription clientSubscription = new ClientSubscription();
        clientSubscription.setClient(client);
        clientSubscription.setSubscription(subscription);
        clientSubscription.setActivationDate(date);
        ReflectionTestUtils.setField(clientSubscription, "id", id);
        return clientSubscription;

    }



}
