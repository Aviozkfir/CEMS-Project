package entity;

import java.io.Serializable;

public class SolvedQuestionToView implements Serializable {
	private String Qid;
	private int chosenAnswer;
	private String notesForStudent;
	private String questionText;
	private String ansA;
	private String ansB;
	private String ansC;
	private String ansD;
	private int correctAns;
	private int questionNum;

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

	public String getQid() {
		return Qid;
	}

	public int getChosenAnswer() {
		return chosenAnswer;
	}

	public String getNotesForStudent() {
		return notesForStudent;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getAnsA() {
		return ansA;
	}

	public String getAnsB() {
		return ansB;
	}

	public String getAnsC() {
		return ansC;
	}

	public String getAnsD() {
		return ansD;
	}

	public int getCorrectAns() {
		return correctAns;
	}
	
	public int getQuestionNum() {
		return questionNum;
	}

	@Override
	public String toString() {
		return chosenAnswer+"";
	}

}
