package entity;

import java.io.Serializable;

/**
 * A class that holds information about exam that was solved by student
 * @author Shalom and Omer
 *
 */
public class SolvedExam extends Exam implements Serializable {

	/**
	 * Holds the finish time in minutes for that SolvedExam
	 */
	String finishTime;
	/**
	 * holds the finoish date for that SolvedExam
	 */
	String finishDate;
	/**
	 * holds the final grade for that SolvedExam
	 */
	String finalGrade;
	/**
	 * Indicated whether the exam was checked or no, default: No
	 */
	String Checked = "No";
	/**
	 * Indicates if exam was submitted 
	 */
	String Submitted = "";

	/**
	 * Holds the student for that exam
	 */
	String student;
	/**
	 * returns the student for that exam
	 * @return String
	 */
	public String getStudent() {
		return student;
	}

	/**
	 * sets student for that exam
	 * @param String student
	 */
	public void setStudent(String student) {
		this.student = student;
	}

	/**
	 * Constructor given all the fields of exam + finishTime+finishDate+finalGrade
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
	 * @param finishTime
	 * @param finishDate
	 * @param finalGrade
	 */
	public SolvedExam(String eid, String sid, String cid, String name, String date, String tdescription,
			String sdescription, String iD, String totalTime, String code, String mode, String finishTime,
			String finishDate, String finalGrade) {
		super(eid, sid, cid, name, date, tdescription, sdescription, iD, totalTime, code, mode);

		this.finishTime = finishTime;
		this.finishDate = finishDate;
		this.finalGrade = finalGrade;
	}

	/**
	 * sets the submitted field
	 * @param String submitted
	 */
	public void setSubmitted(String submitted) {
		this.Submitted = submitted;

	}

	/**
	 * returns the submitted field
	 * @return String
	 */
	public String getSubmitted() {
		return Submitted;

	}

	/**
	 * returns the time taken to solve the exam
	 * @return String
	 */
	public String getFinishTime() {
		return finishTime;
	}

	/**
	 * returns date when the exam was finished
	 * @return String
	 */
	public String getFinishDate() {
		return finishDate;
	}

	/**
	 * returns final grade of that solved exam
	 * @return String
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

}
