package com.github.alinaguzova.projects.fitness_club_management_system.exception_handling;

import java.util.List;

public class NoSuchClientException extends RuntimeException{

    private final List<String> details;

    public NoSuchClientException(int id) {
        super("Клиент с id = " + id + " не найден");
        this.details = List.of("Проверьте корректность ID");;
    }

    public NoSuchClientException(String username) {
        super("Клиент с username = '" + username + "' не найден");
        this.details = List.of("Проверьте корректность username");
    }

    public List<String> getDetails() {
        return details;
    }
}
