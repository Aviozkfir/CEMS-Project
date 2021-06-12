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

/**
 * @author On Avioz.
 * Controller of principal "HomePage" screen.
 * @extend PrincipalMainPageController.
 * @implements Initializable interface.
 * 
 */
public class dashBoardPrincipalControllerHome extends PrincipalMainPageController implements Initializable {
	/**
	 * Text for the name of the principal in the screen.
	 */
	@FXML
	private Text HelloNameMessage;

	
	
	/**
	 * This Method initialize the HomePage screen for principal when he set the name of the principal in HelloNameMessege.
	 *@param arg0 URL for initialize, not used.
	 *@param arg1 ResourceBundl for initialize, not used.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
			PersonCEMS principal = (Principal) guiControl.getUser();
			HelloNameMessage.setText(principal.getFirstName() + " " + principal.getLastName());
		
	}
}
