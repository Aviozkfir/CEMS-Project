package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class TeacherMainPageController extends MainPageController {


	@FXML
	private Button HomePageButton;

	@FXML
	private Button QuestionBankButton;

	@FXML
	private Button ExamBankButton;

	@FXML
	private Button GetReportButton;
	
	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {
		questionBankSubjectsController a = (questionBankSubjectsController) guiControl.loadStage(ClientsConstants.Screens.QUESTION_BANK_PAGE.path);
		a.setTeacherSubject();
	}
	
	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {

		dashBoardTeacherControllerHome a = (dashBoardTeacherControllerHome) guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_PAGE.path);
		
	
	}

	
}
