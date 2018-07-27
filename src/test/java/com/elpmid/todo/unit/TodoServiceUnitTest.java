package com.elpmid.todo.unit;

import com.elpmid.todo.dao.TodoDAO;
import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoQueryStatus;
import com.elpmid.todo.dto.TodoResource;
import com.elpmid.todo.factory.CommonFactory;
import com.elpmid.todo.factory.TodoDomainFactory;
import com.elpmid.todo.service.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceUnitTest {

    @Mock
    private TodoDAO todoDAO;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    void testFindTodoById() {
        UUID id = UUID.randomUUID();
        TodoDomain todoDomainExpected = TodoDomainFactory.createTodoDomain(id);
        Optional<TodoDomain> todoDomainOptionalExpected = Optional.of(todoDomainExpected);

        when(todoDAO.findOneById(any())).thenReturn(todoDomainOptionalExpected);

        Optional<TodoDomain> todoDomainOptionalActual = todoDAO.findOneById(id);
        assertThat(todoDomainOptionalActual.isPresent(), equalTo(true));
        TodoDomain todoDomainActual = todoDomainOptionalActual.get();
        assertThat(todoDomainActual.getId(), equalTo(id));
        assertThat(todoDomainActual.getName(), equalTo(todoDomainExpected.getName()));
        assertThat(todoDomainActual.getDescription(), equalTo(todoDomainExpected.getDescription()));
        assertThat(todoDomainActual.getDueDate(), equalTo(todoDomainExpected.getDueDate()));
        assertThat(todoDomainActual.getStatus(), equalTo(todoDomainExpected.getStatus()));

        verify(todoDAO).findOneById(any());
        verifyNoMoreInteractions(todoDAO);
    }

    @Test
    void testFindAllTodos() {
        List<TodoDomain> todoDomainsExpected = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> TodoDomainFactory.createTodoDomain(
                        "Name" + i,
                        "Description" + i,
                        LocalDate.now().plusDays(i),
                        CommonFactory.randomTodoStatus()
                        )
                ).collect(Collectors.toList());
        when(todoDAO.findAll(any())).thenReturn(todoDomainsExpected);

        List<TodoResource> todoResourcesActual = todoService.findAllTodos(TodoQueryStatus.ALL);
        assertThat(todoResourcesActual.size(), equalTo(todoDomainsExpected.size()));

        verify(todoDAO).findAll(any());
        verifyNoMoreInteractions(todoDAO);
    }

    @Test
    void testSaveTodo() {
        UUID id = UUID.randomUUID();
        TodoDomain todoDomainExpected = TodoDomainFactory.createTodoDomain(id);

        when(todoDAO.save(any())).thenReturn(todoDomainExpected);

        TodoDomain todoDomainActual = todoDAO.save(todoDomainExpected);
        assertThat(todoDomainActual.getId(), equalTo(id));
        assertThat(todoDomainActual.getName(), equalTo(todoDomainExpected.getName()));
        assertThat(todoDomainActual.getDescription(), equalTo(todoDomainExpected.getDescription()));
        assertThat(todoDomainActual.getDueDate(), equalTo(todoDomainExpected.getDueDate()));
        assertThat(todoDomainActual.getStatus(), equalTo(todoDomainExpected.getStatus()));

        verify(todoDAO).save(todoDomainExpected);
        verifyNoMoreInteractions(todoDAO);
    }

    @Test
    void testDeleteTodoById() {
        UUID id = UUID.randomUUID();
        ArgumentCaptor<UUID> argument = ArgumentCaptor.forClass(UUID.class);
        todoService.deleteTodoById(id);
        verify(todoDAO).deleteById(argument.capture());
        assertThat(argument.getValue(), equalTo(id));

        verify(todoDAO).deleteById(id);
        verifyNoMoreInteractions(todoDAO);
    }

    @Test
    void deleteAllTodos() {
        todoService.deleteAllTodos();

        verify(todoDAO).deleteAll();
        verifyNoMoreInteractions(todoDAO);
    }

}
