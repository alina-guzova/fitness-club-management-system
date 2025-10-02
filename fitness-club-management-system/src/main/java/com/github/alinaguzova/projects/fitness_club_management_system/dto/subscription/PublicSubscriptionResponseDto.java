package com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription;

import java.math.BigDecimal;

/**
 * DTO for exposing subscription details to any user, including unauthenticated visitors.
 * Contains public-facing fields such as name, description, price, duration, and visit count.
 * Used in public endpoints to display available subscription plans.
 */

public class PublicSubscriptionResponseDto {

    private String name;
    private String description;
    private BigDecimal price;
    private int durationDays;
    private int visitCount;

    public PublicSubscriptionResponseDto() {
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
}
