package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrincipalExamViewQuestionTableRowController {
	

	private Question question;
	
	private Course course;
	
	private Exam exam;

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label questionText;

    @FXML
    private Label date;

    
    public void setQuestion(Question question) {
    	this.question=question;
    	questionText.setText(question.getText());
    	date.setText(question.getModified().toString());
    	questionID.setText(""+question.getId());
  
    }
    
    public void setCourse(Course course) {
    	this.course=course;
    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) throws IOException {
     GUIControl guiControl = GUIControl.getInstance();
     PrincipalExamBankViewExamViewQuestionController a = (PrincipalExamBankViewExamViewQuestionController) guiControl.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_VIEW_QUESTION_VIEW.path);
    			a.setPrincipalQuestion(question,course,exam);
    			a.setRequestCounter();
    }

	public void setExam(Exam exam) {
		this.exam = exam;
	}
	
}
