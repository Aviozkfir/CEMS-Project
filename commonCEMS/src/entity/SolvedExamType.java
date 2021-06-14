package entity;

import java.io.Serializable;

public class SolvedExamType implements Serializable {

	/**
	 * Entity of solve exams type
	 * @author guy,Sharon
	 * @implement Serializable
	 */
	private String Eid;
	
	private Course course;
	
	private int total;

	private int checked;

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	private String Name;
	private String Date;
	
	
	public SolvedExamType(String Eid, String Date, String Name, int checked, int total) {
		this.Eid=Eid;
		this.Date=Date;
		this.Name=Name;
		this.checked=checked;
		this.total=total;
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
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}
}
