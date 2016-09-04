package com.era.community.pers.ui.dto; 

public class UserSearchDto
{
	private int Id;
	private String FirstName;
	private String LastName;
	private String PhotoPresent;
	private String EmailAddress;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
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
	public String getPhotoPresent() {
		return PhotoPresent;
	}
	public void setPhotoPresent(String photoPresent) {
		PhotoPresent = photoPresent;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
}