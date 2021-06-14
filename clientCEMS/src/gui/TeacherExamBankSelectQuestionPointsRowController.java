package gui;

import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author On Avioz, Kfir Avioz This class holds the points for questions in
 *         exam.
 *
 */
public class TeacherExamBankSelectQuestionPointsRowController {

	@FXML
	private Button btnView;

	@FXML
	private Label questionID;

	@FXML
	private Label questionText;

	@FXML
	private TextField points;

	public String getPoints() {
		return points.getText();
	}

	private Question question;

	/**
	 * @param q the question we want to set.
	 */
	public void setQuestion(Question q) {
		question = q;
		questionID.setText(q.getId());
		questionText.setText(q.getText());
	}

	/**
	 * @param event
	 */
	@FXML
	void btnViewQuestionPressed(ActionEvent event) {

	}

}
