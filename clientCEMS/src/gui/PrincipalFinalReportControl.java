package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import entity.Principal;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

public class PrincipalFinalReportControl extends PrincipalMainPageController implements Initializable {
	@FXML
	private Text Average;
	@FXML
	private Text Median;
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CategoryAxis xAxis;

	int group[] = new int[9];
	Principal principal = (Principal) guiControl.getUser();

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		if (Istype("Course")) {
			PrincipalReportCourseControl controller = (PrincipalReportCourseControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
		} else if (Istype("Teacher")) {
			PrincipalReportTeacherControl controller = (PrincipalReportTeacherControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_TEACHER_PAGE.path);
		} else if (Istype("Student")) {
			PrincipalReportStudentControl controller = (PrincipalReportStudentControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_STUDENT_PAGE.path);
		}
		else
		GUIControl.popUpError("System error back button");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Average.setText(principal.getReport().getAverage());
		Median.setText(principal.getReport().getMedian());
		SetHistogram();

	}

	@SuppressWarnings("unchecked")
	public void SetHistogram() {

		barChart.setCategoryGap(0);
		barChart.setBarGap(2);
		xAxis.setLabel("Grades");
		yAxis.setLabel("incidence");
		groupData();
		XYChart.Series series = new XYChart.Series<>();
		series.getData().add(new XYChart.Data("0-55", group[0]));
		series.getData().add(new XYChart.Data("55-65", group[1]));
		series.getData().add(new XYChart.Data("65-70", group[2]));
		series.getData().add(new XYChart.Data("70-75", group[3]));
		series.getData().add(new XYChart.Data("75-80", group[4]));
		series.getData().add(new XYChart.Data("80-85", group[5]));
		series.getData().add(new XYChart.Data("85-90", group[6]));
		series.getData().add(new XYChart.Data("90-95", group[7]));
		series.getData().add(new XYChart.Data("95-100", group[8]));
		barChart.getData().addAll(series);
		for (Node n : barChart.lookupAll(".default-color0.chart-bar")) { // set bars color to blue+css:Report.css
			n.setStyle("-fx-bar-fill: #1357BE;");
			yAxis.setTickLabelFormatter(new IntegerStringConverter()); // convert yaxis to integer from double.
			
		}

	}

	private void groupData() {

		for (int i = 0; i < 9; i++) {
			group[i] = 0;
		}
		// Map.Entry<String, Object> entry : map.entrySet()
		for (Entry<String, String> entry : principal.getReport().getReportData().entrySet()) {
			int grade = Integer.parseInt(entry.getKey());
			if (grade <= 55) {
				group[0]++;
			} else if (grade <= 65) {
				group[1]++;
			} else if (grade <= 70) {
				group[2]++;
			} else if (grade <= 75) {
				group[3]++;
			} else if (grade <= 80) {
				group[4]++;
			} else if (grade <= 85) {
				group[5]++;
			} else if (grade <= 90) {
				group[6]++;
			} else if (grade <= 95) {
				group[7]++;
			} else if (grade <= 100) {
				group[8]++;
			}
		}
	}

	public boolean Istype(String type) { // check report type: teacher,student,course.
		if (principal.getReportType().equals(type))
			return true;
		return false;
	}

	class IntegerStringConverter extends StringConverter<Number> { // convert yaxis from doubles to integers.
		public IntegerStringConverter() {
		}

		@Override
		public String toString(Number object) {
			if (object.intValue() != object.doubleValue())
				return "";
			return "" + (object.intValue());
		}

		@Override
		public Number fromString(String string) {
			Number val = Double.parseDouble(string);
			return val.intValue();
		}
	}
	
}
