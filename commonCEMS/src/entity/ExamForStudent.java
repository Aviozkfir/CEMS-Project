package entity;

import java.util.ArrayList;

/**
 * @author Shalom and Omer
 * Holds the information necceary for active computerized exam.
 *
 */
public class ExamForStudent extends Exam {
	/**
	 * Holds the questions of computerized exam
	 */
	ArrayList<QuestionInExam> examQuestions;
	

	/**
	 * @param eid
	 * @param sid
	 * @param cid
	 * @param name
	 * @param date
	 * @param tdescription
	 * @param sdescription
	 * @param iD
	 * @param totalTime
	 * @param code
	 * @param mode
	 * @param questions
	 * Constructor given all the details of Exam + questions.
	 */
	public ExamForStudent(String eid, String sid, String cid, String name, String date, String tdescription,
			String sdescription, String iD, String totalTime, String code, String mode,
			ArrayList<QuestionInExam> questions) {
		super(eid, sid, cid, name, date, tdescription, sdescription, iD, totalTime, code, mode);
		this.examQuestions = questions;
	}

	/**
	 * @return ArrayList<QuestionInExam>
	 * returns questions of exam
	 */
	public ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}



}
