package com.github.alinaguzova.projects.fitness_club_management_system.dto.subscription;

import com.github.alinaguzova.projects.fitness_club_management_system.validation.ValidPrice;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for creating a new subscription via administrative POST request.
 * Used by administrators to define subscription parameters such as name, description, price, duration, and visit count.
 * Validated using standard and custom annotations to ensure format, length, presence and minimum values.
 * Used in admin-facing endpoint: {@code POST /api/admin/subscriptions}.
 */

public class SubscriptionCreateDto {

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 30, message = "Наименование должно содержать не более 30 символов")
    private String name;

    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    @NotNull(message = "Стоимость должна быть указана")
    @ValidPrice
    private BigDecimal price;

    @NotNull(message = "Срок действия должен быть указан")
    @Min(1)
    private Integer durationDays;

    @NotNull(message = "Количесво посещений должено быть заполнено")
    @Min(1)
    private Integer visitCount;

    public SubscriptionCreateDto() {
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
}
