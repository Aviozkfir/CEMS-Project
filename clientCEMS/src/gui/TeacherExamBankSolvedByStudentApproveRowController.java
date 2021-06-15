

package gui;

import java.io.IOException;
import java.util.function.Supplier;

import entity.Course;
import entity.Exam;
import entity.SolvedExamType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import message.ClientMessage;
import message.ClientMessageType;

public class TeacherExamBankSolvedByStudentApproveRowController {

    public void setSetAmounts(Supplier<Integer> setAmounts) {
		this.setAmounts = setAmounts;
	}

	@FXML
    private Label studentID;

    @FXML
    private Label suspiciousRes;

    @FXML
    private Label automaticGrade;

    @FXML
    private Button btnPublishManual;

    @FXML
    private Button btnCheckExam;

    @FXML
    private Label finalGrade;

    @FXML
    private Label publishStatus;
    
    String[] a;
    SolvedExamType exam;

    private Supplier<Integer> setAmounts;
    
    @FXML
    void btnCheckExamPressed(ActionEvent event) throws IOException {

    	
    	
    	TeacherExamBankSolvedByStudentApproveExamViewController c = ((TeacherExamBankSolvedByStudentApproveExamViewController) GUIControl.instance.loadStage("TeacherExamBankSolvedByStudentApproveExamView.fxml"));
    	c.setExam(a,exam);
    	
    }

    @FXML
    void btnPublishManual(ActionEvent event) {
    	
    	String[] ar = {a[7], null, a[0]  };
    	GUIControl.instance.sendToServer(new ClientMessage( ClientMessageType.TEACHER_ADD_GRADE, ar));
    	finalGrade.setText(a[3]);
    	publishStatus.setText("Checked");
    	setAmounts.get();
    	
    }
    
    public void setRow(String[] a, SolvedExamType exam) {
    	this.a=a;
    	this.exam=exam;
    	studentID.setText(a[1]);
    	suspiciousRes.setText(a[2]);
    	automaticGrade.setText(a[3]);
    	
    	if(a[4].equals("Yes")) {
    		finalGrade.setText(a[7]);
    		publishStatus.setText("Checked");
    	}
    	
    		
    }

}

    
    





