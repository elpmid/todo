package com.elpmid.todo.unit;

import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.dto.TodoUpdate;
import com.elpmid.todo.factory.TodoDTOFactory;
import com.elpmid.todo.factory.TodoDomainFactory;
import com.elpmid.todo.service.TodoMappingServiceImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TodoMappingServiceUnitTest {

    private TodoMappingServiceImpl todoMappingService = new TodoMappingServiceImpl();

    @Test
    void testTodoDomainToTodoResource() {
        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        TodoResource todoResource = todoMappingService.todoDomainToTodoResource(todoDomain);
        assertThat(todoResource.getId(), equalTo(todoDomain.getId()));
        assertThat(todoResource.getName(), equalTo(todoDomain.getName()));
        assertThat(todoResource.getDescription(), equalTo(todoDomain.getDescription()));
        assertThat(todoResource.getDueDate(), equalTo(todoDomain.getDueDate()));
        assertThat(todoResource.getStatus(), equalTo(todoDomain.getStatus()));
    }

    @Test
    void testCreateTodoDomain() {
        TodoCreate todoCreate = TodoDTOFactory.createTodoCreate();
        TodoDomain todoDomain = todoMappingService.createTodoDomain(todoCreate);
        assertThat(todoDomain.getId(), equalTo(todoCreate.getId()));
        assertThat(todoDomain.getName(), equalTo(todoCreate.getName()));
        assertThat(todoDomain.getDescription(), equalTo(todoCreate.getDescription()));
        assertThat(todoDomain.getDueDate(), equalTo(todoCreate.getDueDate()));
        assertThat(todoDomain.getStatus(), equalTo(todoCreate.getStatus()));
    }

    @Test
    void testUpdateTodoDomain() {
        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        TodoUpdate todoUpdate = TodoDTOFactory.createTodoUpdate();
        TodoDomain todoDomainUpdated = todoMappingService.updateTodoDomain(todoDomain, todoUpdate);
        assertThat(todoDomainUpdated.getId(), equalTo(todoDomain.getId()));
        assertThat(todoDomainUpdated.getName(), equalTo(todoUpdate.getName()));
        assertThat(todoDomainUpdated.getDescription(), equalTo(todoUpdate.getDescription()));
        assertThat(todoDomainUpdated.getDueDate(), equalTo(todoUpdate.getDueDate()));
        assertThat(todoDomainUpdated.getStatus(), equalTo(todoUpdate.getStatus()));
    }
}
