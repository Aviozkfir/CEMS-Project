package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.CourseReport;
import entity.Principal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class PrincipalReportCourseControl extends PrincipalMainPageController implements Initializable {
	@FXML
	private Button Back;
	@FXML
	private TableView<Course> TableView;
	@FXML
	private TableColumn<Course, String> IDColumn;
	@FXML
	private TableColumn<Course, String> NameColumn;
	@FXML
	private TextField IDtext;
	@FXML
	private TextField Yeartext;
	@FXML
	private Button GetButton;

	ObservableList<Course> ObsCourseList = FXCollections.observableArrayList();
	Principal principal = (Principal) guiControl.getUser();
	String[] inputData= new String[2];

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankSubjectsController a = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
		a.setPrincipalSubject();
	}

	@FXML
	void GetButtonPressed(ActionEvent event) throws IOException {


		if (validateInput()) {
			inputData[0]=IDtext.getText();
			inputData[1]=Yeartext.getText();
			ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_REPORT_COURSES_INFORMATION,inputData);
			guiControl.sendToServer(msg);

			if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_REPORT_COURSES_ADDED) {

				ArrayList<CourseReport> courseReportList = (ArrayList<CourseReport>) guiControl.getServerMsg()
						.getMessage();

				principal.setCourseReportList(courseReportList);

			} else {
				GUIControl.popUpError("Error in loading courses-report list  to Principal");
			}

			PrincipalFinalReportControl controller = (PrincipalFinalReportControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_FINAL_REPORT_PAGE.path);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObsCourseList.addAll(principal.getCourseList());
		IDColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Id"));
		NameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("Name"));
		TableView.getItems().setAll(ObsCourseList);
	}

	public boolean validateInput() {
		if (IDtext.getText().isEmpty() || Yeartext.getText().isEmpty())
			GUIControl.popUpError("Please fill all the required fields.");
		return false;
	}

	public String getID() {
		return IDtext.getText();
	}

	public String GetYear() {
		return Yeartext.getText();
	}

}
