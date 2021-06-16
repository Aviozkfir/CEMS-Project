package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;

import entity.Report;
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
import message.ServerMessage;
import message.ServerMessageTypes;

/**
 * A class that takes care of reports for teacher
 * 
 * @author Guy and Sharon
 * 
 *
 */
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
	static GUIControl guiControl = GUIControl.getInstance();
	static IServerClientCommunication serverClientCommunication = new ServerClientCommunication();
	private Teacher teacher = (Teacher) serverClientCommunication.getUser();
	private String[] inputData = new String[2];

	static class ServerClientCommunication implements IServerClientCommunication {

		@Override
		public void sendToServer(Object msg) {
			guiControl.sendToServer(msg);

		}

		@Override
		public ServerMessage getServerMsg() {
			return guiControl.getServerMsg();
		}
		
		@Override 
		public void popUpError(String msg) {
			GUIControl.popUpError(msg);
		}
		@Override 
		public  void popUpMessage(String msg) {
			GUIControl.popUpError(msg);
		     
		}
		@Override 
		public Object getUser() {
			return guiControl.getUser();
		     
		}
	}

	/**
	 * A method that gets data about reports and adds it to window
	 * 
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void GetButtonPressed(ActionEvent event) throws IOException {
		inputData[0] = IDtext.getText();
		inputData[1] = datePicker.getValue().toString();
		if (validateInput(inputData,teacher.getExamList())) {
			produceReport(inputData);
		}
	}

	/**
	 * Sets exams list on table
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObsExamList.addAll(teacher.getExamList());
		IDColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("eid"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("name"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<TeachersExam, String>("date"));
		TableView.getItems().setAll(ObsExamList);
	}

	/**
	 * Validates the ID of teacher
	 * 
	 * @return boolean
	 */
	public boolean validateInput(String[] inputData, ArrayList<TeachersExam> examList) {
		String[] date = new String[3];
		
		try {
			date = inputData[1].split("-"); // spliting the not manualy input date into yyyy,MM,dd.
			if (inputData[0].isEmpty() || inputData[1].isEmpty()) {
				serverClientCommunication.popUpError("Please fill all the required fields.");
				return false;
			} else if (inputData[0].length() != 6 || !inputData[0].matches("[0-9]+")) {
				serverClientCommunication.popUpError("Invalid ID input.\nPlease insert only 6 digits");
				return false;
			} else if (date[0].length() != 4 || date[1].length() != 2 || date[2].length() != 2 
					|| !inputData[1].matches("[0-9\\-/]+")) {
				serverClientCommunication.popUpError("Invalid Date input.\nPlease insert legal date");
				return false;
			} else {
				for (TeachersExam exam : examList) {
					if (inputData[0].equals(exam.getEid()))
						idInlist = true;
				}
				if (!idInlist) {
					serverClientCommunication.popUpError("Id is not in the list.\n Please insert id from the list.");
					return false;
				}
				idInlist = false;
			}
			return true;
		} catch (Exception e) {
			serverClientCommunication.popUpError("Invalid Date input manualy.\nPlease insert legal date or use the button");
			return false;
		}
	}

	/**
	 * Sets report of median and average
	 */
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

	/**
	 * returns data about median.
	 * 
	 * @param values
	 * @return String
	 */
	public static String Median(ArrayList<Integer> values) {
		String convertStr = null;
		Collections.sort(values);
		int lower = values.get(values.size() / 2 - 1);
		int upper = values.get(values.size() / 2);

		return convertStr.valueOf((int) ((lower + upper) / 2.0));
	}

	/**
	 * Calculates report about average
	 * 
	 * @param values
	 * @return String
	 */
	public static String Average(ArrayList<Integer> values) {
		String convertStr = null;
		int sum = 0;
		for (int i = 0; i < values.size(); i++)
			sum += values.get(i);
		return convertStr.valueOf(sum / values.size());
	}

	/**
	 * Sets the ranges of years we want reports of.
	 */
	public void SetYearRange() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int pickedYear = datePicker.getValue().getYear();
		teacher.getReport().setYearRange(pickedYear + "-" + currentYear);
	}




	public void produceReport(String[] inputData) throws IOException {
		ClientMessage msg= new ClientMessage(ClientMessageType.TEACHER_REPORT_DATA, inputData);
		serverClientCommunication.sendToServer(msg);
		if (serverClientCommunication.getServerMsg().getType()== ServerMessageTypes.TEACHER_REPORT_DATA_ADDED) {
			ArrayList<String> reportData = (ArrayList<String>) serverClientCommunication.getServerMsg().getMessage();
			if (!reportData.isEmpty()) {
				report = new Report(reportData);
				teacher.setReport(report);
				teacher.getReport().setSelected(IDtext.getText());
				SetMedianAndAverage();
				SetYearRange();

				TeacherFinalReportControll controller = (TeacherFinalReportControll) guiControl
						.loadStage(ClientsConstants.Screens.TEACHER_FINAL_REPORT.path);
			} else {
				serverClientCommunication.popUpMessage("The chosen Id and Date range for the report contains 0 solved exams.");
			}
		}
		if (serverClientCommunication.getServerMsg().getType() == ServerMessageTypes.TEACHER_REPORT_DATA_NOT_ADDED)
			serverClientCommunication.popUpError("Error in loading the report data");
	}
	
	public static void setServerClientCommunication(IServerClientCommunication serverClientCommunication) {
		TeacherMainReportController.serverClientCommunication = serverClientCommunication;
	}
}
