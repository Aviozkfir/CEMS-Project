package entity;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Report implements Serializable {

	private HashMap<String, String> report = new HashMap<String, String>(); // (grade,Date).
	private String Median;
	private String Average;
	private String yearRange;


	public Report(HashMap<String, String> report) {
		this.report = report;
	}

	public HashMap<String, String> getReportData() {
		return report;
	}

	public void setReportData(HashMap<String, String> report) {
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


}
