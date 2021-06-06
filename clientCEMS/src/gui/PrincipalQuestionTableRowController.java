package gui;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class PrincipalQuestionTableRowController {

	private Question q;
	
	private Course c;

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label questionText;

    @FXML
    private Label date;

    
    public void setQuestion(Question q) {
    	this.q=q;
    	questionText.setText(q.getText());
    	date.setText(q.getModified().toString());
    	questionID.setText(""+q.getId());
  
    }
    
    public void setCourse(Course c) {
    	this.c=c;
    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) {

    }
}
