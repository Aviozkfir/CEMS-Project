package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TeachersExam implements Serializable{
private String eid;
private String name;
private String date;

public TeachersExam(String eid, String name, String date) {
	this.eid = eid;
	this.name = name;
	this.date = date;
}

public String getEid() {
	return eid;
}

public void setEid(String eid) {
	this.eid = eid;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}


}
