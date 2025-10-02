package com.github.alinaguzova.projects.fitness_club_management_system.archived.entities;

import jakarta.persistence.*;

import java.util.List;
/**
 * Архивная сущность. Не используется в текущей архитектуре.
 */
@Deprecated
@Entity
@Table(name = "specializations")
public class Specialization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "specialization_name")
    private String name;

    @OneToMany(mappedBy = "specialization")
    private List<Trainer> trainers;

    public Specialization() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "name='" + name + '\'' +
                '}';
    }
}
