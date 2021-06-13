package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.Principal;
import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * Controller for choosing the type of report the principal wants to choose.
 * 
 * @author On Avioz,Kfir Avioz.
 * @extend PrincipalMainPageController
 * @implements Initializable interface.
 */
public class PrincipalReportController extends PrincipalMainPageController implements Initializable {
	/**
	 * combo box for choosing the type of report.
	 */
	@FXML
	private ComboBox<String> ReportTypeCombo;
	/**
	 * button for start creating the report with the chosen type.
	 */
	@FXML
	private Button CreateButton;
	/**
	 * string to save selected report type.
	 */
	private String SelectedReport = null;
	/**
	 * principal instance.
	 */
	Principal principal = (Principal) guiControl.getUser();

	/**
	 * the principal can choose the type of report he desire in the combobox.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void ChooseReportCombo(ActionEvent event) throws IOException {
		SelectedReport = ReportTypeCombo.getSelectionModel().getSelectedItem();
	}

	/**
	 * when the principal push the button "Create",the system will get the principal
	 * to the next page by the type of course he chose.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void CreateButtonPressed(ActionEvent event) throws IOException {
		if (Istype("Course")) {
			principal.setReportType("Course");
			PrincipalReportCourseControl controller = (PrincipalReportCourseControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
			controller.setRequestCounter();
		}

		else if (Istype("Teacher")) {
			principal.setReportType("Teacher");
			GetTeacherListFromDB();
			PrincipalReportTeacherControl controller = (PrincipalReportTeacherControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_TEACHER_PAGE.path);
			controller.setRequestCounter();
		}

		else if (Istype("Student")) {
			principal.setReportType("Student");
			GetStudentListFromDB();
			PrincipalReportStudentControl controller = (PrincipalReportStudentControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_STUDENT_PAGE.path);
			controller.setRequestCounter();
		}

	}

	/**
	 * IsType returns the type of report : course,student,teacher.
	 * 
	 * @param type
	 */
	public boolean Istype(String type) {
		if (SelectedReport.equals(type))
			return true;
		return false;
	}

	/**
	 * When the principal reach to the main page for report the combobox already has
	 * its options ready.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReportTypeCombo.getItems().addAll("Teacher", "Course", "Student");
	}

	/**
	 * GetTeacherListFromDB gets from the server the list of teachers in the db. and
	 * set the teacherlist array list in principal.
	 */
	public void GetTeacherListFromDB() {
		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_TEACHERS_INFORMATION,

				((Principal) guiControl.getUser()).getId());

		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_TEACHERS_ADDED) {

			ArrayList<Teacher> teacherList = (ArrayList<Teacher>) guiControl.getServerMsg().getMessage();

			principal.setTeacherList(teacherList);

		} else {

			GUIControl.popUpError("Error in loading teachers list to Principal");

		}
	}

	/**
	 * GetStudentListFromDB gets from the server the list of students in the db. and
	 * set the studentlist array list in principal.
	 */
	public void GetStudentListFromDB() {
		ClientMessage msg1 = new ClientMessage(ClientMessageType.PRINCIPAL_STUDENTS_INFORMATION,

				((Principal) guiControl.getUser()).getId());

		guiControl.sendToServer(msg1);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_STUDENTS_ADDED) {

			ArrayList<Student> studentList = (ArrayList<Student>) guiControl.getServerMsg().getMessage();

			principal.setStudentList(studentList);

		} else {

			GUIControl.popUpError("Error in loading students list to Principal");

		}
	}
}
