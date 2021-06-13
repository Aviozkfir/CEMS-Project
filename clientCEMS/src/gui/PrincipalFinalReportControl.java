package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

/**
 * * Controller for the principal report that presents the report by the options
 * the princial chose.
 * 
 * @author On Avioz,Kfir Avioz.
 * @extend PrincipalMainPageController
 * @implements Initializable interface.
 */
public class PrincipalFinalReportControl extends PrincipalMainPageController implements Initializable {
	/**
	 * text that presents Average.
	 */
	@FXML
	private Text Average;
	/**
	 * text that presents Median.
	 */
	@FXML
	private Text Median;
	/**
	 * text that presents the number of the students in the report.
	 */
	@FXML
	private Text totalStudentsText;
	/**
	 * text that presents the number of the students that failed in the report.
	 */
	@FXML
	private Text totalFailedText;
	/**
	 * text that presents the year range in the report.
	 */
	@FXML
	private Text yearsText;
	/**
	 * text that presents the report explanations.
	 */
	@FXML
	private Text PageExplainText;
	/**
	 * Histogram barchart.
	 */
	@FXML
	private BarChart<String, Integer> barChart;
	/**
	 * Histogram's Y axis.
	 */
	@FXML
	private NumberAxis yAxis;
	/**
	 * Histogram's X axis.
	 */
	@FXML
	private CategoryAxis xAxis;
	/**
	 * array of int that counts instances in each bin.
	 */
	int yAxisGroup[] = new int[9];
	/**
	 * principal instance.
	 */
	Principal principal = (Principal) guiControl.getUser();

	/**
	 * The user can get back to the previous page, by the type of report he chose.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		if (Istype("Course")) {
			PrincipalReportCourseControl controller = (PrincipalReportCourseControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
			controller.setRequestCounter();
		} else if (Istype("Teacher")) {
			PrincipalReportTeacherControl controller = (PrincipalReportTeacherControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_TEACHER_PAGE.path);
			controller.setRequestCounter();
		} else if (Istype("Student")) {
			PrincipalReportStudentControl controller = (PrincipalReportStudentControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_STUDENT_PAGE.path);
			controller.setRequestCounter();
		}

	}

	/**
	 * Shows the report details(histogram,average,median,etc..) immidiatly when the
	 * user gets into the final report fxml.
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PageExplainTextset();
		Average.setText(principal.getReport().getAverage());
		Median.setText(principal.getReport().getMedian());
		SetHistogram();
		totalStudentsText.setText(String.valueOf(principal.getReport().getReportData().size()));
		totalFailedText.setText(String.valueOf(yAxisGroup[0]));
		yearsText.setText(principal.getReport().getYearRange());

	}

	/**
	 * This method is to set the histogram when x's axis index =10. the method
	 * counts every instance in each bin and sets the histogram result.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void SetHistogram() {

		barChart.setCategoryGap(0);
		barChart.setBarGap(2);
		xAxis.setLabel("Grades");
		yAxis.setLabel("incidence");
		groupData();
		XYChart.Series series = new XYChart.Series<>();
		series.getData().add(new XYChart.Data("0-55", yAxisGroup[0]));
		series.getData().add(new XYChart.Data("55-65", yAxisGroup[1]));
		series.getData().add(new XYChart.Data("65-70", yAxisGroup[2]));
		series.getData().add(new XYChart.Data("70-75", yAxisGroup[3]));
		series.getData().add(new XYChart.Data("75-80", yAxisGroup[4]));
		series.getData().add(new XYChart.Data("80-85", yAxisGroup[5]));
		series.getData().add(new XYChart.Data("85-90", yAxisGroup[6]));
		series.getData().add(new XYChart.Data("90-95", yAxisGroup[7]));
		series.getData().add(new XYChart.Data("95-100", yAxisGroup[8]));
		barChart.getData().addAll(series);
		for (Node n : barChart.lookupAll(".default-color0.chart-bar")) { // set bars color to blue+css:Report.css
			n.setStyle("-fx-bar-fill: #1357BE;");
			yAxis.setTickLabelFormatter(new IntegerStringConverter()); // convert yaxis to integer from double.

		}

	}

	/**
	 * groupData counts each instance in the histogram's bins.
	 */
	private void groupData() {

		for (int i = 0; i < 9; i++) {
			yAxisGroup[i] = 0;
		}
		for (String currentgrade : principal.getReport().getReportData()) {
			int grade = Integer.parseInt(currentgrade);
			if (grade <= 55) {
				yAxisGroup[0]++;
			} else if (grade <= 65) {
				yAxisGroup[1]++;
			} else if (grade <= 70) {
				yAxisGroup[2]++;
			} else if (grade <= 75) {
				yAxisGroup[3]++;
			} else if (grade <= 80) {
				yAxisGroup[4]++;
			} else if (grade <= 85) {
				yAxisGroup[5]++;
			} else if (grade <= 90) {
				yAxisGroup[6]++;
			} else if (grade <= 95) {
				yAxisGroup[7]++;
			} else if (grade <= 100) {
				yAxisGroup[8]++;
			}
		}
	}

	/**
	 * IsType returns the type of report : course,student,teacher.
	 * 
	 * @param type
	 */
	public boolean Istype(String type) {
		if (principal.getReportType().equals(type))
			return true;
		return false;
	}

	/**
	 * Class to fix scenebuilder: yaxis shows integers instead of doubls.
	 * 
	 * @author On Avioz,Kfir Avioz.
	 * @extend StringConverter<Number>.
	 */
	class IntegerStringConverter extends StringConverter<Number> {
		/**
		 * IntegerStringConverter empty constructor.
		 */
		public IntegerStringConverter() {
		}

		/**
		 * converts an integer to string.
		 * 
		 * @param object
		 */
		@Override
		public String toString(Number object) {
			if (object.intValue() != object.doubleValue())
				return "";
			return "" + (object.intValue());
		}

		/**
		 * converts a string to integer
		 * 
		 * @param object
		 */
		@Override
		public Number fromString(String string) {
			Number val = Double.parseDouble(string);
			return val.intValue();
		}
	}

	/**
	 * PageExplainTextset sets text explanation for gui.
	 * 
	 */
	public void PageExplainTextset() {
		PageExplainText.setText("Distribution of grades- exams of chosen " + principal.getReportType() + " with id: "
				+ principal.getReport().getSelected());

	}

}
