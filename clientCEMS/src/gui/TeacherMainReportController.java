package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;

import entity.Exam;
import entity.Principal;
import entity.Report;
import entity.Student;
import entity.Teacher;
import entity.TeachersExam;
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

public class TeacherMainReportController extends TeacherMainPageController implements Initializable {
	@FXML
	private Button Back;
	@FXML
	private TableView<TeachersExam> TableView;
	@FXML
	private TableColumn<TeachersExam, String> IDColumn;
	@FXML
	private TableColumn<TeachersExam, String> nameColumn;
	@FXML
	private TableColumn<TeachersExam, String> dateColumn;
	@FXML
	private TextField IDtext;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button GetButton;
	private Report report;

	private boolean idInlist = false;
	private ObservableList<TeachersExam> ObsExamList = FXCollections.observableArrayList();
	private Teacher teacher = (Teacher) guiControl.getUser();
	private String[] inputData = new String[2];




	@FXML
	void GetButtonPressed(ActionEvent event) throws IOException {

		if (validateInput()) {

			inputData[0] = IDtext.getText();
			inputData[1] = datePicker.getValue().toString();
			ClientMessage msg = new ClientMessage(ClientMessageType.TEACHER_REPORT_DATA, inputData);
			guiControl.sendToServer(msg);

			if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_REPORT_DATA_ADDED) {
				ArrayList<String> reportData= (ArrayList<String>) guiControl.getServerMsg().getMessage();
				if (!reportData.isEmpty()) {
					report = new Report(reportData);
					teacher.setReport(report);
					teacher.getReport().setSelected(IDtext.getText());
					SetMedianAndAverage();
					SetYearRange();

					TeacherFinalReportControll controller = (TeacherFinalReportControll) guiControl
							.loadStage(ClientsConstants.Screens.TEACHER_FINAL_REPORT.path);
				} else {
					GUIControl.popUpMessage("The chosen Id and Date range for the report contains 0 solved exams.");
				}
			}
			if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_REPORT_DATA_NOT_ADDED)
				GUIControl.popUpError("Error in loading the report data");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObsExamList.addAll(teacher.getExamList());
		IDColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("eid"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("date"));
		TableView.getItems().setAll(ObsExamList);
	}
	

	public boolean validateInput() {
		String[] date = new String[3];

		try {
			date = datePicker.getValue().toString().split("-"); // spliting the not manualy input date into yyyy,MM,dd.
			if (IDtext.getText().isEmpty() || datePicker.getValue().toString().isEmpty()) {
				GUIControl.popUpError("Please fill all the required fields.");
				return false;
			} else if (IDtext.getText().length() != 6 || !IDtext.getText().matches("[0-9]+")) {
				GUIControl.popUpError("Invalid ID input.\nPlease insert only 6 digits");
				return false;
			} else if (date[0].length() != 4 || date[1].length() != 2 || date[2].length() != 2 || date.length != 3
					|| !datePicker.getValue().toString().matches("[0-9\\-/]+")) {
				GUIControl.popUpError("Invalid Date input.\nPlease insert legal date");
				return false;
			} else {
				for (TeachersExam exam : teacher.getExamList()) {
					if (IDtext.getText().equals(exam.getEid()))
						idInlist = true;
				}
				if (!idInlist) {
					GUIControl.popUpError("Id is not in the list.\n Please insert id from the list.");
					return false;
				}
				idInlist = false;
			}
			return true;
		} catch (Exception e) {
			GUIControl.popUpError("Invalid Date input manualy.\nPlease insert legal date or use the button");
			return false;
		}
	}
	

	public void SetMedianAndAverage() {
		ArrayList<String> gradesString = new ArrayList<String>(teacher.getReport().getReportData()); // set

		ArrayList<Integer> grades = new ArrayList<Integer>();
		for (String i : gradesString) {
			Integer k = Integer.parseInt(i);
			grades.add(k);
		}
		teacher.getReport().setMedian(Median(grades));
		teacher.getReport().setAverage(Average(grades));
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
		teacher.getReport().setYearRange(pickedYear + "-" + currentYear);
	}
	
}
