package gui;

import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrincipalExamBankRowController {

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label examTitle;

    @FXML
    private Label date;

    @FXML
    private Label author;


    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
		questionID.setText(exam.getEid());
		author.setText(exam.getID());
		date.setText(exam.getDate());
		examTitle.setText(exam.getName());
	}

    @FXML
    void btnViewQuestionPressed(ActionEvent event) {
    	
    }
}
