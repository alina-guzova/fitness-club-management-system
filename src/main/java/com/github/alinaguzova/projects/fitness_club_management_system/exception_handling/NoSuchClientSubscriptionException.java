package com.github.alinaguzova.projects.fitness_club_management_system.exception_handling;

import java.util.List;

public class NoSuchClientSubscriptionException extends RuntimeException{
    private final List<String> details;

    public NoSuchClientSubscriptionException(int id) {
        super("Абонемент с id = " + id + " не найден");
        this.details = List.of("Проверьте корректность ID");
    }

    public List<String> getDetails() {
        return details;
    }
}
