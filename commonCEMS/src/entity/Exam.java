package entity;

import java.io.Serializable;

public class Exam implements Serializable {
	String Eid;
	

	String Sid;
	String Cid;
	String Name;
	String Date;
	String Tdescription;
	String Sdescription;
	String ID;
	String TotalTime;
	String Code;
	String Mode;
	
	public Exam() {
		
	}
	
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

	public String getEid() {
		return Eid;
	}

	public String getSid() {
		return Sid;
	}

	public String getCid() {
		return Cid;
	}

	public String getName() {
		return Name;
	}

	public String getDate() {
		return Date;
	}

	public String getTdescription() {
		return Tdescription;
	}

	public String getSdescription() {
		return Sdescription;
	}

	public String getID() {
		return ID;
	}

	public String getTotalTime() {
		return TotalTime;
	}

	public String getCode() {
		return Code;
	}

	public String getMode() {
		return this.Mode;
	}

	public void setEid(String eid) {
		Eid = eid;
	}

	public void setSid(String sid) {
		Sid = sid;
	}

	public void setCid(String cid) {
		Cid = cid;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setDate(String date) {
		Date = date;
	}
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
	

	public void setTdescription(String tdescription) {
		Tdescription = tdescription;
	}

	public void setSdescription(String sdescription) {
		Sdescription = sdescription;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setTotalTime(String totalTime) {
		TotalTime = totalTime;
	}

	public void setCode(String code) {
		Code = code;
	}

	public void setMode(String mode) {
		Mode = mode;
	}
}
