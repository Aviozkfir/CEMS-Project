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
	void HomePageButtonPressed(ActionEvent event) throws IOException {
		TeacherMainDashBoardController a = (TeacherMainDashBoardController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_WELCOME_PAGE.path);
		
	}
	
	@FXML
	void QuestionPageButtonPressed(ActionEvent event) throws IOException {
		TeacherMainQuestionController a = (TeacherMainQuestionController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_QUESTION_PAGE.path);
		a.setTeacherSubject();
	}
	
	
	@FXML
	void ExamPageButtonPressed(ActionEvent event) throws IOException {
		guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_EXAM_PAGE.path);
	}
	
	@FXML
	void ReportPageButtonPressed(ActionEvent event) throws IOException {
		guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_REPORT_PAGE.path);
	}
	
	
	
	
	

	
}
