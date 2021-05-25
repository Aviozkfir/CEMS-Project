package gui;

import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TeacherQuestionTableRowController {
	
	
	private Question q;

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label questionText;

    @FXML
    private Label date;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;
    
    public void setQuestion(Question q) {
    	this.q=q;
    	
    	
    }

    @FXML
    void btnDeletePressed(ActionEvent event) {

    }

    @FXML
    void btnEditPressed(ActionEvent event) {

    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) {

    }

}
