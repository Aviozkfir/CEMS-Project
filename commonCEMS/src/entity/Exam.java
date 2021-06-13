package entity;

import java.io.Serializable;

/**
 * @author Shalom and Omer
 * A class that keeps information about Exam entity.
 *
 */
public class Exam implements Serializable {
	/**
	 * Holds exam ID
	 */
	String Eid;
	/**
	 * Holds Subject ID
	 */
	String Sid;
	/**
	 * Holds course ID
	 */
	String Cid;
	/**
	 * Holds name of exam
	 */
	String Name;
	/**
	 * Holds Date of creation of exam
	 */
	String Date;
	/**
	 * Holds Teacher description
	 */
	String Tdescription;
	/**
	 * Holds Student description
	 */
	String Sdescription;
	/**
	 * Holds ID of exam
	 */
	String ID;
	/**
	 * Holds totalTime of exam
	 */
	String TotalTime;
	/**
	 * Holds the code for exam
	 */
	String Code;
	/**
	 * Holds Mode of exam (Manual, Computerized)
	 */
	String Mode;
	
	/**
	 * An empty constructor
	 */
	public Exam() {
		
	}
	
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
	 * Constructor for Exam given all the fields.
	 */
	public Exam(String eid, String sid, String cid, String name, String date, String tdescription, String sdescription,
			String iD, String totalTime, String code,String mode) {
		super();
		Eid = eid;
		Sid = sid;
		Cid = cid;
		Name = name;
		Date = date;
		Tdescription = tdescription;
		Sdescription = sdescription;
		ID = iD;
		TotalTime = totalTime;
		Code = code;
		Mode = mode;
	}

	/**
	 * @return String
	 * returns Exam ID
	 */
	public String getEid() {
		return Eid;
	}

	/**
	 * @return String
	 * returns Subject ID
	 */
	public String getSid() {
		return Sid;
	}

	/**
	 * @return String
	 * returns Course ID
	 */
	public String getCid() {
		return Cid;
	}

	/**
	 * @return String	
	 * returns name of exam
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @return String
	 * returns creation date of exam
	 */
	public String getDate() {
		return Date;
	}

	/**
	 * @return String
	 * returns Teacher Description for exam
	 */
	public String getTdescription() {
		return Tdescription;
	}

	/**
	 * @return String
	 * returns subject description for exam
	 */
	public String getSdescription() {
		return Sdescription;
	}

	/**
	 * @return String
	 * returns ID of exam
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return String
	 * returns total time of exam
	 */
	public String getTotalTime() {
		return TotalTime;
	}

	/**
	 * @return String
	 * returns code of exam
	 */
	public String getCode() {
		return Code;
	}

	/**
	 * @return Stirng
	 * returns mode of exam
	 */
	public String getMode() {
		return this.Mode;
	}

	/**
	 * @param String eid
	 * sets the exam ID
	 */
	public void setEid(String eid) {
		Eid = eid;
	}

	/**
	 * @param String sid
	 * returns the subject ID
	 */
	public void setSid(String sid) {
		Sid = sid;
	}

	/**
	 * @param String cid
	 * sets the course ID
	 */
	public void setCid(String cid) {
		Cid = cid;
	}

	/**
	 * @param String name
	 * sets the name of exam
	 */
	public void setName(String name) {
		Name = name;
	}

	/**
	 * @param String date
	 * sets the date for exam given the date
	 */
	public void setDate(String date) {
		Date = date;
	}
	
	/**
	 * sets the date of exam without given date
	 */
	public void setDate() {
		int day = java.time.LocalDate.now().getDayOfMonth();
    	int mounth =java.time.LocalDate.now().getMonthValue();
    	int year=java.time.LocalDate.now().getYear();
    	String s="";
    	s=s.concat(""+year+"-");
    	if(mounth<10)
    		s=s.concat("0");
    	s=s.concat(mounth+"-");
    	if(day<10)
    		s=s.concat("0");
    	s=s.concat(day+"");
    	
    	
    	//22-2-1012
		Date = s;
	}
	

	/**
	 * @param String tdescription
	 * sets teacher description for exam
	 */
	public void setTdescription(String tdescription) {
		Tdescription = tdescription;
	}

	/**
	 * @param String sdescription
	 * sets the student description
	 */
	public void setSdescription(String sdescription) {
		Sdescription = sdescription;
	}

	
	/**
	 * @param String iD
	 * sets the ID for exam
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @param String totalTime
	 * sets the total time for exam
	 */
	public void setTotalTime(String totalTime) {
		TotalTime = totalTime;
	}

	/**
	 * @param String code
	 * sets the code for exam
	 */
	public void setCode(String code) {
		Code = code;
	}

	/**
	 * @param String mode
	 * sets the mode for exam
	 */
	public void setMode(String mode) {
		Mode = mode;
	}
}
