package com.era.community.assignment.ui.dto; 

import com.era.community.assignment.dao.generated.AssignmentAssigneeLinkEntity;

public class AssignmentAssigneeDto extends AssignmentAssigneeLinkEntity 
{
	private int Assigned;
	private String FirstName;
	private String LastName;
	private boolean PhotoPresent;

	public int getAssigned() {
		return Assigned;
	}

	public void setAssigned(Long assigned) {
		Assigned = assigned.intValue();
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public boolean isPhotoPresent() {
		return PhotoPresent;
	}

	public void setPhotoPresent(boolean photoPresent) {
		PhotoPresent = photoPresent;
	}
}