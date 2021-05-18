package gui;

import java.util.ArrayList;

import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class CurrentController {
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

	private Object user;
	private GUIControl guiControl = GUIControl.getInstance();

	public void setUser(Object person) {
		this.user = person;
		if (user instanceof Teacher) {
			this.user = (Teacher) person;
			HelloNameText.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());
			Name.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());
		}

	}

	@FXML
	void ExamBankButtonPressed(ActionEvent event) {

	}

	@FXML
	void GetReportButtonPressed(ActionEvent event) {

	}

	@FXML
	void HomeButtonPressed(ActionEvent event) {

	}

	@FXML
	void LogOutButtonPressed(ActionEvent event) {

	}

	@FXML
	void QuestionBankButtonPressed(ActionEvent event) {

	}

}
