package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Exam;
import entity.Principal;
import entity.SolvedExam;
import entity.Teacher;
import entity.TeachersExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * The main controller for the teacher. 
 * @author oavioz
 *
 */
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
    private Button OnGoingButton;
	Teacher teacher = (Teacher) guiControl.getUser();

	
	/**
	 * This method sending us to the homepage in menu.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void HomePageButtonPressed(ActionEvent event) throws IOException {
		TeacherMainDashBoardController a = (TeacherMainDashBoardController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_WELCOME_PAGE.path);
		
	}
	
	/**
	 * This method sending us to the question bank in menu.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void QuestionPageButtonPressed(ActionEvent event) throws IOException {
		TeacherMainQuestionController a = (TeacherMainQuestionController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_QUESTION_PAGE.path);
		a.setTeacherSubject();
	}
	
	/**
	 * This method sending us to the exam bank in menu.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void ExamPageButtonPressed(ActionEvent event) throws IOException {
		guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_EXAM_PAGE.path);
	}
	
	/**
	 * This method sending us to the report in menu.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void ReportPageButtonPressed(ActionEvent event) throws IOException {
		GetExamListFromDB();
		TeacherMainReportController controller= (TeacherMainReportController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_REPORT_PAGE.path);
		
	}
	/**
	 * This method sending us to the Ongoing page  in menu.
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 void OnGoingPageButtonPressed(ActionEvent event) throws IOException {
		 TeacherOngoingExamsController a = (TeacherOngoingExamsController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_ONGOING_EXAMS_PAGE.path);
		 a.setOngoingExams();
	 }
//	
//	 public void CheckForRefreshOfQuestions() {
//	    	
//	 }
//	 
//	 public void CheckForRezzfreshOfExams() {
//	    	
//	 }
//	
	 
	 /**
	 * This method get Exam list from the DB.
	 */
	public void GetExamListFromDB(){
			ClientMessage msg = new ClientMessage(ClientMessageType.TEACHER_GET_REPORT_EXAM_LIST,

					((Teacher) guiControl.getUser()).getId());

			guiControl.sendToServer(msg);

			if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_GET_REPORT_EXAM_LIST_ADDED) {
				
				ArrayList<TeachersExam> examslist= (ArrayList<TeachersExam>) guiControl.getServerMsg().getMessage();

				teacher.setExamList(examslist);
			}
	 }

	
}
