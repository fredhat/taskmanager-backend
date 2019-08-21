package com.taskmanager.application;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanager.application.deletableentity.task.Task;
import com.taskmanager.application.deletableentity.task.TaskRepository;

/**
 * Integration tests for testing api endpoints in a mock web environment.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
@Transactional
//Note: This allows the tests to be run on a database separate from the production database.
//		Since H2 runs in memory and does not persist between sessions, this in not a major concern for this project.
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApplicationTests {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
    private MockMvc mockMvc;
	
	//Note: This is unnecessary for this batch of tests, but can be useful if these tests were to be extended.
	@Autowired
    private TaskRepository mockTaskRepository;
	
	/**
	 * Main integration test for Task endpoint.
	 */
	
	@Test
    public void test_create_tasks_OK() throws Exception {

        Task firstTask = new Task("First Task", "Write more tests.", new Date(System.currentTimeMillis()));
        Task secondTask = new Task("Second Task", "Still more tests to go.", new Date(System.currentTimeMillis()));

        /**
         * Creates and validates two Tasks via the /tasks POST endpoint.
         */
        mockMvc.perform(post("/tasks")
                .content(MAPPER.writeValueAsString(firstTask))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("First Task")))
    	        .andExpect(jsonPath("$.description", is("Write more tests.")));
        
        mockMvc.perform(post("/tasks")
                .content(MAPPER.writeValueAsString(secondTask))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Second Task")))
    	        .andExpect(jsonPath("$.description", is("Still more tests to go.")));
        
        /**
         * Validates the two tasks separately via the /tasks/{id} GET endpoint.
         */
        mockMvc.perform(get("/tasks/1"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", is(1)))
	        .andExpect(jsonPath("$.name", is("First Task")))
	        .andExpect(jsonPath("$.description", is("Write more tests.")));
        
        mockMvc.perform(get("/tasks/2"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", is(2)))
	        .andExpect(jsonPath("$.name", is("Second Task")))
	        .andExpect(jsonPath("$.description", is("Still more tests to go.")));
        
        /**
         * Validates the two tasks simultaneously via the /tasks GET endpoint.
         */
        mockMvc.perform(get("/tasks"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].id", is(1)))
	        .andExpect(jsonPath("$[0].name", is("First Task")))
	        .andExpect(jsonPath("$[0].description", is("Write more tests.")))
	        .andExpect(jsonPath("$[1].id", is(2)))
	        .andExpect(jsonPath("$[1].name", is("Second Task")))
	        .andExpect(jsonPath("$[1].description", is("Still more tests to go.")));
        
        /**
         * Completes and validates a task via the /tasks/{id} PUT endpoint.
         */
        mockMvc.perform(put("/tasks/2"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", is(2)))
	        .andExpect(jsonPath("$.isCompleted", is(true)));
        
        /**
         * Deletes and validates a task via the /tasks/{id} DELETE endpoint.
         */
        mockMvc.perform(delete("/tasks/1"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", is(1)))
	        .andExpect(jsonPath("$.isDeleted", is(true)));
        
        /**
         * Validates soft-delete functionality via the /tasks GET endpoint.
         */
        mockMvc.perform(get("/tasks"))
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].id", is(2)))
	        .andExpect(jsonPath("$[0].isDeleted", is(false)));
        
        /**
         * Validates soft-delete functionality via the /tasks/{id} GET endpoint.
         */
        mockMvc.perform(get("/tasks/1"))
	    	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	    	.andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.message", is("Task not found.")));

    }
	
	/**
     * Validates exception handling when attempting to reference a non-existent Task via the /tasks/{id} GET endpoint.
     */
	
	@Test
    public void test_task_exists_NotFound() throws Exception {
        mockMvc.perform(get("/tasks/3"))
        	.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(status().isNotFound())
        	.andExpect(jsonPath("$.message", is("Task not found.")));
    }
	
	/**
     * Validates automated constraint validation when creating Tasks via the /tasks POST endpoint.
     */
	
	@Test
    public void test_create_task_BadRequest() throws Exception {

        Task newTask = new Task("This name is to loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong", "This should fail.", new Date(System.currentTimeMillis()));

        mockMvc.perform(post("/tasks")
        		.content(MAPPER.writeValueAsString(newTask))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8))
        		.andExpect(status().isBadRequest());

    }
	
}
