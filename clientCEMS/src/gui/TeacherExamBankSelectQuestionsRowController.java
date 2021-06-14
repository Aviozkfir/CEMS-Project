package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

/**
 * Controller that holds the questions in the exam.
 * 
 * @author On Avioz, Kfir Avioz
 * 
 *
 */
public class TeacherExamBankSelectQuestionsRowController {

	@FXML
	private Button btnView;

	@FXML
	private Label questionID;

	@FXML
	private Label questionText;

	@FXML
	private Label date;

	@FXML
	private CheckBox checkBox;

	private Question question;

	private ArrayList<Question> selectedQuestionList;

	private Text numSelected;

	/**
	 * This Method Setting the number of the question.
	 * 
	 * @param numSelected the number of the question.
	 */
	public void setNumSelected(Text numSelected) {
		this.numSelected = numSelected;
	}

	@FXML
	void btnViewQuestionPressed(ActionEvent event) {

	}

	/**
	 * This method checking the wanted checkboxes.
	 * 
	 * @param event
	 */
	@FXML
	void checkBoxPressed(ActionEvent event) {

		if (checkBox.isSelected()) {
			selectedQuestionList.add(question);
		} else {
			selectedQuestionList.remove(question);
		}
		numSelected.setText("" + selectedQuestionList.size());
	}

	/**
	 * This method setting the question.
	 * 
	 * @param q the specific question we want.
	 */
	public void setQuestion(Question q) {
		question = q;
		questionID.setText(q.getId());
		questionText.setText(q.getText());

		// coverting fate from yyyy-mm-dd to dd-mm-yyyy
		String startDateString = q.getModified().toString();
		DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		//
		date.setText(LocalDate.parse(startDateString, oldFormat).format(newFormat));
	}

	/**
	 * This method setting the list of the questions.
	 * 
	 * @param l array list of questions.
	 */
	public void setList(ArrayList<Question> l) {
		selectedQuestionList = l;
		if (l == null)
			System.out.print("nononon\n\n\n\n");
	}

}
