package entity;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;

public class ManualExamFile implements Serializable {
	
	 private byte[] exam;

	public ManualExamFile(byte[] exam) {
		super();
		this.exam = exam;
	}

	public byte[] getExam() {
		return exam;
	}
	
	 
	 
	

}
