package com.elpmid.todo.service;


import com.elpmid.todo.dao.TodoDAO;
import com.elpmid.todo.domain.TodoDomain;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoDAO todoDAO;

    public TodoServiceImpl(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    @Override
    public Optional<TodoDomain> findTodoById(UUID id) {
        return todoDAO.findOneById(id);
    }

    @Override
    public List<TodoDomain> findAllTodos() {
        return todoDAO.findAll();
    }

    @Override
    public TodoDomain saveTodo(TodoDomain todoDomain) {
        return todoDAO.save(todoDomain);
    }

    @Override
    public void deleteTodoById(UUID id) {
        todoDAO.deleteById(id);
    }

    @Override
    public void deleteAllTodos() {
        todoDAO.deleteAll();
    }

}
