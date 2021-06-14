package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An entity of Teacher.
 * 
 * @author Kfir Avioz,On Avioz.
 * @implement Serializable.
 *
 */
@SuppressWarnings("serial")
public class Teacher extends PersonCEMS implements Serializable {
	/**
	 * arraylist of Common subjects for the teacher.
	 */
	private ArrayList<Subject> subjectList;
	/**
	 * arraylist of Common coruses for the teacher.
	 */
	private ArrayList<Course> courseList;
	/**
	 * instance of report.
	 */
	private Report report;
	/**
	 * arraylist of The exams that the tacher made.
	 */
	private ArrayList<TeachersExam> examList;

	/**
	 * teacher constructor.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param role
	 * 
	 */
	public Teacher(String firstName, String lastName, String id, String email, String role) {
		super(firstName, lastName, id, email, role);
	}

	/**
	 * getter for teacher id
	 * 
	 * @return String.
	 */
	public String getId() {
		return id;
	}

	/**
	 * setter for teacher id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * getter for subject list
	 * 
	 * @return ArrayList<Subject>.
	 */
	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	/**
	 * setter for subject list
	 * 
	 * @parm subjectList.
	 */
	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * getter for course list
	 * 
	 * @return ArrayList<Course>.
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * setter for course list
	 * 
	 * @param ArrayList<courseList>.
	 */

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	/**
	 * getter for report
	 * 
	 * @return Report
	 */
	public Report getReport() {
		return report;
	}

	/**
	 * setter for report
	 * 
	 * @param report
	 */
	public void setReport(Report report) {
		this.report = report;
	}

	/**
	 * getter for a list of exams that was made by this teacher.
	 * 
	 * @return ArrayList<TeachersExam>
	 */
	public ArrayList<TeachersExam> getExamList() {
		return examList;
	}

	/**
	 * setter for a list of exams that was made by this teacher.
	 * 
	 * @param examList
	 */
	public void setExamList(ArrayList<TeachersExam> examList) {
		this.examList = examList;
	}

}
