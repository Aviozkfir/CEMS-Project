package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

/**
 * @author On Avioz,Kfir Avioz.
 * @extend PrincipalMainPageController.
 *  Controller for question information, when user select question.
 */
public class PrincipalQuestionBankViewQuestionController extends PrincipalMainPageController {
	/**
	 * The question.
	 */
	private Question question;
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
	 * The question name.
	 */
	@FXML
	private Text questionName;

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
	 * The text of answer B.
	 */
	@FXML
	private Text textB;
	/**
	 * Radio Button of answer C.
	 */
	@FXML
	private RadioButton rbC;
	/**
	 * The text of answer C.
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
	 * @param event ActionEvent
	 * @throws IOException. when Back button pressed, loading question list of the
	 *                      chosen course, setting new requests text if necessary.
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalQuestionBankQuestionsController contr = (PrincipalQuestionBankQuestionsController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_QUESTION_BANK_QUESTION.path);
		contr.setPrincipalCourse(course);
		contr.setRequestCounter();
	}

	/**
	 * @param question The question.
	 * @param course   The course that holds the question. This method sending
	 *                 request message to server, and getting back question list of
	 *                 the desired course the user chose, setting question
	 *                 dynamically inside the Table. for each question, setting
	 *                 question information.
	 */
	public void setPrincipalQuestion(Question question, Course course) throws IOException {

		this.question = question;
		this.course = course;

		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());
		questionName.setText(question.getId());
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
