import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.Teacher;
import gui.TeacherFinalReportControll;
import javafx.embed.swing.JFXPanel;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class TeacherFinalReportControllTest {

	Teacher teacher;
	ArrayList<String> gradeslist;
	BarChart<String, Number> barChart;
	NumberAxis yAxis;
	CategoryAxis xAxis;
	int yAxisGroupExpected[];
	int yAxisGroupActual[];
	TeacherFinalReportControll teacherFinalReportControll;
	JFXPanel panel = new JFXPanel();

	@Before
	public void setUp() throws Exception {

		teacherFinalReportControll = new TeacherFinalReportControll();
		yAxisGroupExpected = new int[9];
		yAxisGroupActual = new int[9];
		gradeslist = new ArrayList<String>();
		yAxis = new NumberAxis();
		xAxis = new CategoryAxis();
		barChart = new BarChart<String, Number>(xAxis, yAxis);
	}

	@Test
	public void TestGroupDataSetsRightGroups() {
		gradeslist.add("54");
		gradeslist.add("60");
		gradeslist.add("66");
		gradeslist.add("71");
		gradeslist.add("76");
		gradeslist.add("81");
		gradeslist.add("86");
		gradeslist.add("91");
		gradeslist.add("96");
		for (int i = 0; i < yAxisGroupExpected.length; i++) {
			yAxisGroupExpected[i]++;
			yAxisGroupActual[i] = 0;
		}

		teacherFinalReportControll.groupData(yAxisGroupActual, gradeslist);
		for (int i = 0; i < yAxisGroupExpected.length; i++) {
			assertEquals(yAxisGroupExpected[i], yAxisGroupActual[i]);
		}

	}

	@Test
	public void TestGroupDataSetsGradeHigherthan100() {
		gradeslist.add("150");
		for (int i = 0; i < yAxisGroupExpected.length; i++) {
			yAxisGroupExpected[i] = 0;
			yAxisGroupActual[i] = 0;
		}

		teacherFinalReportControll.groupData(yAxisGroupActual, gradeslist);
		for (int i = 0; i < yAxisGroupExpected.length; i++) {
			assertEquals(yAxisGroupExpected[i], yAxisGroupActual[i]);

		}
	}

	@Test
	public void TestGroupDataYaxisArgNull() {

		try {
			teacherFinalReportControll.groupData(null, gradeslist);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), null);
		}

	}

	@Test
	public void TestGroupDataGradesListArgNull() {

		try {
			teacherFinalReportControll.groupData(yAxisGroupActual, null);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), null);
		}

	}

	@Test
	public void SetHistorgramXchartSuccessfull() {
		gradeslist.add("54");
		gradeslist.add("60");
		gradeslist.add("66");
		gradeslist.add("71");
		gradeslist.add("76");
		gradeslist.add("81");
		gradeslist.add("86");
		gradeslist.add("91");
		gradeslist.add("96");
		for (int i = 0; i < yAxisGroupExpected.length; i++) {
			yAxisGroupExpected[i] = 1;
			yAxisGroupActual[i] = 0;
		}

		teacherFinalReportControll.SetHistogram(yAxisGroupActual, gradeslist, barChart, yAxis, xAxis);
		assertEquals(barChart.getData().get(0).getData().get(0).getXValue(), "0-55");
		assertEquals(barChart.getData().get(0).getData().get(1).getXValue(), "55-65");
		assertEquals(barChart.getData().get(0).getData().get(2).getXValue(), "65-70");
		assertEquals(barChart.getData().get(0).getData().get(3).getXValue(), "70-75");
		assertEquals(barChart.getData().get(0).getData().get(4).getXValue(), "75-80");
		assertEquals(barChart.getData().get(0).getData().get(5).getXValue(), "80-85");
		assertEquals(barChart.getData().get(0).getData().get(6).getXValue(), "85-90");
		assertEquals(barChart.getData().get(0).getData().get(7).getXValue(), "90-95");
		assertEquals(barChart.getData().get(0).getData().get(8).getXValue(), "95-100");

	}

	@Test
	public void SetHistorgramYchartSuccessfull() {
		gradeslist.add("54");
		gradeslist.add("60");
		gradeslist.add("66");
		gradeslist.add("71");
		gradeslist.add("76");
		gradeslist.add("81");
		gradeslist.add("86");
		gradeslist.add("91");
		gradeslist.add("96");
		for (int i = 0; i < yAxisGroupActual.length; i++) {
			yAxisGroupActual[i] = 0;
		}

		teacherFinalReportControll.SetHistogram(yAxisGroupActual, gradeslist, barChart, yAxis, xAxis);
		for (int i = 0; i < yAxisGroupActual.length; i++) {
			assertEquals(barChart.getData().get(0).getData().get(i).getYValue(), yAxisGroupActual[i]);
		}

	}
	@Test
	public void TestHistogramArgNull() {

		try {
			teacherFinalReportControll.SetHistogram(null, null, null, null, null);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), null);
		}

	}
}
