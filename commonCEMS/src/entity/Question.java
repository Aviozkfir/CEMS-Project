package entity;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {
	private String text;
	
	private String ans1;
	private String ans2;
	private String ans3;
	private String ans4;
	private int CorrectAns;
	
	private String id;//5 digit number in the format:-subject(2)-question number(3)
	private String author;//the name of the teacher that created the question
	private String dateModified;//the date the question was modified or updated
	
	
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
		return dateModified;
	}
	public void setModified(String modified) {
		this.dateModified = modified;
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
    	
    	dateModified=s;
	}
	
	
	public Question() { }
	
	public Question(String text, String ansA, String ansB, String ansC,
			String ansD, 
			String id, String author,
			String dateModified,int CorrectAnswer) {
		this.text = text;
		this.ans1 = ansA;
		this.ans2 = ansB;
		this.ans3 = ansC;
		this.ans4 = ansD;
		this.id = id;
		this.author = author;
		this.dateModified = dateModified;
		this.CorrectAns=CorrectAnswer;
		this.setModified(dateModified);
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.CorrectAns = correctAnswer;
	}
	
	public Question(String text, String ansA, String ansB, String ansC, String ansD) {
		this.text = text;
		this.ans1 = ansA;
		this.ans2 = ansB;
		this.ans3 = ansC;
		this.ans4 = ansD;
	
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAnsA() {
		return ans1;
	}
	public void setAnsA(String ansA) {
		this.ans1 = ansA;
	}
	public String getAnsB() {
		return ans2;
	}
	public void setAnsB(String ansB) {
		this.ans2 = ansB;
	}
	public String getAnsC() {
		return ans3;
	}
	public void setAnsC(String ansC) {
		this.ans3 = ansC;
	}
	public String getAnsD() {
		return ans4;
	}
	public void setAnsD(String ansD) {
		this.ans4 = ansD;
	}
	public int getCorrectAnswer() {
		return CorrectAns;
	}
}
