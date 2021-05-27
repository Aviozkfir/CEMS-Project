package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseReport implements Serializable{
	private String id;
	private String name;

	public CourseReport(String id, String name) {
		this.id = id;
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return "Course [name=" + name + ", id=" + id;
	}
}
