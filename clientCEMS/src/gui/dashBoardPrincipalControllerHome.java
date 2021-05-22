package gui;

import java.io.IOException;

import entity.PersonCEMS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashBoardPrincipalControllerHome extends PrincipalMainPageController{
	@FXML
	private Text HelloNameMessage;
	
	public void setName(PersonCEMS person) {
		HelloNameMessage.setText(person.getFirstName()+" "+person.getLastName() );
	}
}
