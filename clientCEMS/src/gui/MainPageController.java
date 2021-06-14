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

/**
 * This class is the Main Controller for all.the users main controllers extends
 * him.
 * 
 * @author On avioz,Kfir Avioz.
 * 
 */
public class MainPageController {
	public final GUIControl guiControl = GUIControl.getInstance();
	/**
	 * User name text
	 */
	@FXML
	private Text Name;
	/**
	 * log out button
	 */
	@FXML
	private Button LogOutButton;

	/**
	 * Method for logging out when pressing on logout button.
	 * 
	 * @param event
	 * 
	 */
	@FXML
	void LogOutButtonPressed(ActionEvent event) {
		guiControl.logOut();
		guiControl.openLoginPage();

	}

	/**
	 * set the user details on Name Text in the gui.
	 * 
	 * @param person
	 * 
	 */
	public void setUser(PersonCEMS person) {

		Name.setText(person.getFirstName() + " " + person.getLastName());
	}

}
