package com.github.alinaguzova.projects.fitness_club_management_system.repository;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>, JpaSpecificationExecutor<Subscription> {

}
