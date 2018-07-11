package com.elpmid.todo.factory;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoStatus;

import java.time.LocalDate;
import java.util.UUID;

public class TodoDomainFactory {

    public static TodoDomain createTodoDomain(UUID id) {
        return createTodoDomain(
                id,
                "Name",
                "Description",
                LocalDate.now().plusDays(1),
                CommonFactory.randomTodoStatus()
        );
    }

    public static TodoDomain createTodoDomain() {
        return createTodoDomain(
                UUID.randomUUID()
        );
    }

    public static TodoDomain createTodoDomain(
            UUID id,
            String name,
            String description,
            LocalDate dueDate,
            TodoStatus status
    ) {
        return new TodoDomain(
                id,
                name,
                description,
                dueDate,
                status
        );
    }

    public static TodoDomain createTodoDomain(
            String name,
            String description,
            LocalDate dueDate,
            TodoStatus status
    ) {
        return createTodoDomain(
                UUID.randomUUID(),
                name,
                description,
                dueDate,
                status
        );
    }
}
