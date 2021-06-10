package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class TeacherCreateExamPage1Controller {

    @FXML
    private TextField examTitle;

    @FXML
    private TextField examDuration;

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
    
    private ArrayList<Question> myQuestions;
    
    public void setMyQuestions(ArrayList<Question> myQuestions) {
		this.myQuestions = myQuestions;
	}
    
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
    	
    	if(examTitle.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Must enter exam title.");
    		return;
    	}
    	exam.setName(examTitle.getText());
    	
    	
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
    	if((Integer.parseInt(examDuration.getText())>3599)){
    		GUIControl.popUpMessage("Error", "Exam is too long");
    		return;
    	}
    	String h = (Integer.parseInt(examDuration.getText())/60)+"";
    	String min = (Integer.parseInt(examDuration.getText())%60)+"";
    	if(h.length()==1)
    		h="0"+h;
    	if(min.length()==1)
    		min="0"+min;
    	exam.setTotalTime(h+":"+min);
    	
    	
    	
    	if(!Pattern.compile("\\w{4}").matcher(examCode.getText()).matches()) {
    		GUIControl.popUpMessage("Error", "Must enter a code with 4 digits and letters.");
    		return;
    	}
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_CHECK_VALID_CODE, examCode.getText());
    	GUIControl.instance.sendToServer(m1);
    	
    	if(GUIControl.instance.getServerMsg().getType()==ServerMessageTypes.TEACHER_INVALID_CODE){
    		GUIControl.popUpMessage("Error", "This code is already being used in another exam");
    		return;
    	}
    	exam.setCode(examCode.getText());
    	
    	if(!teacherNotesText.getText().trim().equals("")) 
    		exam.setTdescription(teacherNotesText.getText());
    	else 
    		exam.setTdescription("");
    	
    	if(!studentNotesText.getText().trim().equals(""))
    		exam.setSdescription(studentNotesText.getText());
    	else
    		exam.setSdescription("");
    	
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page2);
    	
    }

}
