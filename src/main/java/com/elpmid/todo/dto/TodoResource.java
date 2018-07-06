package com.elpmid.todo.dto;

import java.time.LocalDate;
import java.util.UUID;

public class TodoResource {

    private UUID id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private TodoStatus status;

    public TodoResource(UUID id, String name, String description, LocalDate dueDate, TodoStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }
}
