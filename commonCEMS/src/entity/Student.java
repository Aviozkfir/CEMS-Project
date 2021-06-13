package entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A class that holds relevant information about Student.
 * @author Shalom and Omer
 *
 */
public class Student extends PersonCEMS implements Serializable {

	//private ArrayList<SolvedExam> examList = new ArrayList<SolvedExam>(); // need to create solved exam class and set/get
	/**
	 * Subject list of Student
	 */
	private ArrayList<Subject> subjectList = new ArrayList<Subject>();
	/**
	 * course list of student
	 */
	private ArrayList<Course> courseList = new ArrayList<Course>(); 
	
	/**
	 * Constructor of student given all the fields of PersonCEMS
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param role
	 */
	public Student(String firstName, String lastName,String id, String email, String role) {
		super(firstName, lastName,id, email, role);
	}

	/**
	 * returns subject list of student
	 * @return ArrayList<Subject> 
	 */
	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}

	/**
	 * sets subject list of student
	 * @param ArrayList<Subject> subjectList
	 */
	public void setSubjectList(ArrayList<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	/**
	 * returns course list of student
	 * @return ArrayList<Course>
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * sets course list of student
	 * @param ArrayList<Course> courseList
	 */
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}



}
