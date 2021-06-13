package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for each exam in the exam list inside specific course.
 * 
 * @author On Avioz ,Kfir Avioz
 * 
 */
public class PrincipalExamBankRowController {
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
	 * The exam title.
	 */
	@FXML
	private Label examTitle;
	/**
	 * Date of question.
	 */
	@FXML
	private Label date;
	/**
	 * The author of the exam.
	 */
	@FXML
	private Label author;

	/**
	 * The exam.
	 */
	private Exam exam;
	/**
	 * The course of the question.
	 */
	private Course course;

	/**
	 * This method setting the exem.
	 * 
	 * @param exam   The exam the user chose.
	 * @param course The course the user chose.
	 */
	public void setExam(Exam exam, Course course) {
		this.exam = exam;
		this.course = course;
		questionID.setText(exam.getEid());
		author.setText(exam.getID());
		date.setText(exam.getDate());
		examTitle.setText(exam.getName());
	}

	/**
	 * When blue eye image button pressed, the user will see the information of the
	 * exam, setting text new request if necessary.
	 * 
	 * @param event ActionEvent
	 * @throws IOException
	 *
	 */
	@FXML
	void btnViewQuestionPressed(ActionEvent event) throws IOException {
		GUIControl guiControl = GUIControl.getInstance();
		PrincipalExamBankViewExamController a = (PrincipalExamBankViewExamController) guiControl
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_VIEW.path);
		a.setPrincipalExam(exam, course);
		a.setRequestCounter();
	}
}
