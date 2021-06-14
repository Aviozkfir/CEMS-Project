package entity;

import java.io.Serializable;

/**
 * An entity of exam that the current teacher made.
 * 
 * @author Kfir Avioz,On Avioz.
 * @implement Serializable.
 *
 */
@SuppressWarnings("serial")
public class TeachersExam implements Serializable {
	/**
	 * exam id.
	 */
	private String eid;
	/**
	 * exam name.
	 */
	private String name;
	/**
	 * exam date
	 */
	private String date;

	/**
	 * TeacherExams constructor.
	 * 
	 * @param eid
	 * @param name
	 * @param date
	 */
	public TeachersExam(String eid, String name, String date) {
		this.eid = eid;
		this.name = name;
		this.date = date;
	}

	/**
	 * getter for exam id
	 * 
	 * @return String.
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * setter for exam id
	 * 
	 * @param eid.
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * getter for exam name
	 * 
	 * @return String.
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter for exam name
	 * 
	 * @param name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * getter for exam date
	 * 
	 * @return String.
	 */
	public String getDate() {
		return date;
	}

	/**
	 * setter for exam date
	 * 
	 * @param date.
	 */

	public void setDate(String date) {
		this.date = date;
	}

}
