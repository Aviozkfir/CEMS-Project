package gui;

import java.io.IOException;
import java.util.function.BiConsumer;

import entity.SolvedExamType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class TeacherExamBankSolvedByStudentExamsRowsController {

    @FXML
    private Button btnView;

    @FXML
    private Label examID;

    @FXML
    private Label examTitle;

    @FXML
    private Label date;

    @FXML
    private Text checkedNum;

    @FXML
    private Text totalNum;
    
    @FXML
    private Button btnCheckExam;
    
    
    

    private SolvedExamType exam;
   

	

    public void setExam(SolvedExamType exam) {
		this.exam = exam;
		this.date.setText(exam.getDate());
		this.examID.setText(exam.getEid());
		this.examTitle.setText(exam.getName());
		this.totalNum.setText(exam.getTotal()+"");
		this.checkedNum.setText(exam.getChecked()+"");
	}

	@FXML
    void btnCheckPressed(ActionEvent event) throws IOException {
		TeacherExamBankSolvedByStudentApproveController a = ((TeacherExamBankSolvedByStudentApproveController) GUIControl.instance.loadStage("TeacherExamBankSolvedByStudentApprove.fxml"));
    	a.setExamType(exam);
    }

    @FXML
    void btnViewExamPressed(ActionEvent event) {

    }

}
