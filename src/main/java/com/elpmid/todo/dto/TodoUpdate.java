package com.elpmid.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TodoUpdate {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    private TodoStatus status;

    public TodoUpdate() {
        super();
    }

    public TodoUpdate(String name, String description, LocalDate dueDate, TodoStatus status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
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
