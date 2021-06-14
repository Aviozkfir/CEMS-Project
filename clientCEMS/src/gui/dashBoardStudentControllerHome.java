package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class dashBoardStudentControllerHome extends StudentMainPageController implements Initializable {
	@FXML
	private Text HelloNameMessage;
    
  

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PersonCEMS student = (Student) guiControl.getUser();
		HelloNameMessage.setText(student.getFirstName() + " " + student.getLastName());
		
	}

}
