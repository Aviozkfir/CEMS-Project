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

	@FXML
	private Text Name;

	@FXML
	private Button LogOutButton;

	@FXML
	void LogOutButtonPressed(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();

	}

	public void setUser(PersonCEMS person) {

		Name.setText(person.getFirstName() + " " + person.getLastName());
	}

}
