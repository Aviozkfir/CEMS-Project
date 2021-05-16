package entity;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Teacher extends PersonCEMS {


	ArrayList<Subject> subjectList = new ArrayList<Subject>();
	ArrayList<Course> courseList = new ArrayList<Course>(); // Create an ArrayList object
	
	
	public Teacher(String firstName, String lastName,String id, String email, String role) {
		super(firstName,lastName,id,email,role);
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
