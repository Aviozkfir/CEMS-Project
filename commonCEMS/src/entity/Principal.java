package entity;

import java.io.Serializable;

/**
 * Entity-Principal
 * @author On Avioz ,Kfir Avioz.
 * @implements Serializable.
 */

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Principal extends PersonCEMS implements Serializable {

	/**
	 * arraylist of all subjects in db.
	 */
	private ArrayList<Subject> subjectList;
	/**
	 * arraylist of all courses in db.
	 */
	private ArrayList<Course> courseList;
	/**
	 * arraylist of all teachers in db.
	 */
	private ArrayList<Teacher> teacherList;
	/**
	 * arraylist of all students in db.
	 */
	private ArrayList<Student> studentList;
	/**
	 * arraylist of requests for adding time.
	 */
	private ArrayList<Request> requestList;
	/**
	 * instance of report
	 */
	private Report report;
	/**
	 * String that holds the type of a report for the principal.
	 */
	private String ReportType;

	/**
	 * principal constructor.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param role
	 * 
	 */
	public Principal(String firstName, String lastName, String id, String email, String role) {
		super(firstName, lastName, id, email, role);
	}

	/**
	 * getter for principal id
	 * 
	 * @return String.
	 */
	public String getId() {
		return id;
	}

	/**
	 * setter for principal id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * getter for principal report
	 * 
	 * @return Report.
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * setter for report.
	 * 
	 * @param report
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * getter for principal's subject list.
	 * 
	 * @return ArrayList<Subject>.
	 */
	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	/**
	 * setter for subject list
	 * 
	 * @param subjectList
	 */
	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * getter for principal's course list.
	 * 
	 * @return ArrayList<Course>
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * setter for course list
	 * 
	 * @param courseList
	 */
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	/**
	 * getter for principal's student list.
	 * 
	 * @return ArrayList<Student>
	 */
	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	/**
	 * setter for student list
	 * 
	 * @param studentList
	 */
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	/**
	 * getter for principal's teacher list.
	 * 
	 * @return ArrayList<Teacher>
	 */
	public ArrayList<Teacher> getTeacherList() {
		return teacherList;
	}

	/**
	 * setter for teacher list
	 * 
	 * @param teacherList
	 */
	public void setTeacherList(ArrayList<Teacher> teacherList) {
		this.teacherList = teacherList;
	}

	/**
	 * getter for principal's report type.
	 * 
	 * @return String
	 */
	public String getReportType() {
		return ReportType;
	}

	/**
	 * setter for the type of report.
	 * 
	 * @param reportType
	 */
	public void setReportType(String reportType) {
		ReportType = reportType;
	}

	/**
	 * getter for principal's requests list.
	 * 
	 * @return ArrayList<Request>
	 */
	public ArrayList<Request> getRequestList() {
		return requestList;
	}

	/**
	 * setter for request list.
	 * 
	 * @param requestList
	 */
	public void setRequestList(ArrayList<Request> requestList) {
		this.requestList = requestList;
	}

}
