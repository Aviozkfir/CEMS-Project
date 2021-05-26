package entity;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
	private String text;
	
	private String ansA;
	private String ansB;
	private String ansC;
	private String ansD;
	
	private int id;//5 digit number in the format:-subject(2)-question number(3)
	private String author;//the name of the teacher that created the question
	private String modified;//the date the question was modified or updated
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	private int correctAnswar;
	public int getCorrectAnswar() {
		return correctAnswar;
	}
	public void setCorrectAnswar(int correctAnswar) {
		this.correctAnswar = correctAnswar;
	}
	
	public Question() { }
	
	public Question(int id, String text, String ansA, String ansB, String ansC, String ansD, int correct, String author, String date) {
		this.text = text;
		this.ansA = ansA;
		this.ansB = ansB;
		this.ansC = ansC;
		this.ansD = ansD;
		this.id= id;
		this.correctAnswar=correct;
		this.author=author;
		this.setModified(date);
	}
	
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
