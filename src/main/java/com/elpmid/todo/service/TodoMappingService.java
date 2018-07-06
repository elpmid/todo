package com.elpmid.todo.service;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;

public interface TodoMappingService {

    TodoResource todoDomainToTodoResource(TodoDomain todoDomain);
    TodoDomain createTodoDomain(TodoCreate todoCreate);
    TodoDomain updateTodoDomain(TodoDomain todoDomain, TodoUpdate todoUpdate);

}
