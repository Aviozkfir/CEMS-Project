package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


/**
 * Controller that holds the exam that solved by student approve.
 * 
 * @author oavioz
 *
 */
public class TeacherExamBankSolvedByStudentApproveRowController {

<<<<<<< HEAD
    @FXML
    private Label studentID;
=======
	@FXML
	private Label studentID;
>>>>>>> refs/heads/OnBranch

	@FXML
	private Label suspiciousRes;

	@FXML
	private Label automaticGrade;

<<<<<<< HEAD
    @FXML
    private TextArea changeGradeExplain1;
=======
	@FXML
	private Button btnApproveAutomatic;
>>>>>>> refs/heads/OnBranch

	@FXML
	private TextArea changeGradeExplain;

	@FXML
	private Button btnPublishSingle;

<<<<<<< HEAD
    @FXML
    private Label publishStatus;

    private String SEid;
    
    @FXML
    void btnPublishSinglePressed(ActionEvent event) {
    	
    }
    
    public void setRow(String[] a, String SEid) {
    	this.SEid=SEid;
    	studentID.setText(a[0]);
    	suspiciousRes.setText(a[1]);
    	automaticGrade.setText(a[2]);
    	
    }
=======
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

>>>>>>> refs/heads/OnBranch
}
