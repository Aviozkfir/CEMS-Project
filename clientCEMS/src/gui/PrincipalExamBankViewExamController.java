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

public class PrincipalExamBankViewExamController extends PrincipalMainPageController {
	private ArrayList<Question> allQuestions;
	ArrayList<PrincipalExamViewQuestionTableRowController> questionControllerList = new ArrayList<PrincipalExamViewQuestionTableRowController> ();
	
	
	private Exam exam;
	
	private Course course;
	
	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;
	
	@FXML
	private Text examID;
	
	@FXML
	private Text code;
	
	@FXML
	private Text examTitle;
	
	@FXML
	private Text time;
	
	@FXML
	private Text studentNotes;
	
	@FXML
	private Text teacherNotes;
	
	@FXML
	private Button Back;
	
	@FXML
	private VBox vTable;
	
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankExamsController contr = (PrincipalExamBankExamsController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_EXAM.path);
		contr.setPrincipalCourse(course);
		contr.setRequestCounter();
	}

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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_QUESTION_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalExamViewQuestionTableRowController) fxmlLoader.getController();
		controller.setQuestion(q);
		controller.setCourse(course);
		controller.setExam(exam);
		vTable.getChildren().add(root);
		questionControllerList.add(controller);
	} 

}
