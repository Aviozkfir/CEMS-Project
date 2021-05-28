package entity;

import java.io.Serializable;

import java.util.ArrayList;
@SuppressWarnings("serial")
public class Principal extends PersonCEMS implements Serializable {

	private ArrayList<Subject> subjectList;
	private ArrayList<Course> courseList;
	private ArrayList<CourseReport> courseReportList;
	private Report report;

	public Principal(String firstName, String lastName, String id, String email, String role) {
		super(firstName, lastName, id, email, role);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
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
	
	public ArrayList<CourseReport> getCoursereportList() {
		return courseReportList;
	}

	public void setCourseReportList(ArrayList<CourseReport> coursereportList) {
		this.courseReportList = coursereportList;
	}

}
