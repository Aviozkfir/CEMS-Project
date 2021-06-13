package entity;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

/**
 * A class for manual exam.
 * 
 * @author Shalom and Omer
 *
 */
public class ManualExamFile implements Serializable {

	/**
	 * holds the manual exam file as byte array
	 */
	private byte[] exam;

	/**
	 * constructor for manual exam that gets byte array.
	 * @param byte[] exam 
	 */
	public ManualExamFile(byte[] exam) {
		super();
		this.exam = exam;
	}

	/**
	 * returns byte array of manual exam
	 * @return byte[] 
	 */
	public byte[] getExam() {
		return exam;
	}

}
