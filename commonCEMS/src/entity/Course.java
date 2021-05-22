package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

	private String name;
	private String id;
	private Subject subject;

	// private ArrayList<String> TeachersID = new ArrayList<String>(); // array list
	// that contains the teachers that teach
	// this field of subject.
	// private ArrayList<String> StudentsID = new ArrayList<String>(); // array list
	// that contains the students that study
	// this field of subject.

	public Course(String name, String id,Subject subject) {
		this.name = name;
		this.id = id;
		this.subject=subject;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Subject getSubject() {
		return subject;
	}

	@Override
	public String toString() {
		return "Course [name=" + name + ", id=" + id + ", subject=" + subject + "]";
	}
	
	

	// public ArrayList<String> getTeachersID() {
	// return TeachersID;
//	}

	// public ArrayList<String> getStudentsID() {
	// return StudentsID;
	// }

}
