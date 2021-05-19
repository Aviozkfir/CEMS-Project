package gui;



import entity.Principal;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class dashBoardPrincipalControllerHome {
	private Principal user;
	private GUIControl guiControl = GUIControl.getInstance();

    @FXML
    private Button HomePageButton;

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
    private Text HelloNameMessage;

    @FXML
    void ExamBankButtonPressed(ActionEvent event) {
    	

    }

    @FXML
    void GetReportButtonPressed(ActionEvent event) {

    }

    @FXML
    void HomePageButtonPressed(ActionEvent event) {

    }

    @FXML
    void LogOutButtonPressed(ActionEvent event) {
    	guiControl.logOut();
		guiControl.openLoginPage();

    }

    @FXML
    void QuestionBankButtonPressed(ActionEvent event) {
    	

    }
    
	public void setUser(Object person) {
		this.user = (Principal)person;
		setNames();

	}
	public void setNames() {
		HelloNameMessage.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());
		Name.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());
	}

}
