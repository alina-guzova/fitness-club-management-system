package com.github.alinaguzova.projects.fitness_club_management_system.specification;

import com.github.alinaguzova.projects.fitness_club_management_system.entity.Subscription;
import org.springframework.data.jpa.domain.Specification;


public class SubscriptionSpecification {

    public static Specification<Subscription> hasActive(Boolean active){
        return ((root, query, criteriaBuilder) -> active == null ? null : criteriaBuilder.equal(root.get("isActive"), active));
    }
    public static Specification<Subscription> priceFrom(Integer priceFrom){
        return ((root, query, criteriaBuilder) -> priceFrom == null ? null : criteriaBuilder.ge(root.get("price"), priceFrom));
    }
}
