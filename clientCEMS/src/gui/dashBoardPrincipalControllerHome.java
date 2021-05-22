package gui;

import java.io.IOException;

import entity.PersonCEMS;
import entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashBoardPrincipalControllerHome extends PrincipalMainPageController {
	@FXML
	private Text HelloNameMessage;

	public void setWelcomeName() {
		PersonCEMS principal = (Principal) guiControl.getUser();
		HelloNameMessage.setText(principal.getFirstName() + " " + principal.getLastName());
	}
}
