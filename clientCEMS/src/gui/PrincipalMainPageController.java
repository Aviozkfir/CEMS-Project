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

/**
 * @author On avioz
 * This class is the Main Controller for principal, all other controllers extends him.
 * @extend MainPageController
 */
public class PrincipalMainPageController extends MainPageController  {
	
	/**
	 * HomePage button in menu.
	 */
	@FXML
	private Button HomePageButton;

	/**
	 * Question Bank button in menu.
	 */
	@FXML
	private Button QuestionBankButton;

	/**
	 * Exam Bank button in menu.
	 */
	@FXML
	private Button ExamBankButton;

	/**
	 * Report button in menu.
	 */
	@FXML
	private Button GetReportButton;

	/**
	 * Request button in menu.
	 */
	@FXML
	private Button RequestsButton;
	
	/**
	 * Text that show number of new requests.
	 */
	@FXML
	private Text requests;

	/**
	 * @param event actionEvent when question bank button in menu pressed.
	 * @throws IOException 
	 * This Method loading Principal question bank screen ,setting Subjects inside and setting the new request text message.
	 */
	@FXML
	void QuestionBankButtonPressed(ActionEvent event) throws IOException {
		PrincipalQuestionBankSubjectsController controller = (PrincipalQuestionBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_PAGE.path);
		controller.setPrincipalSubject();
		controller.setRequestCounter();
	}

	/**
	 * @param event actionEvent when Home page button in menu pressed.
	 * @throws IOException
	 * This Method loading Principal home page screen  and setting the new request text message.
	 */
	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {

		dashBoardPrincipalControllerHome controller = (dashBoardPrincipalControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path);
		controller.setRequestCounter();
		
	}
	/**
	 * @param event actionEvent when Exam Bank button in menu pressed.
	 * @throws IOException
	 * This Method loading Principal Exam Bank screen, setting Subjects inside and setting the new request text message.
	 */
	@FXML
	void ExamBankButtonPressed(ActionEvent event) throws IOException {

		PrincipalExamBankSubjectsController controller = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_PAGE.path);
		controller.setPrincipalSubject();
		controller.setRequestCounter();
	}
	/**
	 * @param event actionEvent when Report button in menu pressed.
	 * @throws IOException
	 * This Method loading Principal Reports screen  and setting the new request text message.
	 */
	@FXML
	void GetReportButtonPressed(ActionEvent event) throws IOException {

		PrincipalReportController controller = (PrincipalReportController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_PAGE.path);
		controller.setRequestCounter();
	}
	/**
	 * @param event actionEvent when Requests button in menu pressed.
	 * @throws IOException
	 * This Method loading Principal Requests screen, set requests inside  and setting the new request text message.
	 */
	@FXML
	void RequestsButtonPressed(ActionEvent event) throws IOException {

		PrincipalRequestsController controller = (PrincipalRequestsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REQUESTS_PAGE.path);
		controller.GetRequestListFromDB();
		controller.setPrincipalRequests();
		
	}
	
	/**
	 * This method get counting number for new requests and setting in text which became visible for principal,
	 * if the counter equals zero, the text message is invisible
	 */
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
