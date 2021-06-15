package entity;

import java.io.Serializable;

/**
 * A class that holds relevant information to present to student.
 * 
 * @author Shalom and Omer
 *
 */
public class SolvedExamToView implements Serializable {
	String notesForExam;
	/**
	 * Holds the Submission exam ID
	 */
	String SEid;
	/**
	 * Holds the exam ID
	 */
	String eid;
	/**
	 * Holds finish Date
	 */
	String finishDate;
	/**
	 * Holds exam Name
	 */
	String examName;
	/**
	 * Holds grade for that exam
	 */
	String grade;

	/**
	 * constructor given all the fields
	 * 
	 * @param SEid
	 * @param eid
	 * @param finishDate
	 * @param examName
	 * @param grade
	 */
	public SolvedExamToView(String SEid, String eid, String finishDate, String examName, String grade,
			String notesForExam) {
		super();
		this.eid = eid;
		this.finishDate = finishDate;
		this.examName = examName;
		this.grade = grade;
		this.SEid = SEid;
		this.notesForExam = notesForExam;
	}

	/**
	 * returns Exam ID
	 * 
	 * @return String
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * returns finish date
	 * 
	 * @return String
	 */
	public String getFinishDate() {
		return finishDate;
	}

	/**
	 * returns exam name
	 * 
	 * @return String
	 */
	public String getExamName() {
		return examName;
	}

	/**
	 * sets grade for that exam
	 * 
	 * @return String
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * toString for SolvedExam
	 */
	@Override
	public String toString() {
		return "SolvedExamToView [eid=" + eid + ", finishDate=" + finishDate + ", examName=" + examName + ", grade="
				+ grade + "]";
	}

	/**
	 * returns submission exam ID
	 * 
	 * @return String
	 */
	public String getSEid() {
		return SEid;
	}

	public String getNotesForExam() {
		return notesForExam;
	}

}
