package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Principal extends PersonCEMS implements Serializable {

	private ArrayList<Subject> subjectList;
	private ArrayList<Course> courseList;
	private String str = "temp";

	public Principal(String firstName, String lastName, String id, String email, String role) {
		super(firstName, lastName, id, email, role);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

}
