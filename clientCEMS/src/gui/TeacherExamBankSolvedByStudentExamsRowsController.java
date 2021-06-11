package gui;

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
    private Label checkStatus;
    
    @FXML
    private Button btnCheckExam;
    
    
    private  String chosenPath;

    private SolvedExamType exam;
   

	private BiConsumer<String,Object> consumer;

    public void setExam(SolvedExamType exam) {
		this.exam = exam;
		this.date.setText(exam.getDate());
		this.examID.setText(exam.getEid());
		this.examTitle.setText(exam.getName());
	}

	@FXML
    void btnCheckPressed(ActionEvent event) {

    }

    @FXML
    void btnViewExamPressed(ActionEvent event) {

    }

}
