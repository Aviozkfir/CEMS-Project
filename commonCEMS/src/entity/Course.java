package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

	private String name;
	private String id;
	private ArrayList<String> TeachersID = new ArrayList<String>(); // array list that contains the teachers that teach
																	// this field of subject.
	private ArrayList<String> StudentsID = new ArrayList<String>(); // array list that contains the students that study
																	// this field of subject.

	public void setId(String id) {
		this.id = id;
	}

	public Course(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public ArrayList<String> getTeachersID() {
		return TeachersID;
	}

	public ArrayList<String> getStudentsID() {
		return StudentsID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((StudentsID == null) ? 0 : StudentsID.hashCode());
		result = prime * result + ((TeachersID == null) ? 0 : TeachersID.hashCode());
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
		Course other = (Course) obj;
		if (StudentsID == null) {
			if (other.StudentsID != null)
				return false;
		} else if (!StudentsID.equals(other.StudentsID))
			return false;
		if (TeachersID == null) {
			if (other.TeachersID != null)
				return false;
		} else if (!TeachersID.equals(other.TeachersID))
			return false;
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

}
