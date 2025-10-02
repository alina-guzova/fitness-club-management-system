package com.github.alinaguzova.projects.fitness_club_management_system.advice;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Translates low-level SQL constraint violation messages into user-friendly error descriptions.
 * Used primarily in exception handling to convert database constraint codes (e.g. "uq_clients_email")
 * into readable messages for frontend display.
 */

@Component
public class ConstraintTranslator {

    private static final Map<String, String> messages = Map.of(
            "uq_clients_phone", "Телефон уже зарегистрирован",
            "uq_clients_email", "Email уже зарегистрирован"
    );

    public List<String> translateToList(String sqlMessage) {
        return messages.entrySet().stream()
                .filter(entry -> sqlMessage.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}
