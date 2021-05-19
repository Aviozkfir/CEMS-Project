package gui;

import java.io.IOException;

import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashBoardTeacherControllerHome {
	@FXML
	private Button HomeButton;

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
	private Text HelloNameText;

	private Teacher user;
	private GUIControl guiControl = GUIControl.getInstance();

	public void setUser(Object person) {
		this.user = (Teacher)person;
		setNames();

	}

	@FXML
	void ExamBankButtonPressed(ActionEvent event) {

	}

	@FXML
	void GetReportButtonPressed(ActionEvent event) {

	}

	@FXML
	void HomeButtonPressed(ActionEvent event) throws IOException {
	
	}

	@FXML
	void LogOutButtonPressed(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();
	}

	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.QUESTION_BANK_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		//guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();

	}
	
	public void setNames() {
		HelloNameText.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());
		Name.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());
	}


}
