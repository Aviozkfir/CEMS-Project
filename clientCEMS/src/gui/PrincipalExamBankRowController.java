package gui;

import java.io.IOException;

import entity.Course;
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
    private Course course;
    
    public void setExam(Exam exam,Course course) {
		this.exam = exam;
		this.course = course;
		questionID.setText(exam.getEid());
		author.setText(exam.getID());
		date.setText(exam.getDate());
		examTitle.setText(exam.getName());
	}

    @FXML
    void btnViewQuestionPressed(ActionEvent event) throws IOException {
    	   GUIControl guiControl = GUIControl.getInstance();
    	   PrincipalExamBankViewExamController a = (PrincipalExamBankViewExamController) guiControl.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_VIEW.path);
       			a.setPrincipalExam(exam,course);
       			a.setRequestCounter();
    }
}
