package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrincipalMainPageController extends MainPageController {
	@FXML
	private Button HomePageButton;

	@FXML
	private Button QuestionBankButton;

	@FXML
	private Button ExamBankButton;

	@FXML
	private Button GetReportButton;

	@FXML
	private Button RequestsButton;

	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {
		questionBankSubjectsController a = (questionBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_PAGE.path);
		a.setTeacherSubject();
	}

	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome a = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path);
	}
	
	@FXML
	void ExamBankButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome a = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_PAGE.path);
	}
	
	@FXML
	void GetReportButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome a = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
	}
	
	@FXML
	void RequestsButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome a = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REQUESTS_PAGE.path);
	}
	
}
