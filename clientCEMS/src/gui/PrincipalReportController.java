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

public class PrincipalReportController extends PrincipalMainPageController implements Initializable{
	
	private String SelectedReport=null;
	
	Principal principal = (Principal) guiControl.getUser();
	@FXML
	private ComboBox<String> ReportTypeCombo;
	@FXML
	private Button CreateButton;
	
	

	@FXML void ChooseReportCombo(ActionEvent event) throws IOException {
		SelectedReport = ReportTypeCombo.getSelectionModel().getSelectedItem();
	}

	@FXML
	void CreateButtonPressed(ActionEvent event) throws IOException {
		if (Istype("Course")) {
			principal.setReportType("Course");
			PrincipalReportCourseControl controller = (PrincipalReportCourseControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
		}
		
		else if (Istype("Teacher")) {
			principal.setReportType("Teacher");
			GetTeacherListFromDB();
			PrincipalReportTeacherControl controller = (PrincipalReportTeacherControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_TEACHER_PAGE.path);
			
		}
		
		else if (Istype("Student")) {
			principal.setReportType("Student");
			GetStudentListFromDB();
			PrincipalReportStudentControl controller = (PrincipalReportStudentControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_STUDENT_PAGE.path);
			
		}

	}

	public boolean Istype(String type) {
		if (SelectedReport.equals(type))
			return true;
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReportTypeCombo.getItems().addAll("Teacher","Course","Student");
	}
	
	public void GetTeacherListFromDB(){
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
	
	public void GetStudentListFromDB(){
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
