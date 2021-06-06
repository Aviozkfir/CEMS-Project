package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
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
		PrincipalQuestionTableRowController controller;
		String questionID = searchBar.getText();
		boolean exist = false;
		vTable.getChildren().clear(); 
		for (Question q : allQuestions) {
			if (q.getId().equals(questionID)) {
				exist = true;
				AddTableRow(q);
				break;
			}
		}
		if (!exist)
			GUIControl.popUpError("There is no such a question in the list.");
	}

	@SuppressWarnings("unchecked")
	public void setPrincipalCourse(Course course) throws IOException {

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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/PrincipalQuestionTableRow.fxml"));
		for (Question q : allQuestions) {
			AddTableRow(q);
		}
	}
	
	public void AddTableRow(Question q) throws IOException {
		PrincipalQuestionTableRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/PrincipalQuestionTableRow.fxml"));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(q);
		controller.setCourse(course);
		vTable.getChildren().add(root);
	}

}
