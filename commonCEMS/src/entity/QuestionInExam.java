package entity;

import java.io.Serializable;

/**
 * A class that keeps relevant information about questions that are in exams.
 * @author Shalom and Omer
 * @extend Question
 * 
 *
 */
public class QuestionInExam extends Question implements Serializable {
	/**
	 * Keeps the correct answer number. default: -1
	 */
	private int chosenAnswer=-1;
	/**
	 * Keeps that point for that question.
	 */
	private int pointsQuestion;
	/**
	 * keeps number of question in exam
	 */
	private int numOfQuestion;
	

	/**
	 * A constructor for QuestionInExam given all the fields of Question + pointsQuestion+ numOfQuestion
	 * @param text
	 * @param ansA
	 * @param ansB
	 * @param ansC
	 * @param ansD
	 * @param pointsQuestion
	 * @param numOfQuestion
	 * @param id
	 * @param author
	 * @param dateModified
	 * @param CorrectAnswer
	 */
	public QuestionInExam(String text, String ansA, String ansB, String ansC, String ansD, 
			int pointsQuestion, int numOfQuestion, String id, String author,
			String dateModified,int CorrectAnswer) {
		super(text, ansA, ansB, ansC, ansD, id, author, dateModified,CorrectAnswer);
		this.pointsQuestion = pointsQuestion;
		this.numOfQuestion = numOfQuestion;
		
	}
	/**
	 * An empty constructor
	 */
	public QuestionInExam() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Sets the chosen answer given the correct answer number
	 * @param int chosenAnswer
	 */
	public void setChosenAnswer(int chosenAnswer) {
		this.chosenAnswer=chosenAnswer;
	}

	/**
	 * returns the chosenAnswer for QuestionInExam
	 * @return int
	 */
	public int getChosenAnswer() {
		return chosenAnswer;
	}

	/**
	 * returns points of question
	 * @return int
	 */
	public int getPointsQuestion() {
		return pointsQuestion;
	}

	/**
	 * sets points for question
	 * @param int pointsQuestion
	 */
	public void setPointsQuestion(int pointsQuestion) {
		this.pointsQuestion = pointsQuestion;
	}
	/**
	 * sets question number
	 * @param int numOfQuestion
	 */
	public void setNumOfQuestion(int numOfQuestion) {
		this.numOfQuestion = numOfQuestion;
	}
	/**
	 * returns question number
	 * @return int
	 */
	public int getNumOfQuestion() {
		return numOfQuestion;
	}
	/**
	 *toString for QuestionInExam
	 */
	@Override
	public String toString() {
		return this.numOfQuestion+"";
	}

	
	

	
	


	
	

}
