package gui;

import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class dashBoardStudentControllerHome {
	private Student user;
	private GUIControl guiControl = GUIControl.getInstance();

    @FXML
    private Button HomeButton;

    @FXML
    private Button MyExamsButton;

    @FXML
    private Button StartExamButton;

    @FXML
    private Text Name;

    @FXML
    private Button LogOutButton;

    @FXML
    private Text HelloMessageName;

    @FXML
    void HomeButtonPressed(ActionEvent event) {

    }

    @FXML
    void LogOutButtonPressed(ActionEvent event) {
    	guiControl.logOut();
		guiControl.openLoginPage();

    }

    @FXML
    void MyExamsButtonPressed(ActionEvent event) {

    }

    @FXML
    void StartExamButtonPressed(ActionEvent event) {

    }
    
	public void setUser(Object person) {
		this.user = (Student)person;
		setNames();

	}
	public void setNames() {
		HelloMessageName.setText(((Student) user).getFirstName() + " " + ((Student) user).getLastName());
		Name.setText(((Student) user).getFirstName() + " " + ((Student) user).getLastName());
	}

}
