package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		//coverting fate from yyyy-mm-dd to dd-mm-yyyy
    	String startDateString = exam.getDate().toString();
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	//
		this.date.setText(LocalDate.parse(startDateString, oldFormat).format(newFormat));
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
