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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * @author On Avioz. Controller for exam information, when user select exam.
 */
public class PrincipalExamBankViewExamController extends PrincipalMainPageController {
	private ArrayList<Question> allQuestions;
	ArrayList<PrincipalExamViewQuestionTableRowController> questionControllerList = new ArrayList<PrincipalExamViewQuestionTableRowController>();

	/**
	 * The exam.
	 */
	private Exam exam;
	/**
	 * The course of the question.
	 */
	private Course course;
	/**
	 * The name of the question's subject.
	 */
	@FXML
	private Text subjectName;
	/**
	 * The name of the question's course.
	 */
	@FXML
	private Text courseName;
	/**
	 * The exam id.
	 */
	@FXML
	private Text examID;
	/**
	 * The code for the exam.
	 */
	@FXML
	private Text code;
	/**
	 * The exam title.
	 */
	@FXML
	private Text examTitle;
	/**
	 * The total time of the exam.
	 */
	@FXML
	private Text time;
	/**
	 * Notes for students in the exam.
	 */
	@FXML
	private Text studentNotes;
	/**
	 * Notes for teachers in the exam.
	 */
	@FXML
	private Text teacherNotes;
	/**
	 * Back button.
	 */
	@FXML
	private Button Back;
	/**
	 * The table that holds the question in the exam.
	 */
	@FXML
	private VBox vTable;

	/**
	 * @param event ActionEvent
	 * @throws IOException. when Back button pressed, loading exam list of the
	 *                      chosen course, setting new requests text if necessary.
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankExamsController contr = (PrincipalExamBankExamsController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_EXAM.path);
		contr.setPrincipalCourse(course);
		contr.setRequestCounter();
	}

	/**
	 * @param exam   The exam.
	 * @param course The course that holds the exam. This method sending request
	 *               message to server, and getting back question list of the
	 *               desired exam the user chose, setting question dynamically
	 *               inside the Table. for each question, setting question
	 *               information.
	 */
	public void setPrincipalExam(Exam exam, Course course) throws IOException {

		this.exam = exam;
		this.course = course;

		subjectName.setText(course.getSubject().getName());
		courseName.setText(course.getName());
		examID.setText(exam.getEid());
		code.setText(exam.getCode());
		examTitle.setText(exam.getName());
		time.setText(exam.getTotalTime());
		studentNotes.setText(exam.getSdescription());
		teacherNotes.setText(exam.getTdescription());

		ClientMessage m1 = new ClientMessage(ClientMessageType.GET_QUESTION_BY_EXAM, exam);
		guiControl.sendToServer(m1);
		if (guiControl.getServerMsg().getType() == ServerMessageTypes.QUESTION_BY_EXAM_NOT_RECIVED) {
			GUIControl.popUpError("Error-getting Question list for exam.");
		}
		allQuestions = (ArrayList<Question>) guiControl.getServerMsg().getMessage();

		for (Question q : allQuestions) {
			AddTableRow(q);
		}
	}

	public void AddTableRow(Question q) throws IOException {
		PrincipalExamViewQuestionTableRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_QUESTION_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalExamViewQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(q);
		controller.setCourse(course);
		controller.setExam(exam);
		vTable.getChildren().add(root);
		questionControllerList.add(controller);
	}

}
