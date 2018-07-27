package com.elpmid.todo.service;

import com.elpmid.todo.dao.TodoDAO;
import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoQueryStatus;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;
import com.elpmid.todo.exception.TodoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;

    private final TodoMappingService todoMappingService;

    public TodoServiceImpl( TodoMappingService todoMappingService, TodoDAO todoDAO) {
        this.todoMappingService = todoMappingService;
        this.todoDAO = todoDAO;
    }

    @Override
    public TodoResource findTodoById(UUID id) {
        Optional<TodoDomain> todoDomain = todoDAO.findOneById(id);
        if (!todoDomain.isPresent()) {
            throw new TodoNotFoundException("todo not found");
        }
        return todoMappingService.todoDomainToTodoResource(todoDomain.get());
    }

    @Override
    public List<TodoResource> findAllTodos(TodoQueryStatus status) {
        List<TodoDomain> todoDomains = todoDAO.findAll(status);
        return todoDomains.stream().map(todoMappingService::todoDomainToTodoResource).collect(Collectors.toList());
    }

    @Override
    public TodoResource createTodo(TodoCreate todoCreate) {
        TodoDomain todoDomain = todoMappingService.createTodoDomain(todoCreate);
        // handle Todo Already exists
        todoDomain = todoDAO.save(todoDomain);
        return todoMappingService.todoDomainToTodoResource(todoDomain);
    }

    @Override
    public TodoResource updateTodo(UUID id, TodoUpdate todoUpdate) {
        Optional<TodoDomain> todoDomainOptional = todoDAO.findOneById(id);
        if (!todoDomainOptional.isPresent()) {
            throw new TodoNotFoundException("todo not found");
        }
        TodoDomain todoDomain = todoDomainOptional.get();
        todoDomain = todoMappingService.updateTodoDomain(todoDomain, todoUpdate);
        todoDomain = todoDAO.save(todoDomain);
        return todoMappingService.todoDomainToTodoResource(todoDomain);
    }


    @Override
    public void deleteTodoById(UUID id) {
        Optional<TodoDomain> todoDomainOptional = todoDAO.findOneById(id);
        if (!todoDomainOptional.isPresent()) {
            throw new TodoNotFoundException("todo not found");
        }
        todoDAO.deleteById(id);
    }

    @Override
    public void deleteAllTodos() {
        todoDAO.deleteAll();
    }

}
