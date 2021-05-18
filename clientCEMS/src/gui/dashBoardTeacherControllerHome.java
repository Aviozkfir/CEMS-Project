package gui;

import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class dashBoardTeacherControllerHome {
	Teacher currentTeacher;
	
	public dashBoardTeacherControllerHome(Teacher teacher) {
		super();
		this.currentTeacher=teacher;
		HelloNameText.setText(currentTeacher.getFirstName()+" "+currentTeacher.getLastName());
	}

	@FXML
    private Button HomeButton;

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
    private Text HelloNameText;

    @FXML
    void ExamBankButtonPressed(ActionEvent event) {

    }

    @FXML
    void GetReportButtonPressed(ActionEvent event) {

    }

    @FXML
    void HomeButtonPressed(ActionEvent event) {

    }

    @FXML
    void LogOutButtonPressed(ActionEvent event) {

    }

    @FXML
    void QuestionBankButtonPressed(ActionEvent event) {

    }

}
