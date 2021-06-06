package entity;

import java.io.Serializable;

public class SolvedExam extends Exam implements Serializable {

	String finishTime;
	String finishDate;
	String finalGrade;
	String Checked = "No";
	String Submitted = "";

	public SolvedExam(String eid, String sid, String cid, String name, String date, String tdescription,
			String sdescription, String iD, String totalTime, String code, String mode, String finishTime,
			String finishDate, String finalGrade) {
		super(eid, sid, cid, name, date, tdescription, sdescription, iD, totalTime, code, mode);

		this.finishTime = finishTime;
		this.finishDate = finishDate;
		this.finalGrade = finalGrade;
	}

	public void setSubmitted(String submitted) {
		this.Submitted = submitted;

	}

	public String getSubmitted() {
		return Submitted;

	}

	public String getFinishTime() {
		return finishTime;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public String getFinalGrade() {
		return finalGrade;
	}

}
