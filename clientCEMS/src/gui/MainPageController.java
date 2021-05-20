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
	private GUIControl guiControl = GUIControl.getInstance();
	private Object user;
	private  String chosenPath;
	private boolean IsDashBord;


	@FXML
	private Button HomePageButton;

	@FXML
	private Button QuestionBankButton;

	@FXML
	private Button ExamBankButton;

	@FXML
	private Button GetReportButton;

	@FXML
	private Text Name;

	@FXML
	private Button LogOutButton;

	@FXML
	private Text HelloNameMessage;

	@FXML
	private Button RequestsButton;

	@FXML
	private Button MyExamsButton;

	@FXML
	private Button StartExamButton;

	@FXML
	void ExamBankButtonPressed(ActionEvent event) throws IOException {
		chosenPath = ClientsConstants.Screens.EXAM_BANK_MAIN_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);

	}

	@FXML
	void GetReportButtonPressed(ActionEvent event) throws IOException {
		chosenPath = ClientsConstants.Screens.REPORT_MAIN_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);
	}
	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {
		if (user instanceof Principal) {
			chosenPath = ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path;
		} else if (user instanceof Teacher) {
			chosenPath = ClientsConstants.Screens.TEACHER_MAIN_PAGE.path;
		} else {
			chosenPath = ClientsConstants.Screens.STUDENT_MAIN_PAGE.path;
		}
		IsDashBord=true;
		loadStage( chosenPath);
	}

	@FXML
	void LogOutButtonPressed(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();

	}

	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {

		chosenPath = ClientsConstants.Screens.QUESTION_BANK_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);
	}

	@FXML
	void RequestsButtonPressed(ActionEvent event) throws IOException {
		chosenPath = ClientsConstants.Screens.MANAGER_REQUESTS_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);
	}

	@FXML
	void MyExamsButtonPressed(ActionEvent event) throws IOException {
		chosenPath = ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);
	}

	@FXML
	void StartExamButtonPressed(ActionEvent event) throws IOException {
		chosenPath = ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path;
		IsDashBord=false;
		loadStage( chosenPath);
	}

	public void setIsDashBord(boolean isDashBord) {
		IsDashBord = isDashBord;
	}

	public void setUser(Object person,boolean isDashBorad) {
		if(person instanceof Teacher)
		    user = (Teacher)person;
		if(person instanceof Principal)
			user=(Principal)person;
		if(person instanceof Student)
			user=(Student) person;
		setNames(isDashBorad);

	}

	public void setNames(boolean IsDashBoard) {
		if(IsDashBoard) {
			HelloNameMessage.setText(((PersonCEMS) user).getFirstName() + " " + (((PersonCEMS) user).getLastName()));
		}
			Name.setText(((PersonCEMS) user).getFirstName() + " " + (((PersonCEMS) user).getLastName()));
		
	}
	
	public void loadStage(String chosenPath) throws IOException {
		MainPageController controller;
		Stage primaryStage = guiControl.getStage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		controller = (MainPageController) fxmlLoader.getController();
		guiControl.setController(controller);
		(controller).setUser(guiControl.getUser(),this.IsDashBord);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();
	}

}
