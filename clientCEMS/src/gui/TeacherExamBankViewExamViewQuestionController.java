package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class TeacherExamBankViewExamViewQuestionController extends TeacherMainPageController {
	/**
	 * The question.
	 */
	private Question question;
	/**
	 * The course of the question.
	 */
	private Course course;
	/**
	 * The exam that holds this question.
	 */
	private Exam exam;
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
	 * The question name.
	 */
	@FXML
	private Text questionName;
	/**
	 * The exam id that hold this question.
	 */
	@FXML
	private Text examID;
	/**
	 * The text of the question.
	 */
	@FXML
	private Text textQuestion;
	/**
	 * Radio Button of answer A.
	 */
	@FXML
	private RadioButton rbA;
	/**
	 * The text of answer A.
	 */
	@FXML
	private Text textA;
	/**
	 * Radio Button of answer B.
	 */
	@FXML
	private RadioButton rbB;
	/**
	 * Radio Button of answer C.
	 */
	@FXML
	private Text textB;
	/**
	 * The text of answer C.
	 */
	@FXML
	private RadioButton rbC;
	/**
	 * Radio Button of answer D.
	 */
	@FXML
	private Text textC;
	/**
	 * Radio Button of answer D.
	 */
	@FXML
	private RadioButton rbD;
	/**
	 * The text of answer D.
	 */
	@FXML
	private Text textD;
	/**
	 * Back button.
	 */
	@FXML
	private Button Back;

	/**
	 * when Back button pressed, loading question list of the chosen exam, setting
	 * new requests text if necessary.
	 * 
	 * @param event ActionEvent
	 * @throws IOException.
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		TeacherExamBankViewExamController contr = (TeacherExamBankViewExamController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.TEACHER_EXAM_VIEW.path);
		contr.setTeacherExam(exam, course);
	}

	/**
	 * This method sending request message to server, and getting back question list
	 * of the desired exam the user chose, setting question dynamically inside the
	 * Table. for each question, setting question information.
	 * 
	 * @param question The question.
	 * @param course   The course that holds the question.
	 * @param exam     The exam that holds the question.
	 */
	public void setTeacherQuestion(Question question, Course course, Exam exam) throws IOException {

		this.question = question;
		this.course = course;
		this.exam = exam;

		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());
		questionName.setText(question.getId());
		examID.setText(exam.getEid());
		textQuestion.setText(question.getText());
		textA.setText(question.getAnsA());
		textB.setText(question.getAnsB());
		textC.setText(question.getAnsC());
		textD.setText(question.getAnsD());
		setCorrectAns();
	}

	/**
	 * This method setting the correct answer in the question.
	 */
	private void setCorrectAns() {
		int correct = question.getCorrectAnswer();
		switch (correct) {

		case 1:
			rbA.setSelected(true);
			break;

		case 2:
			rbB.setSelected(true);
			break;

		case 3:
			rbC.setSelected(true);
			break;

		case 4:
			rbD.setSelected(true);
			break;
		}

	}
}
