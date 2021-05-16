package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Subject implements Serializable {

	private String name;
	private String id;
	private ArrayList<String> TeachersID = new ArrayList<String>(); // array list that contains the teachers that teach
																	// this field of subject.
	private ArrayList<String> StudentsID = new ArrayList<String>(); // array list that contains the students that study
																	// this field of subject.

	public void setId(String id) {
		this.id = id;
	}

	public void setTeachersID(ArrayList<String> teachersID) {
		TeachersID = teachersID;
	}

	public void setStudentsID(ArrayList<String> studentsID) {
		StudentsID = studentsID;
	}

	public Subject(String name) {

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() { // make method in sqlconnection that gets id from db.
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public ArrayList<String> getTeachersID() {
		return TeachersID;
	}

	public ArrayList<String> getStudentsID() {
		return StudentsID;
	}

}
