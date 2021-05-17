package entity;

import java.io.Serializable;
import java.util.ArrayList;


public class Student extends PersonCEMS implements Serializable {

	//private ArrayList<SolvedExam> examList = new ArrayList<SolvedExam>(); // need to create solved exam class and set/get
	private ArrayList<Subject> subjectList = new ArrayList<Subject>();
	private ArrayList<Course> courseList = new ArrayList<Course>(); 
	
	public Student(String firstName, String lastName,String id, String email, String role) {
		super(firstName, lastName,id, email, role);
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
