package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entity-report : for principal reports and teacher report.
 * 
 * @author On Avioz ,Kfir Avioz
 * @implements Serializable
 */
@SuppressWarnings("serial")
public class Report implements Serializable {

	/**
	 * arraylist of report data (grades).
	 */
	private ArrayList<String> report = new ArrayList<String>();
	/**
	 * string that hold median.
	 */
	private String Median;
	/**
	 * string that hold average.
	 */
	private String Average;
	/**
	 * string that hold year range in report.
	 */
	private String yearRange;
	/**
	 * string that hold the id selected.
	 */
	private String selected;

	/**
	 * report constructor. construct the array of grades (report)
	 * 
	 * @param report
	 */
	public Report(ArrayList<String> report) {
		this.report = report;
	}

	/**
	 * getter for report array (reportdata).
	 * 
	 * @return ArrayList<String>.
	 */
	public ArrayList<String> getReportData() {
		return report;
	}

	/**
	 * setter for report array (reportdata).
	 * 
	 * @param report
	 */
	public void setReportData(ArrayList<String> report) {
		this.report = report;
	}

	/**
	 * setter for median
	 * 
	 * @param median
	 */
	public void setMedian(String median) {
		Median = median;
	}

	/**
	 * setter for average
	 * 
	 * @param average
	 */
	public void setAverage(String average) {
		Average = average;
	}

	/**
	 * getter for median
	 * 
	 * @return String
	 */
	public String getMedian() {
		return Median;
	}

	/**
	 * getter for average
	 * 
	 * @return String
	 */
	public String getAverage() {
		return Average;
	}

	/**
	 * getter for the range of years.
	 * 
	 * @return String
	 */
	public String getYearRange() {
		return yearRange;
	}

	/**
	 * setter for the range of years.
	 * 
	 * @param yearRange
	 */
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	/**
	 * getter for the id selected.
	 * 
	 * @return String
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * setter for the id selected.
	 * 
	 * @param selected
	 */
	public void setSelected(String selected) {
		this.selected = selected;
	}

}
