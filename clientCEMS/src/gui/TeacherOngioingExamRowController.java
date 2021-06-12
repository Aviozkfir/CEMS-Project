package gui;

import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TeacherOngioingExamRowController {

    @FXML
    private Label num;

    @FXML
    private Label title;

    @FXML
    private Label currentDuration;

    @FXML
    private TextField newDurationText;

    @FXML
    private TextArea reasonText;

    @FXML
    private Label examStatus;

    @FXML
    private Button btnChangeTime;

    @FXML
    private Button btnLockExam;
    
    private Exam exam;

    public void setExam(Exam exam) {
		this.exam = exam;
		title.setText(exam.getName());
		currentDuration.setText(exam.getTotalTime());
		examStatus.setText("Not sent yet");
	}
    
    @FXML
    void btnChangeTimePressed(ActionEvent event) {
    //we need to send req to teacher
    }

    @FXML
    void btnLockExamPressed(ActionEvent event) {
    //the exam should be stopped
    }

}
