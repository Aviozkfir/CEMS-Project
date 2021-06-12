package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Report implements Serializable {

	//private HashMap<String, String> report = new HashMap<String, String>(); // (grade,Date).
	private ArrayList<String> report = new ArrayList<String>();
	private String Median;
	private String Average;
	private String yearRange;
	private String selected;


	public Report(ArrayList<String> report) {
		this.report = report;
	}

	public ArrayList<String> getReportData() {
		return report;
	}

	public void setReportData(ArrayList<String> report) {
		this.report = report;
	}

	public void setMedian(String median) {
		Median = median;
	}

	public void setAverage(String average) {
		Average = average;
	}

	public String getMedian() {
		return Median;
	}

	public String getAverage() {
		return Average;
	}

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}


}
