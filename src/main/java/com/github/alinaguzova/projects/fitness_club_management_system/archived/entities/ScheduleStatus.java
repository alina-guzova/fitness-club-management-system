package com.github.alinaguzova.projects.fitness_club_management_system.archived.entities;

import jakarta.persistence.*;
/**
 * Архивная сущность. Не используется в текущей архитектуре.
 */
@Deprecated
@Entity
@Table(name = "status_types")
public class ScheduleStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code")
    private SessionStatusType statusCode;

    @Column(name = "status_name")
    private String statusName;

    public ScheduleStatus() {
    }

    public int getId() {
        return id;
    }

    public SessionStatusType getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(SessionStatusType statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "ScheduleStatus{" +
                "statusCode=" + statusCode +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
