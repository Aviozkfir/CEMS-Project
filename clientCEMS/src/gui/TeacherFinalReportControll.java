package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import entity.Teacher;
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

public class TeacherFinalReportControll extends TeacherMainPageController implements Initializable {
	@FXML
	private Text Average;
	@FXML
	private Text Median;
	@FXML
	private Text totalStudentsText;  
	@FXML
	private Text totalFailedText;   
	@FXML
	private Text yearsText;			
	@FXML
	private Text PageExplainText;
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CategoryAxis xAxis;

	int yAxisGroup[] = new int[9];
	Teacher teacher = (Teacher) guiControl.getUser();
	String failedStudents;

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		TeacherMainReportController controller = (TeacherMainReportController) guiControl
					.loadStage(ClientsConstants.Screens.TEACHER_MAIN_REPORT_PAGE.path);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PageExplainTextset();
		Average.setText(teacher.getReport().getAverage());
		Median.setText(teacher.getReport().getMedian());
		SetHistogram();
		totalStudentsText.setText(String.valueOf(teacher.getReport().getReportData().size()));
		totalFailedText.setText(String.valueOf(yAxisGroup[0]));
		yearsText.setText(teacher.getReport().getYearRange());

	}

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

	private void groupData() {

		for (int i = 0; i < 9; i++) {
			yAxisGroup[i] = 0;
		}
		// Map.Entry<String, Object> entry : map.entrySet()
		for (String currentgrade : teacher.getReport().getReportData()) {
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

	public void PageExplainTextset() {
		PageExplainText.setText("Distribution of grades from exam: "+teacher.getReport().getSelected());//add exam id.

	}


}
