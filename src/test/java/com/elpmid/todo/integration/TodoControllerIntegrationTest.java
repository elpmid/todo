package com.elpmid.todo.integration;

import com.elpmid.todo.TodoApplication;
import com.elpmid.todo.dao.TodoDAO;
import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.dto.TodoCreate;
import com.elpmid.todo.dto.TodoStatus;
import com.elpmid.todo.dto.TodoUpdate;
import com.elpmid.todo.factory.TodoDTOFactory;
import com.elpmid.todo.factory.TodoDomainFactory;
import com.elpmid.todo.service.TodoService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TodoApplication.class)
@RunWith(SpringRunner.class)
public class TodoControllerIntegrationTest {

    @Inject
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Inject
    private TodoService todoService;

    @Inject
    private TodoDAO todoDAO;

    @Inject
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        // clear an existing test data
        todoService.deleteAllTodos();
    }

    @Test
    public void getTodoById_success() throws Exception {

        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain);

        mockMvc.perform(get("/api/todo/" + todoDomain.getId().toString())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(todoDomain.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(todoDomain.getName())))
                .andExpect(jsonPath("$.description", equalTo(todoDomain.getDescription())))
                .andExpect(jsonPath("$.dueDate", equalTo(todoDomain.getDueDate().toString())))
                .andExpect(jsonPath("$.status", equalTo(todoDomain.getStatus().toString())));
    }

    @Test
    public void getTodoById_not_found() throws Exception {

        String todoId = UUID.randomUUID().toString();
        mockMvc.perform(get("/api/todo/" + todoId)
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage", equalTo("Unable to get Todo with id " + todoId + ". It was not found.")));
    }

    @Test
    public void getAllTodos_success() throws Exception {

        TodoDomain todoDomain1 = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain1);

        TodoDomain todoDomain2 = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain2);

        mockMvc.perform(get("/api/todo/")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(containsString("\"id\":\"" + todoDomain1.getId().toString() + "\"")))
                .andExpect(content().string(containsString("\"id\":\"" + todoDomain2.getId().toString() + "\"")));
    }

    @Test
    public void getAllTodos_status() throws Exception {

        TodoDomain todoDomain1 = TodoDomainFactory.createTodoDomain();
        todoDomain1.setStatus(TodoStatus.PENDING);
        todoDAO.save(todoDomain1);

        TodoDomain todoDomain2 = TodoDomainFactory.createTodoDomain();
        todoDomain2.setStatus(TodoStatus.DONE);
        todoDAO.save(todoDomain2);

        mockMvc.perform(get("/api/todo/")
                .param("status", "PENDING")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().string(containsString("\"id\":\"" + todoDomain1.getId().toString() + "\"")));

        mockMvc.perform(get("/api/todo/")
                .param("status", "DONE")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(content().string(containsString("\"id\":\"" + todoDomain2.getId().toString() + "\"")));

    }


    @Test
    public void getAllTodos_none_created() throws Exception {

        mockMvc.perform(get("/api/todo/")
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void createTodo_success() throws Exception {

        TodoCreate todoCreate = TodoDTOFactory.createTodoCreate();

        mockMvc.perform(post("/api/todo/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(todoCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(todoCreate.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(todoCreate.getName())))
                .andExpect(jsonPath("$.description", equalTo(todoCreate.getDescription())))
                .andExpect(jsonPath("$.dueDate", equalTo(todoCreate.getDueDate().toString())));

        // check was saved
        Optional<TodoDomain> todoDomainOptional = todoDAO.findOneById(todoCreate.getId());
        assertThat(todoDomainOptional.isPresent(), equalTo(true));
        TodoDomain todoDomain = todoDomainOptional.get();
        assertThat(todoDomain.getId(), equalTo(todoCreate.getId()));
        assertThat(todoDomain.getName(), equalTo(todoCreate.getName()));
        assertThat(todoDomain.getDescription(), equalTo(todoCreate.getDescription()));
        assertThat(todoDomain.getStatus(), equalTo(todoCreate.getStatus()));
    }

    @Test
    public void createTodo_already_exists() throws Exception {

        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain);

        TodoCreate todoCreate = TodoDTOFactory.createTodoCreate(todoDomain.getId());

        mockMvc.perform(post("/api/todo/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(todoCreate)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage", equalTo("Unable to create Todo with id " + todoDomain.getId() + ". It already exists.")));

    }

    @Test
    public void createTodo_mandatory_fields() throws Exception {

        TodoCreate todoCreate = TodoDTOFactory.createTodoCreate();
        todoCreate.setId(null);
        todoCreate.setName(null);

        mockMvc.perform(post("/api/todo/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(todoCreate)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage",
                        equalTo("Field error in object 'todoCreate' on field 'id': rejected value [null];Field error in object 'todoCreate' on field 'name': rejected value [null]")));
    }

    @Test
    public void updateTodo_success() throws Exception {

        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain);

        TodoUpdate todoUpdate = TodoDTOFactory.createTodoUpdate();

        mockMvc.perform(put("/api/todo/" + todoDomain.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(todoUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(todoDomain.getId().toString())))
                .andExpect(jsonPath("$.name", equalTo(todoUpdate.getName())))
                .andExpect(jsonPath("$.description", equalTo(todoUpdate.getDescription())))
                .andExpect(jsonPath("$.dueDate", equalTo(todoUpdate.getDueDate().toString())))
                .andExpect(jsonPath("$.status", equalTo(todoUpdate.getStatus().toString())));

        // check was saved
        Optional<TodoDomain> todoDomainOptional = todoDAO.findOneById(todoDomain.getId());
        assertThat(todoDomainOptional.isPresent(), equalTo(true));
        TodoDomain todoDomainUpdated = todoDomainOptional.get();
        assertThat(todoDomainUpdated.getId(), equalTo(todoDomain.getId()));
        assertThat(todoDomainUpdated.getName(), equalTo(todoUpdate.getName()));
        assertThat(todoDomainUpdated.getDescription(), equalTo(todoUpdate.getDescription()));
        assertThat(todoDomainUpdated.getStatus(), equalTo(todoUpdate.getStatus()));
    }

    @Test
    public void updateTodo_mandatory_fields() throws Exception {

        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain);

        TodoUpdate todoUpdate = TodoDTOFactory.createTodoUpdate();
        todoUpdate.setName(null);
        todoUpdate.setDescription(null);

        mockMvc.perform(put("/api/todo/" + todoDomain.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(todoUpdate)))
                .andDo(print());
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errorMessage",
//                        equalTo("Field error in object 'todoUpdate' on field 'description': rejected value [null];Field error in object 'todoUpdate' on field 'name': rejected value [null]")));
    }

    @Test
    public void deleteTodo_success() throws Exception {

        TodoDomain todoDomain = TodoDomainFactory.createTodoDomain();
        todoDAO.save(todoDomain);

        mockMvc.perform(delete("/api/todo/" + todoDomain.getId().toString())
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNoContent());

        // check was deleted
        Optional<TodoDomain> todoDomain1Deleted = todoDAO.findOneById(todoDomain.getId());
        assertThat(todoDomain1Deleted.isPresent(), equalTo(false));
    }

    @Test
    public void deleteTodo_notFound() throws Exception {
        String todoId = UUID.randomUUID().toString();
        mockMvc.perform(delete("/api/todo/" + todoId)
                .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage", equalTo("Unable to delete Todo with id " + todoId + ". It was not found.")));

    }

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private byte[] convertObjectToJsonBytes(Object object) throws IOException {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsBytes(object);
    }
}
