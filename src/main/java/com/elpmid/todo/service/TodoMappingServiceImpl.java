package com.elpmid.todo.service;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;
import org.springframework.stereotype.Service;

// I decided to make this a spring bean incase latter we need to inject another service

@Service
public class TodoMappingServiceImpl implements TodoMappingService {

    // Maybe use something like dozer to do the mapping

    @Override
    public TodoResource todoDomainToTodoResource(TodoDomain todoDomain) {
        return new TodoResource(
                todoDomain.getId(),
                todoDomain.getName(),
                todoDomain.getDescription(),
                todoDomain.getDueDate(),
                todoDomain.getStatus()
        );
    }

    @Override
    public TodoDomain createTodoDomain(TodoCreate todoCreate) {
        return new TodoDomain(
                todoCreate.getId(),
                todoCreate.getName(),
                todoCreate.getDescription(),
                todoCreate.getDueDate(),
                todoCreate.getStatus()
        );
    }

    @Override
    public TodoDomain updateTodoDomain(TodoDomain todoDomain, TodoUpdate todoUpdate) {
        todoDomain.setName(todoUpdate.getName());
        todoDomain.setDescription(todoUpdate.getDescription());
        todoDomain.setDueDate(todoUpdate.getDueDate());
        todoDomain.setStatus(todoUpdate.getStatus());
        return todoDomain;
    }

}
