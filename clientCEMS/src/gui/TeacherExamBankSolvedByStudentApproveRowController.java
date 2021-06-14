package gui;

import entity.Question;
import entity.SolvedExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Controller that holds the exam that solved by student approve.
 * 
 * @author oavioz
 *
 */
public class TeacherExamBankSolvedByStudentApproveRowController {

	@FXML
	private Label studentID;

	@FXML
	private Label suspiciousRes;

	@FXML
	private Label automaticGrade;

	@FXML
	private Button btnApproveAutomatic;

	@FXML
	private TextArea changeGradeExplain;

	@FXML
	private Button btnPublishSingle;

	@FXML
	private Label publishStatus;
	@FXML
	private Text finalGrade;

	private SolvedExam solvedExam;/////

	/**
	 * This method setting the solved exam.
	 * 
	 * @param solvedE The solved exam.
	 */
	public void setSolvedExam(SolvedExam solvedE) {
		solvedExam = solvedE;
		studentID.setText(solvedE.getID());
		automaticGrade.setText(solvedE.getFinalGrade());

	}

	/**
	 * This method approved automatically.
	 * 
	 * @param event
	 */
	@FXML
	void approveAutomaticButtonPressed(ActionEvent event) {
		finalGrade.setText(automaticGrade.getText());
	}

	@FXML
	void btnCheckExamPressed(ActionEvent event) {

	}

	/**
	 * This method setting the text published.
	 * 
	 * @param event
	 */
	@FXML
	void btnPublishSinglePressed(ActionEvent event) {
		publishStatus.setText("Published!");
	}

}
