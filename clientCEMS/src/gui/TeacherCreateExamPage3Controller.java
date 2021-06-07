package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherCreateExamPage3Controller {

	private Course course;
    
    private VBox myVbox;
	@FXML
    private VBox vTable;

    @FXML
    private Text examTitle;

    @FXML
    private Text time;

    @FXML
    private Text points;

    @FXML
    private Text studentNotes;

    @FXML
    private Text teacherNotes;

    @FXML
    private Button btnPublish;

    
    private AnchorPane page2;
    private AnchorPane page4;
    
private ArrayList<Question> myQuestions;
    
    public void setMyQuestions(ArrayList<Question> myQuestions) {
		this.myQuestions = myQuestions;
	}
    
 
    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
	}
    
    public void setPage2(AnchorPane page2) {
		this.page2 = page2;
	}
    
    public void setPage4(AnchorPane page4) {
  		this.page4 = page4;
  	}
    
    public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}
    
    public void setCourse(Course course) {
		this.course = course;
	}

    @FXML
    void btnBackPressed(ActionEvent event) {
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page2);
    }

    @FXML
    void btnPublishPressed(ActionEvent event) {
    	
    	
    	
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page4);
    }

    public void PrintSelectedQuestions() throws IOException {
    	for(Question q : myQuestions)
    	{
    		TeacherExamBankSelectQuestionPointsRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSelectQuestionPointsRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherExamBankSelectQuestionPointsRowController) fxmlLoader.getController();
			controller.setQuestion(q);
			vTable.getChildren().add(root);
    	}
    }
}
