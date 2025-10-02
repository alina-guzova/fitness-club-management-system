package com.github.alinaguzova.projects.fitness_club_management_system.repository;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.ClientSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientSubscriptionRepository extends JpaRepository<ClientSubscription, Integer> {

    List<ClientSubscription> findByClientId(int clientId);

}
