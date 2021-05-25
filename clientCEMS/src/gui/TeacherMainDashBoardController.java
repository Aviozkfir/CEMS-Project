package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TeacherMainDashBoardController extends TeacherMainPageController implements Initializable{
	
	@FXML
	private Text HelloNameMessage;
	
	public void setName() {
		
		Teacher a= (Teacher)guiControl.getUser();
		HelloNameMessage.setText(a.getFirstName()+" "+a.getLastName());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setName();
		
	}

}
