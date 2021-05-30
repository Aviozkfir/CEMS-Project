package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

public class PrincipalFinalReportControl extends PrincipalMainPageController implements Initializable {
	@FXML
	private Text Average;
	@FXML
	private Text Median;
	@FXML
	private BarChart<String,Integer> barChart;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private CategoryAxis xAxis;

	int group[] = new int[10];
	Principal principal = (Principal) guiControl.getUser();

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankSubjectsController a = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
		a.setPrincipalSubject();
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
		barChart.setBarGap(0);
		xAxis.setLabel("Years");
		yAxis.setLabel("incidence");
		groupData();
		XYChart.Series series = new XYChart.Series<>();
		series.setName("Distribution");
		series.getData().add(new XYChart.Data("0-10", group[0]));
		series.getData().add(new XYChart.Data("10-20", group[1]));
		series.getData().add(new XYChart.Data("20-30", group[2]));
		series.getData().add(new XYChart.Data("30-40", group[3]));
		series.getData().add(new XYChart.Data("40-50", group[4]));
		series.getData().add(new XYChart.Data("50-60", group[5]));
		series.getData().add(new XYChart.Data("60-70", group[6]));
		series.getData().add(new XYChart.Data("70-80", group[7]));
		series.getData().add(new XYChart.Data("80-90", group[8]));
		series.getData().add(new XYChart.Data("90-100", group[9]));
		barChart.getData().addAll(series);
		
	}

	private void groupData() {

		for (int i = 0; i < 10; i++) {
			group[i] = 0;
		}
		// Map.Entry<String, Object> entry : map.entrySet()
		for (Entry<String, String> entry : principal.getReport().getReportData().entrySet()) {
			int grade = Integer.parseInt(entry.getKey());
			if (grade <= 10) {
				group[0]++;
			} else if (grade <= 20) {
				group[1]++;
			} else if (grade <= 30) {
				group[2]++;
			} else if (grade <= 40) {
				group[3]++;
			} else if (grade <= 50) {
				group[4]++;
			} else if (grade <= 60) {
				group[5]++;
			} else if (grade <= 70) {
				group[6]++;
			} else if (grade <= 80) {
				group[7]++;
			} else if (grade <= 90) {
				group[8]++;
			} else if (grade <= 100) {
				group[9]++;
			}
		}
	}
	

}
