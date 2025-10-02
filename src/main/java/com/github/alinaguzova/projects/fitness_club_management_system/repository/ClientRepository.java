package com.github.alinaguzova.projects.fitness_club_management_system.repository;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByUsername(String username);

}
