package entity;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
	private String text;
	
	private String ansA;
	private String ansB;
	private String ansC;
	private String ansD;
	
	private String id;//5 digit number in the format:-subject(2)-question number(3)
	private String author;//the name of the teacher that created the question
	private String modified;//the date the question was modified or updated
	
	
	public String getSubject() {
		return id.substring(0, 2);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	
	public void setModified() {
		int day = java.time.LocalDate.now().getDayOfMonth();
    	int mounth =java.time.LocalDate.now().getMonthValue();
    	int year=java.time.LocalDate.now().getYear();
    	String s="";
    	if(day<10)
    		s=s.concat("0");
    	s=s.concat(day+"/");
    	
    	if(mounth<10)
    		s=s.concat("0");
    	s=s.concat(mounth+"/");
    	s=s.concat(""+year);
    	
    	modified=s;
	}
	
	private int correctAnswar;
	public int getCorrectAnswar() {
		return correctAnswar;
	}
	public void setCorrectAnswar(int correctAnswar) {
		this.correctAnswar = correctAnswar;
	}
	
	public Question() { }
	
	public Question(String id, String text, String ansA, String ansB, String ansC, String ansD, int correct, String author, String date) {
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
