package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

/**
 * @author Guy and Sharon
 *TeacherCreateExamPage2Controller-controller of the second page of creating exam(selection
 *of the wanted question for the exam).
 */
public class TeacherCreateExamPage2Controller {

	
	@FXML
    private Text numSelected;

    @FXML
    private Text numTotal;
    
    
    @FXML
    private VBox vTable;
    
    private Course course;
    
    private VBox myVbox;
    
    private AnchorPane page1;

    private Supplier<AnchorPane> setPage3;

    

	public void setPage3setter(Supplier<AnchorPane> setPage3) {
		this.setPage3 = setPage3;
	}

	private Exam exam;
    
    private ArrayList<Question> myQuestions;
    
    public void setMyQuestions(ArrayList<Question> myQuestions) {
		this.myQuestions = myQuestions;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public void setPage1(AnchorPane page1) {
		this.page1 = page1;
	}
    

	public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}

	public void setCourse(Course course) throws IOException {
		this.course = course;
		
		ClientMessage m1 = new ClientMessage(ClientMessageType.GET_QUESTION_BY_COURSE, course);
    	GUIControl.getInstance().sendToServer(m1);
    	
    	ArrayList<Question> allQuestions = (ArrayList<Question>) GUIControl.getInstance().getServerMsg().getMessage();
    	
    	numTotal.setText(""+allQuestions.size());
    	numSelected.setText("0");
    	
    	
    	for(Question q : allQuestions)
    	{
    		TeacherExamBankSelectQuestionsRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSelectQuestionsRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherExamBankSelectQuestionsRowController) fxmlLoader.getController();
			controller.setQuestion(q);
			controller.setList(myQuestions);
			controller.setNumSelected(numSelected);
			vTable.getChildren().add(root);
			
    	}
	}
    
    @FXML
    void btnBackPressed(ActionEvent event) {
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page1);
    }
    
    @FXML
    void btnNextPressed(ActionEvent event) {
    	if(myQuestions.size()==0) {
    		GUIControl.popUpMessage("Error", "Cannot create empty exam.");
    		return;
    	}
    	
    	
    
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(setPage3.get());
    	
    }

}
