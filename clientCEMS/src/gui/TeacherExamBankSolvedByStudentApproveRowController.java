package gui;

import entity.Question;
import entity.SolvedExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private Button btnCheckExam;

    @FXML
    private Label finalGrade;

    @FXML
    private Button btnPublishSingle;

    @FXML
    private Label publishStatus;
    
    private SolvedExam solvedExam;/////
    
    public void setSolvedExam(SolvedExam solvedE) {
    	solvedExam=solvedE;
    	studentID.setText(solvedE.getID());
    	automaticGrade.setText(solvedE.getFinalGrade());
    	
    }

    @FXML
    void approveAutomaticButtonPressed(ActionEvent event) {
    	finalGrade.setText(automaticGrade.getText());
    }

    @FXML
    void btnCheckExamPressed(ActionEvent event) {

    }

    @FXML
    void btnPublishSinglePressed(ActionEvent event) {
    	publishStatus.setText("Published!");
    }

}
