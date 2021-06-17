package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

/**
 * controller that holds the final report.
 * @author onavioz,kfiravioz
 *
 */
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
	public BarChart<String, Number> barChart;
	@FXML
	public NumberAxis yAxis;
	@FXML
	public CategoryAxis xAxis;

	int yAxisGroup[] = new int[9];
	Teacher teacher = (Teacher) guiControl.getUser();
	String failedStudents;

	/**
	 * This method sending us into the previous screen.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		TeacherMainReportController controller = (TeacherMainReportController) guiControl
					.loadStage(ClientsConstants.Screens.TEACHER_MAIN_REPORT_PAGE.path);

	}

	/**
	 *initializing this screen, setting Averge, media, total student counting, years.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PageExplainTextset();
		Average.setText(teacher.getReport().getAverage());
		Median.setText(teacher.getReport().getMedian());
		SetHistogram(yAxisGroup,teacher.getReport().getReportData(),barChart,yAxis,xAxis);
		totalStudentsText.setText(String.valueOf(teacher.getReport().getReportData().size()));
		totalFailedText.setText(String.valueOf(yAxisGroup[0]));
		yearsText.setText(teacher.getReport().getYearRange());

	}

	/**
	 * This method setting the histogram.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void SetHistogram(int[] yAxisGroup,ArrayList<String> reportData,BarChart<String, Number> barChart, NumberAxis yAxis,CategoryAxis xAxis) {

		barChart.setCategoryGap(0);
		barChart.setBarGap(2);
		xAxis.setLabel("Grades");
		yAxis.setLabel("incidence");
		groupData(yAxisGroup,reportData);
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
	 * This method setting the data of the histogram.
	 */
	public void groupData(int yAxisGroup[],ArrayList<String> reportData) {

		for (int i = 0; i < 9; i++) {
			yAxisGroup[i] = 0;
		}
		// Map.Entry<String, Object> entry : map.entrySet()
		for (String currentgrade : reportData) {
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
