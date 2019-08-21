package com.taskmanager.application.deletableentity.task;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.taskmanager.application.deletableentity.DeletableEntityRepository;

/**
 * Dummy interface for managing Task database queries.
 */

@CrossOrigin(origins = "http://localhost:4200")
public interface TaskRepository extends DeletableEntityRepository<Task, Long> {
	/**
	 * For the scope of this project, there is no need to modify how Tasks interface with the database.
	 * All necessary changes are handled through inheritance and default CrudRepository methods.
	 */
}