package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class TeacherExamBankSolvedByStudentApproveRowController {

    @FXML
    private Label studentID;

    @FXML
    private Label suspiciousRes;

    @FXML
    private Label automaticGrade;

    @FXML
    private TextArea changeGradeExplain1;

    @FXML
    private TextArea changeGradeExplain;

    @FXML
    private Button btnPublishSingle;

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
}
