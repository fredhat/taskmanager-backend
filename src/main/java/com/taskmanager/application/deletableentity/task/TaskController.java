package com.taskmanager.application.deletableentity.task;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for Task manipulation.
 */

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

	private final TaskRepository taskRepository;
	
	/**
	 * TaskRepository provides an interface between the api endpoints and the database.
	 */
	
	public TaskController(TaskRepository taskRepository){
		this.taskRepository = taskRepository;
	}
	
	/**
	 * GET /tasks
	 * Returns a list of all Tasks in TaskRepository as well as a 200 status.
	 * Can return an empty list.
	 * Will not return soft-deleted Tasks.
	 */
	
	@GetMapping("/tasks")
	public List<Task> getTasks(){
		return taskRepository.findAll();
	}
	
	/**
	 * GET /tasks/{id}
	 * Returns a single Task based on its id as well as a 200 status.
	 * Throws an EntityNotFoundException and returns a 404 status if it cannot find the specified Task.
	 * Will not return soft-deleted Tasks.
	 */
	
	@GetMapping("/tasks/{id}")
	public Task getTask(@PathVariable Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (!task.isPresent()) {
			throw new EntityNotFoundException("Task not found.");
		}
		return task.get();
	}
	
	/**
	 * POST /tasks
	 * Creates and returns a new Task as well as a 201 status. 
	 * Throws a MethodArgumentNotValidException and returns a 400 status if the request body is formatted incorrectly.
	 */
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/tasks")
	public Task createtask(@Valid @RequestBody Task newTask) {
		return taskRepository.save(newTask);
	}
	
	/**
	 * PUT /tasks/{id}
	 * Updates and returns a single Task based on its id as well as a 200 status.
	 * Throws an EntityNotFoundException and returns a 404 status if it cannot find the specified Task.
	 * Currently this task is only used to update the selected tasks icCompleted field.
	 * In a more robust TaskController implementation this endpoint would be used for general purpose updating instead.
	 */
	
	@PutMapping("/tasks/{id}")
	public Task completeTask(@PathVariable Long id) {
		Optional<Task> task = taskRepository.findById(id);
		if (!task.isPresent()) {
			throw new EntityNotFoundException("Task not found.");
		}
		task.get().setIsCompleted(true);
		return taskRepository.save(task.get());
	}
	
	/**
	 * DELETE /tasks/{id}
	 * Soft-deletes and returns a single Task based on its id as well as a 200 status.
	 * Throws an EntityNotFoundException and returns a 404 status if it cannot find the specified Task.
	 */
	
	//Note: Since this endpoint does not actually delete the Task, it is able to return it as part of its response.
	@DeleteMapping("/tasks/{id}")
	public Task deleteTask(@PathVariable Long id){
		Optional<Task> task = taskRepository.findById(id);
		if (!task.isPresent()) {
			throw new EntityNotFoundException("Task not found.");
		}
		task.get().setIsDeleted(true);
		return taskRepository.save(task.get());
	}
	
}
