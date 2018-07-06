package com.elpmid.todo.factory;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoStatus;
import com.elpmid.todo.dto.TodoUpdate;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class TodoDTOFactory {

    public static TodoCreate createTodoCreate() {
        return new TodoCreate(
                UUID.randomUUID(),
                "Name",
                "Description",
                LocalDate.now().plusDays(1),
                CommonFactory.randomTodoStatus()
        );
    }

    public static TodoUpdate createTodoUpdate() {
        return new TodoUpdate(
                "Name",
                "Description",
                LocalDate.now().plusDays(1),
                CommonFactory.randomTodoStatus()
        );
    }

}