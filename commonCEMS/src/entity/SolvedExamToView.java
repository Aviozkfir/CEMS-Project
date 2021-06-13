package entity;

import java.io.Serializable;

public class SolvedExamToView implements Serializable {
	String SEid;
	String eid;
	String finishDate;
	String examName;
	String grade;

	public SolvedExamToView(String SEid, String eid, String finishDate, String examName, String grade) {
		super();
		this.eid = eid;
		this.finishDate = finishDate;
		this.examName = examName;
		this.grade = grade;
		this.SEid = SEid;
	}

	public String getEid() {
		return eid;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public String getExamName() {
		return examName;
	}

	public String getGrade() {
		return grade;
	}

	@Override
	public String toString() {
		return "SolvedExamToView [eid=" + eid + ", finishDate=" + finishDate + ", examName=" + examName + ", grade="
				+ grade + "]";
	}

	public String getSEid() {
		return SEid;
	}

}
