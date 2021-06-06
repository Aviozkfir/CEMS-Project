package entity;

import java.util.ArrayList;

public class ExamForStudent extends Exam {
	ArrayList<QuestionInExam> examQuestions;
	

	public ExamForStudent(String eid, String sid, String cid, String name, String date, String tdescription,
			String sdescription, String iD, String totalTime, String code, String mode,
			ArrayList<QuestionInExam> questions) {
		super(eid, sid, cid, name, date, tdescription, sdescription, iD, totalTime, code, mode);
		this.examQuestions = questions;
	}

	public ArrayList<QuestionInExam> getExamQuestions() {
		return examQuestions;
	}



}
