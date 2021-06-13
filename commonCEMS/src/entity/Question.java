package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  A class that holds information about question.
 * @author Shalom and Omer
 *
 */
public class Question implements Serializable {
	/**
	 * Holds the question text
	 */
	private String text;
	
	/**
	 * Holds text of option 1
	 */
	private String ans1;
	/**
	 * Holds text of option 2
	 */
	private String ans2;
	/**
	 * Holds text of option 3
	 */
	private String ans3;
	/**
	 * Holds text of option 4
	 */
	private String ans4;
	/**
	 * Holds the correct answer
	 */
	private int CorrectAns;
	
	private String id;//5 digit number in the format:-subject(2)-question number(3)
	private String author;//the name of the teacher that created the question
	private String dateModified;//the date the question was modified or updated
	
	
	/**
	 * returns subject number as string
	 * @return String
	 */
	public String getSubject() {
		return id.substring(0, 2);
	}
	/**
	 * returns ID of question
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * sets id of question
	 * @param String id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * returns Author of the qeustion
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * Sets the author of question.
	 * @param String author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * returns the date the question was modified
	 * @return String
	 */
	public String getModified() {
		return dateModified;
	}
	
	/**
	 * Sets the date the question was modified given the date.
	 * @param String modified
	 */
	public void setModified(String modified) {
		this.dateModified = modified;
	}
	
	/**
	 * Sets the date of today the question was modified
	 */
	public void setModified() {
		int day = java.time.LocalDate.now().getDayOfMonth();
    	int mounth =java.time.LocalDate.now().getMonthValue();
    	int year=java.time.LocalDate.now().getYear();
    	String s="";
    	s=s.concat(""+year+"-");
    	if(mounth<10)
    		s=s.concat("0");
    	s=s.concat(mounth+"-");
    	if(day<10)
    		s=s.concat("0");
    	s=s.concat(day+"");
    	
    	
    	//22-2-1012
		
    	dateModified=s;
	}
	
	
	/**
	 * An empty constructor for question
	 */
	public Question() { }
	
	/**
	 * Constructor for question given all the fields.
	 * @param text
	 * @param ansA
	 * @param ansB
	 * @param ansC
	 * @param ansD
	 * @param id
	 * @param author
	 * @param dateModified
	 * @param CorrectAnswer
	 */
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
	/**
	 * sets the correct answer given the number of correct answer
	 * @param int correctAnswer
	 * 
	 */
	public void setCorrectAnswer(int correctAnswer) {
		this.CorrectAns = correctAnswer;
	}
	
	/**
	 * constructor for question given the text and all the answers
	 * @param text
	 * @param ansA
	 * @param ansB
	 * @param ansC
	 * @param ansD
	 *
	 */
	public Question(String text, String ansA, String ansB, String ansC, String ansD) {
		this.text = text;
		this.ans1 = ansA;
		this.ans2 = ansB;
		this.ans3 = ansC;
		this.ans4 = ansD;
	
	}
	/**
	 * returns text of question
	 * @return String
	 */
	public String getText() {
		return text;
	}
	/**
	 * sets the text for question
	 * @param String text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * returns first answer
	 * @return String
	 */
	public String getAnsA() {
		return ans1;
	}
	/**
	 * sets first answer
	 * @param String ansA
	 */
	public void setAnsA(String ansA) {
		this.ans1 = ansA;
	}
	/**
	 * returns second answer
	 * @return String
	 */
	public String getAnsB() {
		return ans2;
	}
	/**
	 * sets the second answer
	 * @param String ansB
	 */
	public void setAnsB(String ansB) {
		this.ans2 = ansB;
	}
	/**
	 * returns the third answer
	 * @return String 
	 */
	public String getAnsC() {
		return ans3;
	}
	/**
	 * Sets the third answer
	 * @param String ansC
	 */
	public void setAnsC(String ansC) {
		this.ans3 = ansC;
	}
	/**
	 * returns the fourth answer
	 * @return String
	 */
	public String getAnsD() {
		return ans4;
	}
	/**
	 * sets the fourth answer
	 * @param ansD
	 */
	public void setAnsD(String ansD) {
		this.ans4 = ansD;
	}
	/**
	 * returns the correct answer
	 * @return int
	 */
	public int getCorrectAnswer() {
		return CorrectAns;
	}
}
