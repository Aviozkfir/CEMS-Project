package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * Controller for each exam inside chosen course inside chosen subject inside
 * exam bank screen.
 * 
 * @author On Avioz ,Kfir Avioz
 * @extend PrincipalMainPageController
 */
public class PrincipalExamBankExamsController extends PrincipalMainPageController {

	/**
	 * array list that holds the exams.
	 */
	private ArrayList<Exam> allExams;
	/**
	 * array list that holds the dynamic controllers of the exams.
	 */
	ArrayList<PrincipalExamBankRowController> examControllerList = new ArrayList<PrincipalExamBankRowController>();

	/**
	 * The AnchorPane of question.
	 */
	@FXML
	private AnchorPane myRoot;

	/**
	 * Subject Name.
	 */
	@FXML
	private Text subjectName;
	/**
	 * Course Name.
	 */
	@FXML
	private Text courseName;
	/**
	 * Number of exams.
	 */
	@FXML
	private Text numberOfExams;
	/**
	 * the table where exams setted on.
	 */
	@FXML
	private VBox vTable;
	/**
	 * Back button.
	 */
	@FXML
	private Button Back;
	/**
	 * Search field.
	 */
	@FXML
	private TextField searchBar;
	/**
	 * Search button.
	 */
	@FXML
	private Button btnSearch;
	/**
	 * showing all exams in the list, made for case when we filtering exams.
	 */
	@FXML
	private Button showAll;

	/**
	 * The course of the questions.
	 */
	private Course course;

	/**
	 * when Back button pressed, loading exam Bank screen, setting new requests text
	 * if necessary
	 * 
	 * @param event ActionEvent
	 * @throws IOException. .
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankCoursesController contr = (PrincipalExamBankCoursesController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAl_EXAM_BANK_COURSES.path);
		contr.setPrincipalCourse(course.getSubject());
		contr.setRequestCounter();

	}

	/**
	 * when search button pressed, filtering the wanted exam by him number.
	 * 
	 * @param event ActionEvent
	 * @throws IOException.
	 */
	@FXML
	void btnSearchPressed(ActionEvent event) throws IOException {
		String examID = searchBar.getText();
		if (CheckInput()) {
			vTable.getChildren().clear();
			for (Exam e : allExams) {
				if (e.getEid().equals(examID)) {
					AddTableRow(e);
					break;
				}
			}
		}
	}

	/**
	 * This method check if input is valid if input is incorrect, the user will get
	 * pop up with the appropriate message of the error.
	 * 
	 * @return true if input is correct, false when incorrect input.
	 */
	private boolean CheckInput() {
		String input = searchBar.getText();
		boolean exist = false;

		for (Exam q : allExams) {
			if (q.getEid().equals(input)) {
				exist = true;
			}
		}

		if (input.isEmpty()) {
			GUIControl.popUpError("Error - Field is empty, please write correct exam number.");
			return false;
		}

		else if (!input.matches("[0-9]+")) {
			GUIControl.popUpError("Error - Please insert numbers only.");
			return false;
		}

		else if (input.length() != 6) {
			GUIControl.popUpError("Error - Exam number length should be 6, please insert again.");
			return false;
		}

		else if (!exist) {
			GUIControl.popUpError("Error - There is no such a Exam in the list.");
			return false;
		}
		return true;
	}

	/**
	 * When showAll button pressed, the user get the original exam list.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 */
	@FXML
	void showAllPressed(ActionEvent event) throws IOException {
		vTable.getChildren().clear();
		for (Exam e : allExams) {
			AddTableRow(e);
		}
	}

	/**
	 * This method sending request message to server, and getting back exam list of
	 * the desired course the user chose, setting exam dynamically inside the Table.
	 * for each exam, setting exam information.
	 * 
	 * @param course the course that holds the exam.
	 */
	public void setPrincipalCourse(Course course) throws IOException {

		this.course = course;

		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());

		ClientMessage m1 = new ClientMessage(ClientMessageType.GET_EXAM_BY_COURSE, course);
		guiControl.sendToServer(m1);
		if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_BY_COURSE_NOT_RECIVED) {
			GUIControl.popUpError("Error-getting Exam list for principal.");
		}
		allExams = (ArrayList<Exam>) guiControl.getServerMsg().getMessage();

		numberOfExams.setText("" + allExams.size());
		for (Exam e : allExams) {
			AddTableRow(e);
		}
	}

	/**
	 * This method setting the exam in the table and setting text new request if
	 * necessary.
	 * 
	 * @param exam an exam from exam list we got from server.
	 * @throws IOException
	 */
	public void AddTableRow(Exam exam) throws IOException {
		PrincipalExamBankRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalExamBankRowController) fxmlLoader.getController();
		controller.setExam(exam, course);
		vTable.getChildren().add(root);
		examControllerList.add(controller);
	}

}
