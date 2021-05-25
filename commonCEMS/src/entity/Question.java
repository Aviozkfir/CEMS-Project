package entity;

import java.util.Date;

public class Question {
	private String text;
	
	private String ansA;
	private String ansB;
	private String ansC;
	private String ansD;
	
	private int id;//5 digit number in the format:-subject(2)-question number(3)-
	private String author;//the name of the teacher that created the question
	private Date modified;//the date the question was modified or updated
	private int correctAnswar;
	public int getCorrectAnswar() {
		return correctAnswar;
	}
	public void setCorrectAnswar(int correctAnswar) {
		this.correctAnswar = correctAnswar;
	}
	
	public Question() { }
	public Question(String text, String ansA, String ansB, String ansC, String ansD) {
		this.text = text;
		this.ansA = ansA;
		this.ansB = ansB;
		this.ansC = ansC;
		this.ansD = ansD;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAnsA() {
		return ansA;
	}
	public void setAnsA(String ansA) {
		this.ansA = ansA;
	}
	public String getAnsB() {
		return ansB;
	}
	public void setAnsB(String ansB) {
		this.ansB = ansB;
	}
	public String getAnsC() {
		return ansC;
	}
	public void setAnsC(String ansC) {
		this.ansC = ansC;
	}
	public String getAnsD() {
		return ansD;
	}
	public void setAnsD(String ansD) {
		this.ansD = ansD;
	}
}
