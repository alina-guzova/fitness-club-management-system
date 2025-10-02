package com.github.alinaguzova.projects.fitness_club_management_system.archived.entities;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Client;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Архивная сущность. Не используется в текущей архитектуре.
 */
@Deprecated
@Entity
@Table(name = "trainers_schedules")
public class TrainerSchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "day")
    private LocalDate sessionDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ScheduleStatus status;

    public TrainerSchedule() {
    }

    public TrainerSchedule(LocalDate sessionDate, LocalTime startTime, LocalTime endTime, Trainer trainer, Client client, ScheduleStatus status) {
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.trainer = trainer;
        this.client = client;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TrainerSchedule{" +
                "id=" + id +
                ", sessionDate=" + sessionDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trainer=" + trainer +
                ", status=" + status +
                '}';
    }
}
