package com.elpmid.todo.factory;

import com.elpmid.todo.dto.TodoStatus;

import java.util.Random;

public class CommonFactory {
    public static TodoStatus randomTodoStatus() {
        return TodoStatus.values()[new Random().nextInt(TodoStatus.values().length)];
    }
}
