package com.github.alinaguzova.projects.fitness_club_management_system.dto.client_subscription;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for exposing details of a specific subscription assigned to a client.
 * Includes subscription metadata along with activation and expiration dates.
 * Used as a nested component within {@link AdminClientWithSubscriptionsDto} for administrative views.
 */

public class AdminClientSubscriptionResponseDto {

    private int clientSubscriptionId;
    private String name;
    private String description;
    private BigDecimal price;
    private int durationDays;
    private int visitCount;
    private LocalDate activationDate;
    private LocalDate expirationDate;

    public AdminClientSubscriptionResponseDto() {
    }

    public int getClientSubscriptionId() {
        return clientSubscriptionId;
    }

    public void setClientSubscriptionId(int clientSubscriptionId) {
        this.clientSubscriptionId = clientSubscriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
