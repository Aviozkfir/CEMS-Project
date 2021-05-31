package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Request implements Serializable {

	private String num;
	private String title;
	private String currentDuration;
	private String newDuration;
	private String teacherID;
	private boolean cBox = false;

	public Request(String num, String title, String currentDuration, String newDuration, String teacherID) {
		this.num = num;
		this.title = title;
		this.currentDuration = currentDuration;
		this.newDuration = newDuration;
		this.teacherID = teacherID;
	}

	public String getNum() {
		return num;
	}

	public String getTitle() {
		return title;
	}

	public String getCurrentDuration() {
		return currentDuration;
	}

	public String getNewDuration() {
		return newDuration;
	}
	
	public String getTeacherID() {
		return teacherID;
	}
	
	public boolean getcBox() {
		return cBox;
	}
	
	public void setcBox(boolean cBox) {
		this.cBox = cBox;
	}

}
