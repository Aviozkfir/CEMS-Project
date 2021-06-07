package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TeacherCreateExamPage1Controller {

    @FXML
    private TextField examTitle;

    @FXML
    private TextField examDuration;

    @FXML
    private TextField points;

    @FXML
    private TextField examCode;

    @FXML
    private TextArea studentNotesText;

    @FXML
    private TextArea teacherNotesText;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnNext;
    
    private Course course;
    
    private VBox myVbox;
    
    private AnchorPane page2;

    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
	}
    
    public void setPage2(AnchorPane page2) {
		this.page2 = page2;
	}

	public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	((TeacherExamBankExamsController)GUIControl.instance.loadStage("/gui/TeacherExamBankExams.fxml")).setTeacherCourse(course);
    }

    @FXML
    void btnNextPressed(ActionEvent event) {
    	try {
    	if((Integer.parseInt(examDuration.getText())<=0))
    	{
    		GUIControl.popUpMessage("Error", "Must enter a positive number of minutes.");
    		return;
    	}
    	}catch(NumberFormatException e) {
    		
    		GUIControl.popUpMessage("Error", "Invalid exam duartion input.");
    		return;
    		
    	}
    	exam.setTotalTime(examDuration.getText());
    	
    	if(examTitle.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer B wasn't inserted.");
    		return;
    	}
    	exam.setName(examTitle.getText());
    	
    	
    	if(examCode.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer B wasn't inserted.");
    		return;
    	}
    	exam.setCode(examCode.getText());
    	
    	if(teacherNotesText.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer B wasn't inserted.");
    		return;
    	}
    	exam.setTdescription(teacherNotesText.getText());
    	
    	exam.setSdescription(studentNotesText.getText());
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page2);
    	
    }

}
