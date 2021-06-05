package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashBoardPrincipalControllerHome extends PrincipalMainPageController implements Initializable {
	@FXML
	private Text HelloNameMessage;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
			PersonCEMS principal = (Principal) guiControl.getUser();
			HelloNameMessage.setText(principal.getFirstName() + " " + principal.getLastName());
		
	}
}
