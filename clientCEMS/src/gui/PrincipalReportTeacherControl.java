package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.ResourceBundle;

import entity.Principal;
import entity.Report;
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

/**
 * Controller for creating report of desired teacher, where the princiapl enters
 * the ID he wants from the list in the table and Date (date chosen-today).
 * 
 * @author On Avioz,Kfir Avioz.
 * @extend PrincipalMainPageController
 * @implements Initializable interface.
 * 
 */
public class PrincipalReportTeacherControl extends PrincipalMainPageController implements Initializable {
	/**
	 * back button to get back to the previous page.
	 */
	@FXML
	private Button Back;
	/**
	 * Table for the teacher's list list from the db
	 */
	@FXML
	private TableView<Teacher> TableView;
	/**
	 * ID column in the table.
	 */
	@FXML
	private TableColumn<Teacher, String> IDColumn;
	/**
	 * First name column in the table.
	 */
	@FXML
	private TableColumn<Teacher, String> FirstNameColumn;
	/**
	 * Last name column in the table.
	 */
	@FXML
	private TableColumn<Teacher, String> LastNameColumn;
	/**
	 * text for ID - principal enters desired course id.
	 */
	@FXML
	private TextField IDtext;
	/**
	 * date button.
	 */
	@FXML
	private DatePicker datePicker;
	/**
	 * button that takes the data that the princiapl entered to get the desired
	 * report.
	 */
	@FXML
	private Button GetButton;
	/**
	 * report instance
	 */
	private Report report;
	/**
	 * boolean var that checks if the chosen id is in the list (in the table)
	 */
	private boolean idInlist = false;
	/**
	 * Observable List for the table that will show the courses from db.
	 */
	private ObservableList<Teacher> ObsCourseList = FXCollections.observableArrayList();
	/**
	 * Principal instance.
	 */
	private Principal principal = (Principal) guiControl.getUser();
	/**
	 * Array of 2 strings: inputData[0]=chosen id ,inputData[1]=date picked.
	 */
	private String[] inputData = new String[2];

	/**
	 * The user can get back to the previous page.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalReportController a = (PrincipalReportController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
		a.setRequestCounter();
	}

	/**
	 * When the princiapl clicks on get button, the id,date that he chose are sent
	 * to the server and returns the desired data to a report entity and sets into
	 * principal. than moves to the next page that visualy presents the report data.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void GetButtonPressed(ActionEvent event) throws IOException {
		if (validateInput()) {

			inputData[0] = IDtext.getText();
			inputData[1] = datePicker.getValue().toString();
			ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_REPORT_TEACHER_INFORMATION, inputData);
			guiControl.sendToServer(msg);

			if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REPORT_TEACHER_ADDED) {

				ArrayList<String> reportData = (ArrayList<String>) guiControl.getServerMsg().getMessage();
				if (!reportData.isEmpty()) {
					report = new Report(reportData);
					principal.setReport(report);
					principal.getReport().setSelected(IDtext.getText());
					SetMedianAndAverage();
					SetYearRange();

					PrincipalFinalReportControl controller = (PrincipalFinalReportControl) guiControl
							.loadStage(ClientsConstants.Screens.PRINCIPAL_FINAL_REPORT_PAGE.path);
					controller.setRequestCounter();
				} else {
					GUIControl.popUpMessage("The chosen Id and Date range for the report contains 0 solved exams.");
				}
			}
			if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REPORT_TEACHER_NOT_ADDED)
				GUIControl.popUpError("Error in loading teachers-report list  to Principal");
		}
	}

	/**
	 * after the principal chose "teachers" report and clicked Create he immidiatly
	 * see the Table with the Courses in the db.
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObsCourseList.addAll(principal.getTeacherList());
		IDColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("id"));
		FirstNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstName"));
		LastNameColumn.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));
		TableView.getItems().setAll(ObsCourseList);
	}

	/**
	 * validateInput covers all the input validation that have to be checked and
	 * gives the right messages.
	 * @return boolean
	 */
	public boolean validateInput() {
		String[] date = new String[3];

		try {
			date = datePicker.getValue().toString().split("-"); // spliting the not manualy input date into yyyy,MM,dd.
			if (IDtext.getText().isEmpty() || datePicker.getValue().toString().isEmpty()) {
				GUIControl.popUpError("Please fill all the required fields.");
				return false;
			} else if (IDtext.getText().length() != 9 || !IDtext.getText().matches("[0-9]+")) {
				GUIControl.popUpError("Invalid ID input.\nPlease insert only 9 digits");
				return false;
			} else if (date[0].length() != 4 || date[1].length() != 2 || date[2].length() != 2 || date.length != 3
					|| !datePicker.getValue().toString().matches("[0-9\\-/]+")) {
				GUIControl.popUpError("Invalid Date input.\nPlease insert legal date");
				return false;
			} else {
				for (Teacher teacher : principal.getTeacherList()) {
					if (IDtext.getText().equals(teacher.getId()))
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

	/**
	 * SetMedianAndAverage, takes the report data strings, converts to integers and
	 * calculate median and average. afterwards saves the results in principal.
	 */
	public void SetMedianAndAverage() {
		ArrayList<String> gradesString = new ArrayList<String>(principal.getReport().getReportData()); // set

		ArrayList<Integer> grades = new ArrayList<Integer>();
		for (String i : gradesString) {
			Integer k = Integer.parseInt(i);
			grades.add(k);
		}
		principal.getReport().setMedian(Median(grades));
		principal.getReport().setAverage(Average(grades));
	}

	/**
	 * Calculating the Median from the report-data.
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
	 * Calculating the average from the report-data.
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
	 * 
	 * set the year-range by chosen year and current year in principal.
	 */
	public void SetYearRange() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int pickedYear = datePicker.getValue().getYear();
		principal.getReport().setYearRange(pickedYear + "-" + currentYear);
	}
}
