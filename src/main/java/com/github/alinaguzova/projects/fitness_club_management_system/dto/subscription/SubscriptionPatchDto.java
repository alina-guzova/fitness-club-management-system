package com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription;

import com.github.alinaguzova.projects.fitness_club_management_system.validation.ValidPrice;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for partial updates to subscription via administrative PATCH request.
 * Used by administrators to modify selected fields such as name, description, price, duration, visit count, or active status.
 * All fields are optional and validated individually for format, value range, and business rules.
 * Applied in admin-facing endpoints responsible for subscription management.
 */

public class SubscriptionPatchDto {

    @Size(max = 30, message = "Наименование должно содержать не более 30 символов")
    private String name;

    private String description;

    @ValidPrice
    private BigDecimal price;

    @Min(1)
    private Integer durationDays;

    @Min(1)
    private Integer visitCount;

    private Boolean isActive;

    public SubscriptionPatchDto() {
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

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
