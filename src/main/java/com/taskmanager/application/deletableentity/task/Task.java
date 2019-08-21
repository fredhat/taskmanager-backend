package com.taskmanager.application.deletableentity.task;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.taskmanager.application.deletableentity.DeletableEntity;

/**
 * Task entity class bean. Reflects database fields and expected Task structure. 
 */

@Entity
@Table(name = "Tasks")
public class Task extends DeletableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	//Note: These annotations provide the bounds by which @Valid validates inputs.
	//		In most business applications name would also have a @Column(unique = true) annotation.
	//		In a more security-minded project, some of these fields would be sanitized with a regex like @Pattern(regexp="^[a-zA-Z0-9]")
	@Column(name = "name")
	@NotNull(message="Required field")
    @Size(min=1, max=100, message="Maximum length 100 characters")
	private String name;
	@Column(name = "description")
	@Size(max=250, message="Maximum length 250 characters")
	private String description;
	@Column(name = "deadline")
	private Date deadline;
	@Column(name = "is_completed")
	private boolean isCompleted = false;
	
	//Note: If this application required logging of when Tasks were created, this constructor would be used alongside the standard constructor to set that final timestamp.
	private Task() {
		
	}
	
	public Task(String name, String description, Date deadline) {
		this.name = name;
		this.description = description;
		this.deadline = deadline;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsCompleted(boolean complete) {
		this.isCompleted = complete;
	}
	
}
