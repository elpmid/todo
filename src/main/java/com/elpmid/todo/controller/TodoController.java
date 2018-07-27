package com.elpmid.todo.controller;

import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoQueryStatus;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;
import com.elpmid.todo.service.TodoMappingService;
import com.elpmid.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(
        value = "/api/todo",
        consumes = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8",
        produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"
)
@CrossOrigin
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService, TodoMappingService todoMappingService) {
        this.todoService = todoService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getTodoById(@PathVariable("id") UUID id) {
        TodoResource todoResource = todoService.findTodoById(id);
        return new ResponseEntity<>(todoResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<TodoResource>> getAllTodos(@RequestParam(value = "status", defaultValue = "ALL") TodoQueryStatus status) {
        List<TodoResource> todoResources = todoService.findAllTodos(status);
        if (!todoResources.isEmpty()) {
            return new ResponseEntity<>(todoResources, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<TodoResource> createTodo(@Valid @RequestBody TodoCreate todoCreate) {
        return new ResponseEntity<>(todoService.createTodo(todoCreate), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TodoResource> updateTodo(@PathVariable("id") UUID id, @Valid @RequestBody TodoUpdate todoUpdate) {
        return new ResponseEntity<>(todoService.updateTodo(id, todoUpdate), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTodo(@PathVariable("id") UUID id) {
        todoService.deleteTodoById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
