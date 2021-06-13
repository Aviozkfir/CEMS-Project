package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An entity that holds information about certain course.
 * 
 * @author Shalom and Omer
 *
 */
@SuppressWarnings("serial")
public class Course implements Serializable {

	/**
	 * Holds name of course
	 */
	private String name;
	/**
	 * Holds ID of course
	 */
	private String id;
	/**
	 * Holds the subject the course belongs to
	 */
	private Subject subject;

	// private ArrayList<String> TeachersID = new ArrayList<String>(); // array list
	// that contains the teachers that teach
	// this field of subject.
	// private ArrayList<String> StudentsID = new ArrayList<String>(); // array list
	// that contains the students that study
	// this field of subject.

	/**
	 * Constructor for course given all the details
	 * 
	 * @param name
	 * @param id
	 * @param subject
	 */
	public Course(String name, String id, Subject subject) {
		this.name = name;
		this.id = id;
		this.subject = subject;
	}

	/**
	 * Returns name of course
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns ID of course
	 * @return String 
	 */
	public String getId() {
		return id;
	}

	/**
	 * returns subject of course
	 * @return Subject 
	 */
	public Subject getSubject() {
		return subject;
	}



	// public ArrayList<String> getTeachersID() {
	// return TeachersID;
//	}

	// public ArrayList<String> getStudentsID() {
	// return StudentsID;
	// }

}
