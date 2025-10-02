package com.github.alinaguzova.projects.fitness_club_management_system.archived.entities;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Архивная сущность. Не используется в текущей архитектуре.
 */
@Deprecated
@Entity
@Table(name = "clients_visit_history")
public class VisitHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "visit_datetime")
    private LocalDateTime visitDateTime;

    public VisitHistory() {
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getVisitDateTime() {
        return visitDateTime;
    }

    @Override
    public String toString() {
        return "VisitHistory{" +
                ", visitDateTime=" + visitDateTime +
                '}';
    }
}
