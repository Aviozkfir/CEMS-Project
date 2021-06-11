package entity;

import java.io.Serializable;

public class SolvedExamType implements Serializable {


	private String Eid;
	
	private Course course;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	private String Name;
	private String Date;
	private String status;
	
	public SolvedExamType(String Eid, String Date, String Name, String status) {
		this.Eid=Eid;
		this.Date=Date;
		this.Name=Name;
		this.status=status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEid() {
		return Eid;
	}

	public void setEid(String eid) {
		Eid = eid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}
}
