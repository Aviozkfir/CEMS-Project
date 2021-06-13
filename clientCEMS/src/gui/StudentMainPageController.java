package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Used to set common elements for most of Student pages
 * 
 * @author Shalom and Omer
 * @extend MainPageController
 *
 */
public class StudentMainPageController extends MainPageController {

	/**
	 * Used as button for Home button tab
	 */
	@FXML
	private Button Home;

	/**
	 * Used as button for My Exams button tab
	 */
	@FXML
	private Button MyExamsButton;

	/**
	 * Used as button for Start Exam button tab
	 */
	@FXML
	private Button StartExamButton;

	/**
	 *Used to load home window when pressed
	 * @param ActionEvent event
	 * @throws IOException 
	 */
	@FXML
	void HomeButtonPressed(ActionEvent event) throws IOException {
		dashBoardStudentControllerHome a = (dashBoardStudentControllerHome) guiControl
				.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);
	}

	/**
	 *Used to go to my exams window when pressed 
	 * @param ActionEvent event
	 * @throws IOException 
	 */
	@FXML
	void MyExamsButtonPressed(ActionEvent event) throws IOException {
		StudentMyExamsController a = (StudentMyExamsController) guiControl
				.loadStage(ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path);
		a.setStudentSubject();

	}

	/**
	*Used to go to Start Exam window when pressed
	 * @param ActionEvent event
	 * @throws IOException 
	 */
	@FXML
	void StartExamButtonPressed(ActionEvent event) throws IOException {
		StudentStartExamController a = (StudentStartExamController) guiControl
				.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	}

}
