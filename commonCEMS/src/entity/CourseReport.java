package entity;

import java.io.Serializable;

/**
 * An entity for report of type:course.
 * 
 * @author Kfir Avioz,On Avioz.
 * @implement Serializable.
 *
 */
@SuppressWarnings("serial")
public class CourseReport implements Serializable {
	/**
	 * course id
	 */
	private String id;
	/**
	 * course name
	 */
	private String name;

	/**
	 * Constuctor.
	 * @param id
	 * @param name.
	 * @return CourseReport
	 */
	public CourseReport(String id, String name) {
		this.id = id;
		this.name = name;

	}

	/**
	 * Course name getter
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Course id getter
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * sets course info on string.
	 * 
	 * @return String
	 */
	@Override
	public String toString() {
		return "Course [name=" + name + ", id=" + id;
	}
}
