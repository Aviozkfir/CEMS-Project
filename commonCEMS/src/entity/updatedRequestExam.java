package entity;

import java.io.Serializable;

public class updatedRequestExam implements Serializable{

	private Exam exam;
	private String status;
	private String duration;
	public updatedRequestExam(Exam e, String status, String newDuration) {
		this.duration=newDuration;
		this.exam=e;
		this.status=status;
	}
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public String isStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
