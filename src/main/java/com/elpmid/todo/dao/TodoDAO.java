package com.elpmid.todo.dao;

import com.elpmid.todo.domain.TodoDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoDAO {

    List<TodoDomain> findAll();

    Optional<TodoDomain> findOneById(UUID id);

    TodoDomain save(TodoDomain todo);

    void deleteById(UUID id);

    void deleteAll();
}
