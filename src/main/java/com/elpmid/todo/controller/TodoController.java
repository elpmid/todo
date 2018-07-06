package com.elpmid.todo.controller;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.Error;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoUpdate;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.service.TodoMappingService;
import com.elpmid.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(
        value = "/api/todo",
        consumes = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
        produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"
)
public class TodoController {

    private final TodoService todoService;
    private final TodoMappingService todoMappingService;

    public TodoController(TodoService todoService, TodoMappingService todoMappingService) {
        this.todoService = todoService;
        this.todoMappingService = todoMappingService;
    }

    // Please note this is not the way I would usually implement error handling
    // If I had more time I would write a Controller Advice to convert business
    // exceptions to error responses. In the getTodoById method if the todo
    // was not found I would raise a TodoNotFoundBusinessException (extended from BusinessException)
    // The Controller Advice would then map any business exceptions to an Error Response
    // It would also support returning multiple business exceptions instead of the one limited to below
    // Also the message would not be hardcoded it would be read from a properties file
    // This would also allow the mappings to be done in the service

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TodoResource> getTodoById(@PathVariable("id") UUID id) {
        Optional<TodoDomain> todoDomainOptional = todoService.findTodoById(id);
        if (todoDomainOptional.isPresent()) {
            TodoResource todoResponseDTO = todoMappingService.todoDomainToTodoResource(todoDomainOptional.get());
            return new ResponseEntity(todoResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    new Error("Unable to get, Todo with id " + id + " not found."),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<TodoResource>> getAllTodos() {
        List<TodoDomain> todoDomains = todoService.findAllTodos();
        if (!todoDomains.isEmpty()) {
            List<TodoResource> todoResponseDTOs = todoDomains.stream().map(todoMappingService::todoDomainToTodoResource).collect(Collectors.toList());
            return new ResponseEntity<>(todoResponseDTOs, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<TodoResource> createTodo(@RequestBody TodoCreate todoCreate) {
        TodoDomain todoDomain = todoMappingService.createTodoDomain(todoCreate);
        todoDomain =todoService.saveTodo(todoDomain);
        return new ResponseEntity(todoDomain, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TodoResource> updateTodo(@PathVariable("id") UUID id, @RequestBody TodoUpdate todoUpdate) {
        Optional<TodoDomain> todoDomainOptional = todoService.findTodoById(id);

        if (!todoDomainOptional.isPresent()) {
            return new ResponseEntity(
                    new Error("Unable to update. Todo with id " + id + " not found."),
                    HttpStatus.NOT_FOUND
            );
        }

        TodoDomain todoDomain = todoDomainOptional.get();
        todoDomain = todoMappingService.updateTodoDomain(todoDomain, todoUpdate);
        todoDomain = todoService.saveTodo(todoDomain);
        TodoResource todoResponseDTO = todoMappingService.todoDomainToTodoResource(todoDomain);
        return new ResponseEntity(todoResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TodoResource> deleteTodo(@PathVariable("id") UUID id) {
        Optional<TodoDomain> todoDomainOptional = todoService.findTodoById(id);

        if (!todoDomainOptional.isPresent()) {
            return new ResponseEntity(
                    new Error("Unable to delete. Todo with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        todoService.deleteTodoById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
