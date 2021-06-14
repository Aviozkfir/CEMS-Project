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
 * Controller that holds the view of the desired exam.
 * @author oavioz
 *
 */
public class TeacherExamBankViewExamController extends TeacherMainPageController{
	
	/**
	 * Array list that holds all questions.
	 */
	private ArrayList<Question> allQuestions;
	/**
	 * Array list that holds the dynamic controllers of the questions.
	 */
	ArrayList<TeacherExamViewQuestionTableRowController> questionControllerList = new ArrayList<TeacherExamViewQuestionTableRowController>();


	/**
	 * The exam.
	 */
	private Exam exam;
	/**
	 * The course of the question.
	 */
	private Course course;
	
    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text examID;

    @FXML
    private Text examTitle;

    @FXML
    private Text time;

    @FXML
    private Text code;

    @FXML
    private Text studentNotes;

    @FXML
    private Text teacherNotes;

    @FXML
    private VBox vTable;

    @FXML
    private Button Back;

    /**
     * This method gets us into the previous screen , the exams list.
     * @param event
     * @throws IOException
     */
    @FXML
	void BackPressed(ActionEvent event) throws IOException {
		TeacherExamBankExamsController contr = (TeacherExamBankExamsController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.TEACHER_EXAM_BANK_EXAMS.path);
		contr.setTeacherCourse(course);
	}

	/**
	 * This method sending request message to server, and getting back question list
	 * of the desired exam the user chose, setting question dynamically inside the
	 * Table. for each question, setting question information.
	 * 
	 * @param exam   The exam.
	 * @param course The course that holds the exam.
	 */
	public void setTeacherExam(Exam exam, Course course) throws IOException {

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

	/**
	 * This method adding dynamically the questions into the list of questions in
	 * the exam.
	 * 
	 * @param question The question of the exam that the user chose.
	 * @throws IOException
	 */
	public void AddTableRow(Question question) throws IOException {
		TeacherExamViewQuestionTableRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource(gui.ClientsConstants.Screens.TEACHER_EXAM_QUESTION_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (TeacherExamViewQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(question);
		controller.setCourse(course);
		controller.setExam(exam);
		vTable.getChildren().add(root);
		questionControllerList.add(controller);
	}

}
