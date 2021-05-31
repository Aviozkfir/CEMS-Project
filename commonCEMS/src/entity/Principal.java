package entity;

import java.io.Serializable;

import java.util.ArrayList;
@SuppressWarnings("serial")
public class Principal extends PersonCEMS implements Serializable {

	private ArrayList<Subject> subjectList;
	private ArrayList<Course> courseList;
	private ArrayList<Teacher> teacherList;
	private ArrayList<Student> studentList;
	private ArrayList<Request> requestList;
	private Report report;
	private String ReportType;

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
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
	
	public ArrayList<Teacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(ArrayList<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	public String getReportType() {
		return ReportType;
	}

	public void setReportType(String reportType) {
		ReportType = reportType;
	}
	
	public ArrayList<Request> getRequestList() {
		return requestList;
	}

	public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}
	


}
