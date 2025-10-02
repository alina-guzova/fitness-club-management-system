package com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription;

import java.math.BigDecimal;

/**
 * DTO for exposing subscription data to administrators.
 * Contains internal fields such as id, name, description, price, duration days, visitCount, isActive.
 * Used in admin-facing endpoints for subscription management and audit purposes
 */

public class AdminSubscriptionResponseDto {

    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int durationDays;
    private int visitCount;
    private Boolean isActive;

    public AdminSubscriptionResponseDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
