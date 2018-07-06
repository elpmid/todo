package com.elpmid.todo.domain;

import com.elpmid.todo.dto.TodoStatus;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class TodoDomain {

    private UUID id;
    private String name;
    private String description;
    private LocalDate dueDate;
    private TodoStatus status;

    public TodoDomain(UUID id, String name, String description, LocalDate dueDate, TodoStatus status) {
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

    @Override
    public boolean equals(Object compareTo) {
        if (this == compareTo) return true;
        if (!(compareTo instanceof TodoDomain)) return false;
        TodoDomain todo = (TodoDomain) compareTo;
        return Objects.equals(this.getId(), todo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
