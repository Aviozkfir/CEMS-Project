package gui;

import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TeacherExamBankSelectQuestionPointsRowController {

    @FXML
    private Button btnView;

	@FXML
    private Label questionID;

    @FXML
    private Label questionText;

    @FXML
    private TextField points;

    public String getPoints() {
		return points.getText();
	}
    
    
    private Question question;
    
    
    public void setQuestion(Question q) {
    	question=q;
    	questionID.setText(q.getId());
    	questionText.setText(q.getText());
    }
    
    @FXML
    void btnViewQuestionPressed(ActionEvent event) {

    }

}
