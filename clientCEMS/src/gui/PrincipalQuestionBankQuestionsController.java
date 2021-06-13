package gui;

import java.io.IOException;
import java.util.ArrayList;
import entity.Course;
import entity.Principal;
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
 * Controller for each question inside chosen course inside chosen subject
 * inside question bank screen.
 * 
 * @author On Avioz,Kfir Avioz.
 * @extend PrincipalMainPageController
 *
 *
 */
public class PrincipalQuestionBankQuestionsController extends PrincipalMainPageController {
	/**
	 * Array list that holds all questions.
	 */
	private ArrayList<Question> allQuestions;
	/**
	 * Array list that holds all the dynamic controllers for the questions.
	 */
	ArrayList<PrincipalQuestionTableRowController> questionControllerList = new ArrayList<PrincipalQuestionTableRowController>();

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
	 * Number of questions.
	 */
	@FXML
	private Text numberOfQuestions;

	/**
	 * the table where questions settled on.
	 */
	@FXML
	private VBox vTable;

	/**
	 * Back button.
	 */
	@FXML
	private Button Back;

	/**
	 * showing all questions in the list, made for case when we filtering questions.
	 */
	@FXML
	private Button showAll;

	/**
	 * The course of the questions.
	 */
	private Course course;

	/**
	 * when Back button pressed, loading Question Bank screen, setting new requests
	 * text if necessary.
	 * 
	 * @param event ActionEvent
	 * @throws IOException.
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalQuestionBankCoursesController contr = (PrincipalQuestionBankCoursesController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_COURSES.path);
		contr.setPrincipalCourse(course.getSubject());
		contr.setRequestCounter();
	}

	/**
	 * when search button pressed, filtering the wanted question by him number.
	 * 
	 * @param event ActionEvent
	 * @throws IOException.
	 */
	@FXML
	void btnSearchPressed(ActionEvent event) throws IOException {
		String questionID = searchBar.getText();
		if (CheckInput()) {
			vTable.getChildren().clear();
			for (Question q : allQuestions) {
				if (q.getId().equals(questionID)) {
					AddTableRow(q);
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

		for (Question q : allQuestions) {
			if (q.getId().equals(input)) {
				exist = true;
			}
		}

		if (input.isEmpty()) {
			GUIControl.popUpError("Error - Field is empty, please write correct question number.");
			return false;
		}

		else if (!input.matches("[0-9]+")) {
			GUIControl.popUpError("Error - Please insert numbers only.");
			return false;
		}

		else if (input.length() != 5) {
			GUIControl.popUpError("Error - Question number length should be 5, please insert again.");
			return false;
		}

		else if (!exist) {
			GUIControl.popUpError("Error - There is no such a question in the list.");
			return false;
		}
		return true;
	}

	/**
	 * When showAll button pressed, the user get the original question list.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 */
	@FXML
	void showAllPressed(ActionEvent event) throws IOException {
		vTable.getChildren().clear();
		for (Question q : allQuestions) {
			AddTableRow(q);
		}
	}

	/**
	 * This method sending request message to server, and getting back question list
	 * of the desired course the user chose, setting question dynamically inside the
	 * Table. for each question, setting question information.
	 * 
	 * @param course the course that holds the question.
	 */
	@SuppressWarnings("unchecked")
	public void setPrincipalCourse(Course course) throws IOException {
		Principal principal = (Principal) GUIControl.instance.getUser();
		this.course = course;

		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());

		ClientMessage m1 = new ClientMessage(ClientMessageType.GET_QUESTION_BY_COURSE, course);
		guiControl.sendToServer(m1);
		if (guiControl.getServerMsg().getType() == ServerMessageTypes.QUESTION_BY_COURSE_NOT_RECIVED) {
			GUIControl.popUpError("Error-getting Question list for principal.");
		}
		allQuestions = (ArrayList<Question>) guiControl.getServerMsg().getMessage();
		numberOfQuestions.setText("" + allQuestions.size());
		for (Question q : allQuestions) {
			AddTableRow(q);
		}
	}

	/**
	 * This method setting the question in the table and setting text new request if
	 * necessary.
	 * 
	 * @param q an question from question list we got from server.
	 * @throws IOException
	 */
	public void AddTableRow(Question q) throws IOException {
		PrincipalQuestionTableRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_QUESTION_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(q);
		controller.setCourse(course);
		vTable.getChildren().add(root);
		questionControllerList.add(controller);
	}

}
