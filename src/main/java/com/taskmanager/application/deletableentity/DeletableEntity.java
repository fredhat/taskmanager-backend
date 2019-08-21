package com.taskmanager.application.deletableentity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Abstract class for building entities with soft-delete functionality.
 */

@MappedSuperclass
public abstract class DeletableEntity {
	
	//Note: Soft-delete functionality is not required by the scope of this project but is vital for any business-grade application that requires logging and accountability.
	@Column(name = "isDeleted")
	private boolean isDeleted = false;
	
	public abstract Long getId();
	
	//Note: The non-standard formatting on the isDeleted getters and setters is a result of how Spring handles serializing booleans to and from json format. 
	public boolean getIsDeleted() {
		return isDeleted;
	};
	
	public void setIsDeleted(boolean delete) {
		this.isDeleted = delete;
	};
	
}
