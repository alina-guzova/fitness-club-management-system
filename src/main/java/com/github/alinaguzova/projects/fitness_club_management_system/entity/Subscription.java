package com.github.alinaguzova.projects.fitness_club_management_system.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "subscription_name")
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "current_price")
    private BigDecimal price;
    @Column(name = "duration_days")
    private int durationDays;

    @Column(name = "visit_count")
    private int visitCount;

    @Column(name = "is_active")
    private Boolean isActive;

    public Subscription() {
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

    public void activate() {
        isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }


    @Override
    public String toString() {
        return "Subscription{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + durationDays +
                ", visitCount=" + visitCount +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Subscription s = (Subscription) obj;
        return id == s.getId();
    }
}
