package com.elpmid.todo.service;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoQueryStatus;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoService {

    List<TodoResource> findAllTodos(TodoQueryStatus status);

    TodoResource findTodoById(UUID id);

    TodoResource createTodo(TodoCreate todoCreate);

    TodoResource updateTodo(UUID id, TodoUpdate todoUpdate);

    void deleteTodoById(UUID id);

    void deleteAllTodos();
}
