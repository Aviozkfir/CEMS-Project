package entity;

import java.io.Serializable;

/**
 * A class that holds the relevant information for solved question
 * @author Shalom and Omer
 *
 */
public class SolvedQuestionToView implements Serializable {
	/**
	 * Holds question ID
	 */
	private String Qid;
	/**
	 * Holds the chosen answer
	 */
	private int chosenAnswer;
	/**
	 * Holds notes relevant to student
	 */
	private String notesForStudent;
	/**
	 * Holds the content of question
	 */
	private String questionText;
	/**
	 * holds option 1
	 */
	private String ansA;
	/**
	 * holds option 2
	 */
	private String ansB;
	/**
	 * holds option 3
	 */
	private String ansC;
	/**
	 * holds option 4
	 */
	private String ansD;
	/**
	 * Holds the correct answer
	 */
	private int correctAns;
	/**
	 * Holds the question number
	 */
	private int questionNum;

	/**
	 * Constructor that holds SolvedQuestionToView given all fields
	 * @param qid
	 * @param chosenAnswer
	 * @param notesForStudent
	 * @param questionText
	 * @param ansA
	 * @param ansB
	 * @param ansC
	 * @param ansD
	 * @param correctAns
	 * @param questionNum
	 */
	public SolvedQuestionToView(String qid, int chosenAnswer, String notesForStudent, String questionText, String ansA,
			String ansB, String ansC, String ansD, int correctAns, int questionNum) {
		super();
		Qid = qid;
		this.chosenAnswer = chosenAnswer;
		this.notesForStudent = notesForStudent;
		this.questionText = questionText;
		this.ansA = ansA;
		this.ansB = ansB;
		this.ansC = ansC;
		this.ansD = ansD;
		this.correctAns = correctAns;
		this.questionNum = questionNum;
	}

	/**
	 * returns ID of question
	 * @return String
	 */
	public String getQid() {
		return Qid;
	}

	/**
	 * returns chosen answer
	 * @return int
	 */
	public int getChosenAnswer() {
		return chosenAnswer;
	}

	/**
	 * returns notes for student
	 * @return String
	 */
	public String getNotesForStudent() {
		return notesForStudent;
	}

	/**
	 * returns text of question
	 * @return String
	 */
	public String getQuestionText() {
		return questionText;
	}

	/**
	 * returns answer 1
	 * @return String
	 */
	public String getAnsA() {
		return ansA;
	}

	/**
	 * returns answer 2
	 * @return String
	 */
	public String getAnsB() {
		return ansB;
	}

	/**
	 * returns answer 3
	 * @return String
	 */
	public String getAnsC() {
		return ansC;
	}

	/**
	 * returns answer 4
	 * @return String
	 */
	public String getAnsD() {
		return ansD;
	}
	
	
	

	/**
	 * return correct answer
	 * @return int
	 */
	public int getCorrectAns() {
		return correctAns;
	}
	
	/**
	 * returns question number
	 * @return int
	 */
	public int getQuestionNum() {
		return questionNum;
	}
	public void setNotesForStudent(String notesForStudent) {
		this.notesForStudent = notesForStudent;
	}

	/**
	 *toString for SolvedQuestuonToView
	 */
	@Override
	public String toString() {
		return chosenAnswer+"";
	}

}
