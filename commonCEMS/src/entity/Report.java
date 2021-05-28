package entity;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Report implements Serializable {

	private HashMap<String, String> report = new HashMap<String, String>(); // (grade,Date).
	private int Median;
	private int Average;

	public Report(HashMap<String, String> report) {
		this.report = report;
	}

	public HashMap<String, String> getReportData() {
		return report;
	}

	public void setReportData(HashMap<String, String> report) {
		this.report = report;
	}

	public void setMedian(int median) {
		Median = median;
	}

	public void setAverage(int average) {
		Average = average;
	}

	public int getMedian() {
		return Median;
	}

	public int getAverage() {
		return Average;
	}
}
