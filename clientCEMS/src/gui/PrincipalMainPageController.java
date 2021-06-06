package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.PersonCEMS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class PrincipalMainPageController extends MainPageController  {
	
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
	private Text requests;

	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {
		PrincipalQuestionBankSubjectsController controller = (PrincipalQuestionBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_PAGE.path);
		controller.setPrincipalSubject();
		controller.setRequestCounter();
	}

	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome controller = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path);
		controller.setRequestCounter();
		
	}

	@FXML
	void ExamBankButtonPressed(ActionEvent event) throws IOException {

		PrincipalExamBankSubjectsController controller = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_PAGE.path);
		controller.setPrincipalSubject();
		controller.setRequestCounter();
	}

	@FXML
	void GetReportButtonPressed(ActionEvent event) throws IOException {

		PrincipalReportController controller = (PrincipalReportController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
		controller.setRequestCounter();
	}

	@FXML
	void RequestsButtonPressed(ActionEvent event) throws IOException {

		PrincipalRequestsController controller = (PrincipalRequestsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REQUESTS_PAGE.path);
		controller.GetRequestListFromDB();
		controller.setPrincipalRequests();
		
	}
	
	public void setRequestCounter() {
		int requestCounter = guiControl.getRequestCount();
		if(requestCounter !=0) {
			requests.setVisible(true);
			requests.setText("You Have "+ requestCounter+" new requests.");
		}
		else {
			requests.setVisible(false);
		}
	}
	
	

	

}
