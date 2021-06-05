package gui;

import java.io.IOException;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {
	public final GUIControl guiControl = GUIControl.getInstance();
	


//	@FXML
//	private Button QuestionBankButton;

//	@FXML
//	private Button ExamBankButton;
//
//	@FXML
//	private Button GetReportButton;

	@FXML
	private Text Name;

	@FXML
	private Button LogOutButton;

	
//
//	@FXML
//	private Button RequestsButton;
//
//	@FXML
//	private Button MyExamsButton;
//
//	@FXML
//	private Button StartExamButton;
//
//	@FXML
//	void ExamBankButtonPressed(ActionEvent event) throws IOException {
//		chosenPath = ClientsConstants.Screens.EXAM_BANK_MAIN_PAGE.path;
//		IsDashBord=false;
//		loadStage( chosenPath);
//
//	}
//
//	@FXML
//	void GetReportButtonPressed(ActionEvent event) throws IOException {
//		chosenPath = ClientsConstants.Screens.REPORT_MAIN_PAGE.path;
//		IsDashBord=false;
//		loadStage( chosenPath);
//	}
	

	@FXML
	void LogOutButtonPressed(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();

	}
//


//	@FXML
//	void RequestsButtonPressed(ActionEvent event) throws IOException {
//		chosenPath = ClientsConstants.Screens.MANAGER_REQUESTS_PAGE.path;
//		IsDashBord=false;
//		loadStage( chosenPath);
//	}
//
//	@FXML
//	void MyExamsButtonPressed(ActionEvent event) throws IOException {
//		chosenPath = ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path;
//		IsDashBord=false;
//		loadStage( chosenPath);
//	}
//
//	@FXML
//	void StartExamButtonPressed(ActionEvent event) throws IOException {
//		chosenPath = ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path;
//		IsDashBord=false;
//		loadStage( chosenPath);
//	}



	
	public void setUser(PersonCEMS person) {
		
		Name.setText(person.getFirstName()+" "+person.getLastName() );
	}

}
