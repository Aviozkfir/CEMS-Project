package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    	
    	//coverting fate from yyyy-mm-dd to dd-mm-yyyy
    	String startDateString = q.getModified().toString();
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	//
    	date.setText(LocalDate.parse(startDateString, oldFormat).format(newFormat));
    }
    
    public void setList(ArrayList<Question> l) {
    	selectedQuestionList=l;
    	if(l==null)
    		System.out.print("nononon\n\n\n\n");
    }

}
