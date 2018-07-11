package com.elpmid.todo.service;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoQueryStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoService {

    List<TodoDomain> findAllTodos(TodoQueryStatus status);

    Optional<TodoDomain> findTodoById(UUID id);

    TodoDomain saveTodo(TodoDomain todoDomain);

    void deleteTodoById(UUID id);

    void deleteAllTodos();
}
