package entity;

import java.io.Serializable;

public class QuestionInExam extends Question implements Serializable {
	private int chosenAnswer=-1;
	private int pointsQuestion;
	private int numOfQuestion;
	

	public QuestionInExam(String text, String ansA, String ansB, String ansC, String ansD, 
			int pointsQuestion, int numOfQuestion, String id, String author,
			String dateModified,int CorrectAnswer) {
		super(text, ansA, ansB, ansC, ansD, id, author, dateModified,CorrectAnswer);
		this.pointsQuestion = pointsQuestion;
		this.numOfQuestion = numOfQuestion;
		
	}
	public void setChosenAnswer(int chosenAnswer) {
		this.chosenAnswer=chosenAnswer;
	}

	public int getChosenAnswer() {
		return chosenAnswer;
	}

	public int getPointsQuestion() {
		return pointsQuestion;
	}

	public int getNumOfQuestion() {
		return numOfQuestion;
	}
	@Override
	public String toString() {
		return this.numOfQuestion+"";
	}

	
	

	
	


	
	

}
