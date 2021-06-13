package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for each question in the question list inside specific exam.
 * 
 * @author On Avioz,Kfir Avioz.
 * 
 */
public class PrincipalExamViewQuestionTableRowController {

	/**
	 * The question.
	 */
	private Question question;
	/**
	 * The course of the question.
	 */
	private Course course;
	/**
	 * The exam of the question.
	 */
	private Exam exam;
	/**
	 * Blue eye image button.
	 */
	@FXML
	private Button btnView;
	/**
	 * The question id.
	 */
	@FXML
	private Label questionID;
	/**
	 * The question text.
	 */
	@FXML
	private Label questionText;
	/**
	 * Date of question.
	 */
	@FXML
	private Label date;

	/**
	 * This method setting the information of the question.
	 * 
	 * @param question The question the user chose.
	 */
	public void setQuestion(Question question) {
		this.question = question;
		questionText.setText(question.getText());
		date.setText(question.getModified().toString());
		questionID.setText("" + question.getId());

	}

	/**
	 * This method setting the course of the question the user chose.
	 * 
	 * @param course The course of the question the user chose.
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * When blue eye image button pressed, the user will see the information of the
	 * question, setting text new request if necessary.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 */
	@FXML
	void btnViewQuestionPressed(ActionEvent event) throws IOException {
		GUIControl guiControl = GUIControl.getInstance();
		PrincipalExamBankViewExamViewQuestionController a = (PrincipalExamBankViewExamViewQuestionController) guiControl
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_VIEW_QUESTION_VIEW.path);
		a.setPrincipalQuestion(question, course, exam);
		a.setRequestCounter();
	}

	/**
	 * This method setting the exam of the question the user chose.
	 * 
	 * @param exam The exam of the question the user chose.
	 */
	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
