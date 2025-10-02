package com.github.alinaguzova.projects.fitness_club_management_system.testutil;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

public class TestSubscriptionFactory {

    public static Subscription create(int id, String name, String description, BigDecimal price, int durationDays, int visitCount, Boolean isActive){
        Subscription subscription = new Subscription();
        subscription.setName(name);
        subscription.setDescription(description);
        subscription.setPrice(price);
        subscription.setDurationDays(durationDays);
        subscription.setVisitCount(visitCount);
        if (isActive) {
            subscription.activate();
        } else {
            subscription.deactivate();
        }
        ReflectionTestUtils.setField(subscription,"id", id);

        return subscription;
    }
}
