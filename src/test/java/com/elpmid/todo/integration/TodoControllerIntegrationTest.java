package com.elpmid.todo.integration;

import com.elpmid.todo.TodoApplication;
import com.elpmid.todo.domain.TodoDomain;
import com.elpmid.todo.service.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TodoApplication.class)
@RunWith(SpringRunner.class)
public class TodoControllerIntegrationTest {

}
