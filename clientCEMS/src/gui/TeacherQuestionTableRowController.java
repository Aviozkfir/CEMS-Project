package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class TeacherQuestionTableRowController {
	
	
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

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;
    
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
    void btnDeletePressed(ActionEvent event) throws IOException {
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_DELETE_QUESTION, q);
    	GUIControl.getInstance().sendToServer(m1);
    	if(GUIControl.getInstance().getServerMsg().getType()==ServerMessageTypes.QUESTION_DELETED) {
    		GUIControl.popUpMessage("Success", "The question was deleted.");
    		
    		((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(c);
    		return; 
    	}if(GUIControl.getInstance().getServerMsg().getType()==ServerMessageTypes.QUESTION_NOT_DELETED) {
    		GUIControl.popUpMessage("Error", "Could not delete question, pleasr try again.");
    		return; 
    	}if(GUIControl.getInstance().getServerMsg().getType()==ServerMessageTypes.QUESTION_NOT_DELETED_IN_USE) {
    		GUIControl.popUpMessage("Error", "Could not delete question, pleasr try again.");
    		return; 
    	}
    	
    }

    @FXML
    void btnEditPressed(ActionEvent event) {
    	
    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) {

    }

}
