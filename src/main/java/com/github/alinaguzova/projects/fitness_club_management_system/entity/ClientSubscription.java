package com.github.alinaguzova.projects.fitness_club_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "clients_subscriptions")
public class ClientSubscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "activation_date")
    private LocalDate activationDate;


    public ClientSubscription() {
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    @Override
    public String toString() {
        return "ClientSubscription{" +
                "id=" + id +
                ", subscription=" + subscription +
                ", activationDate=" + activationDate +
                '}';
    }
}
