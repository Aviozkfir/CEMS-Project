package gui;

import java.util.ArrayList;

import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class TeacherExamBankSelectQuestionsRowController {

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label questionText;

    @FXML
    private Label date;

    @FXML
    private CheckBox checkBox;
    
    private Question question;
    
    private ArrayList<Question> selectedQuestionList;
    
    private Text numSelected;

    public void setNumSelected(Text numSelected) {
		this.numSelected = numSelected;
	}

	@FXML
    void btnViewQuestionPressed(ActionEvent event) {
    	
    }

    @FXML
    void checkBoxPressed(ActionEvent event) {

    	if(checkBox.isSelected()){
    		selectedQuestionList.add(question);
    	}
    	else
    	{
    		selectedQuestionList.remove(question);
    	}
    	numSelected.setText(""+selectedQuestionList.size());
    }
    
    public void setQuestion(Question q) {
    	question=q;
    	questionID.setText(q.getId());
    	questionText.setText(q.getText());
    	date.setText(q.getModified());
    }
    
    public void setList(ArrayList<Question> l) {
    	selectedQuestionList=l;
    	if(l==null)
    		System.out.print("nononon\n\n\n\n");
    }

}
