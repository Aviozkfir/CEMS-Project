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

	public Exam(String eid, String sid, String cid, String name, String date, String tdescription, String sdescription,
			String iD, String totalTime, String code) {
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

}
