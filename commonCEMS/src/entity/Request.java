package entity;

import java.io.Serializable;

/**
 * An entity for requests of exam's time extending that principal gets.
 * 
 * @author Kfir Avioz,On Avioz.
 * @implement Serializable.
 *
 */
@SuppressWarnings("serial")
public class Request implements Serializable {
	/**
	 * String that holds request number.
	 */
	private String num;
	/**
	 * String that holds request title.
	 */
	private String title;
	/**
	 * String that holds exam's current duration
	 */
	private String currentDuration;
	/**
	 * String that holds exam's requested duration (new)
	 */
	private String newDuration;
	/**
	 * String that holds teacher's id.
	 */
	private String teacherID;
	/**
	 * boolean checkbox that defines chosen request.
	 */
	private boolean cBox = false;

	/**
	 * request constructor.
	 * 
	 * @param num
	 * @param title
	 * @param currentDuration
	 * @param newDuration
	 * @param teacherID
	 * @return Request
	 * 
	 */
	public Request(String num, String title, String currentDuration, String newDuration, String teacherID) {
		this.num = num;
		this.title = title;
		this.currentDuration = currentDuration;
		this.newDuration = newDuration;
		this.teacherID = teacherID;
	}

	/**
	 * getter for request num.
	 * 
	 * @return String
	 */
	public String getNum() {
		return num;
	}

	/**
	 * getter for request title.
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * getter for request current duration.
	 * 
	 * @return String
	 */
	public String getCurrentDuration() {
		return currentDuration;
	}

	/**
	 * getter for request new duration.
	 * 
	 * @return String
	 */
	public String getNewDuration() {
		return newDuration;
	}

	/**
	 * getter for id of the teacher that sent the request.
	 * 
	 * @return String
	 */
	public String getTeacherID() {
		return teacherID;
	}

	/**
	 * getter for combo box status (bolean).
	 * 
	 * @return boolean
	 */
	public boolean getcBox() {
		return cBox;
	}

	/**
	 * setter for combo box status (bolean)
	 * 
	 * @param cbox
	 */
	public void setcBox(boolean cBox) {
		this.cBox = cBox;
	}

}
