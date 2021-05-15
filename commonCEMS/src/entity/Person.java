package entity;

import java.io.Serializable;
/*
 * Person is made for employee and subscriber to extend it
 */
public abstract class Person implements Serializable {
	protected String firstName;
	protected String lastName;
	protected String id;
	protected String email;
	protected String role;
	
	
	public Person(String firstName, String lastName, String id, String email, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.role = role;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", id=" + id + ", email=" + email
				+ ", role=" + role + "]";
	}
	
	




}
