package entity;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Teacher extends PersonCEMS implements Serializable {


	private ArrayList<Subject> subjectList;
	private ArrayList<Course> courseList; // Create an ArrayList object
	private Report report;
	private ArrayList<TeachersExam> examList;
	
	





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


	public Report getReport() {
		return report;
	}
	
	
	public void setReport(Report report) {
		this.report = report;
	}


	public ArrayList<TeachersExam> getExamList() {
		return examList;
	}


	public void setExamList(ArrayList<TeachersExam> examList) {
		this.examList = examList;
	}
	
	
}
