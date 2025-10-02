package com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription;

import java.time.LocalDate;

/**
 * DTO for displaying a client's subscription details in their personal profile.
 * Includes subscription name, duration, visit count, and activation/expiration dates.
 * Used in client-facing endpoints to show client's subscriptions.
 */

public class ClientSubscriptionResponseDto {

    private String name;
    private int durationDays;
    private int visitCount;
    private LocalDate activationDate;
    private LocalDate expirationDate;

    public ClientSubscriptionResponseDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
