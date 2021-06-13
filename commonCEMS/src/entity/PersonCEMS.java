package entity;

import java.io.Serializable;

/**
 * A class that holds information about Person in CEMS System.
 * @author Shalom and Omer
 *
 */
@SuppressWarnings("serial")
public abstract class PersonCEMS implements Serializable {
	/**
	 * Holds the first name of person
	 */
	protected String firstName;
	/**
	 * Holds the last name of person
	 */
	protected String lastName;
	/**
	 * Holds the ID of person
	 */
	protected String id;
	/**
	 * Holds the email of person
	 */
	protected String email;
	/**
	 * Holds the role of person
	 */
	protected String role;
	
	
	/**
	 * A constructor for PersonCEMS given all the fields.
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param role
	 */
	public PersonCEMS(String firstName, String lastName, String id, String email, String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.role = role;
	}
	
	/**
	 * returns first name of person
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * sets first name of person
	 * @param String firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * returns last name of person
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * sets last name of person
	 * @param String lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * returns ID of person
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * sets ID of person
	 * @param String id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * returns email of person
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * sets email of person
	 * @param String email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * returns role of person
	 * @return String
	 */
	public String getRole() {
		return role;
	}
	/**
	 * sets role of person
	 * @param String role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 *toString for personCEMS
	 */
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName + ", id=" + id + ", email=" + email
				+ ", role=" + role + "]";
	}

	/**
	 *Generates hash code for person
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	/**
	 *Checks if PersonCEMS is equal to another PersonCEMS
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonCEMS other = (PersonCEMS) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}
	



}
