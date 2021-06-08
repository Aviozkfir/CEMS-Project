package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
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

public class PrincipalQuestionBankQuestionsController extends PrincipalMainPageController {
	private ArrayList<Question> allQuestions;
	ArrayList<PrincipalQuestionTableRowController> questionControllerList = new ArrayList<PrincipalQuestionTableRowController> ();

	@FXML
	private AnchorPane myRoot;

	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;

	@FXML
	private TextField searchBar;

	@FXML
	private Button btnSearch;

	@FXML
	private Text numberOfQuestions;

	@FXML
	private VBox vTable;

	@FXML
	private Button Back;
	
	@FXML
	private Button showAll;

	private Course course;

	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalQuestionBankCoursesController contr = (PrincipalQuestionBankCoursesController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_COURSES.path);
		contr.setPrincipalCourse(course.getSubject());
		contr.setRequestCounter();
	}

	@FXML
	void btnSearchPressed(ActionEvent event) throws IOException {
		String questionID = searchBar.getText();
		if(CheckInput()) {
		vTable.getChildren().clear(); 
		for (Question q : allQuestions) {
			if (q.getId().equals(questionID)) {
				AddTableRow(q);
				break;
			}
		}
		}
		
	}
	
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
		
		else if(!input.matches("[0-9]+")) {
			GUIControl.popUpError("Error - Please insert numbers only.");
			return false;
		}
		
		else if(input.length() != 5) {
			GUIControl.popUpError("Error - Question number length should be 5, please insert again.");
			return false;
		}
		 
		else if(!exist) {
			GUIControl.popUpError("Error - There is no such a question in the list.");
			return false;
		}
		return true;
		}

	@FXML
	void showAllPressed(ActionEvent event) throws IOException {
		vTable.getChildren().clear(); 
		for (Question q : allQuestions) {
				AddTableRow(q);
			}
		}

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
	
	public void AddTableRow(Question q) throws IOException {
		PrincipalQuestionTableRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_QUESTION_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(q);
		controller.setCourse(course);
		vTable.getChildren().add(root);
		questionControllerList.add(controller);
	}

}
