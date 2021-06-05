package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import entity.Course;
import entity.Principal;
import entity.Report;
import entity.Student;
import entity.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class PrincipalReportTeacherControl extends PrincipalMainPageController implements Initializable {
	@FXML
	private Button Back;
	@FXML
	private TableView<Teacher> TableView;
	@FXML
	private TableColumn<Teacher, String> IDColumn;
	@FXML
	private TableColumn<Teacher, String> FirstNameColumn;
	@FXML
	private TableColumn<Teacher, String> LastNameColumn;
	@FXML
	private TextField IDtext;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button GetButton;
	private Report report;

	ObservableList<Teacher> ObsCourseList = FXCollections.observableArrayList();
	Principal principal = (Principal) guiControl.getUser();
	String[] inputData = new String[2];

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalReportController a = (PrincipalReportController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
	}

	@FXML
	void GetButtonPressed(ActionEvent event) throws IOException {

		if (validateInput()) {

			inputData[0] = IDtext.getText();
			inputData[1] = datePicker.getValue().toString();
			System.out.println(datePicker.getValue().toString());
			ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_REPORT_TEACHER_INFORMATION, inputData);
			guiControl.sendToServer(msg);

			if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REPORT_TEACHER_ADDED) {

				HashMap<String, String> reportData = (HashMap<String, String>) guiControl.getServerMsg().getMessage();
				report = new Report(reportData);
				principal.setReport(report);
				SetMedianAndAverage();
				SetYearRange();

			} else {
				GUIControl.popUpError("Error in loading teachers-report list  to Principal");
			}

			PrincipalFinalReportControl controller = (PrincipalFinalReportControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_FINAL_REPORT_PAGE.path);
			controller.setRequestCounter();
		} else {
			GUIControl.popUpError("Problematic, ID:" + IDtext.getText() + "Year:" + datePicker.getValue().toString());
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObsCourseList.addAll(principal.getTeacherList());
		IDColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("id"));
		FirstNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstName"));
		LastNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
		TableView.getItems().setAll(ObsCourseList);
	}

	public boolean validateInput() {
		if (IDtext.getText().isEmpty() || datePicker.getValue().toString().isEmpty()) {
			GUIControl.popUpError("Please fill all the required fields.");
			return false;
		}
		return true;
	}

	public void SetMedianAndAverage() {
		ArrayList<String> gradesString = new ArrayList<String>(principal.getReport().getReportData().keySet()); // set

		ArrayList<Integer> grades = new ArrayList<Integer>();
		for (String i : gradesString) {
			Integer k = Integer.parseInt(i);
			grades.add(k);
		}
		principal.getReport().setMedian(Median(grades));
		principal.getReport().setAverage(Average(grades));
	}

	public static String Median(ArrayList<Integer> values) {
		String convertStr = null;
		Collections.sort(values);
		int lower = values.get(values.size() / 2 - 1);
		int upper = values.get(values.size() / 2);

		return convertStr.valueOf((int) ((lower + upper) / 2.0));
	}

	public static String Average(ArrayList<Integer> values) {
		String convertStr = null;
		int sum = 0;
		for (int i = 0; i < values.size(); i++)
			sum += values.get(i);
		return convertStr.valueOf(sum / values.size());
	}

	public void SetYearRange() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int pickedYear = datePicker.getValue().getYear();
		principal.getReport().setYearRange(pickedYear + "-" + currentYear);
	}
}
