package com.github.alinaguzova.projects.fitness_club_management_system.archived.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Архивная сущность. Не используется в текущей архитектуре.
 */
@Deprecated
@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "trainer_name")
    private String name;

    @Column(name = "trainer_email")
    private String email;

    @Column(name = "trainer_phone")
    private String phone;

    @Column(name = "trainer_price")
    private BigDecimal sessionPrice;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.REMOVE)
    private List<TrainerSchedule> schedules;

    public Trainer() {
    }

    public void addSchedule(TrainerSchedule schedule){
        if (schedules == null){
            schedules = new ArrayList<>();
        }
        schedules.add(schedule);
        schedule.setTrainer(this);

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getSessionPrice() {
        return sessionPrice;
    }

    public void setSessionPrice(BigDecimal sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<TrainerSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TrainerSchedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sessionPrice=" + sessionPrice +
                ", specialization=" + specialization.getName() +
                '}';
    }
}
