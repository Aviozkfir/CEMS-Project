package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Question;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class TeacherCreateQuestionController extends TeacherMainPageController {

	 	private Course course;
	 	
	 	private ArrayList<Course> list= new ArrayList<Course>();
	 	
	 	private Question question = new Question();
	 	@FXML
	    private Button LogOutButton;

	    @FXML
	    private Text subjectName;

	    @FXML
	    private Text courseName;

	    @FXML
	    private TextArea textQuestion;

	    @FXML
	    private RadioButton rbA;

	    @FXML
	    private ToggleGroup rbGroup;

	    @FXML
	    private TextArea textA;

	    @FXML
	    private RadioButton rbB;

	    @FXML
	    private TextArea textB;

	    @FXML
	    private RadioButton rbC;

	    @FXML
	    private TextArea textC;

	    @FXML
	    private RadioButton rbD;

	    @FXML
	    private TextArea textD;

	    @FXML
	    private Button btnSave;

	    @FXML
	    private Button btnBack;
	    
	    
	    @FXML
	    private MenuButton btnCourseList;

	    @FXML
	    void BackPressed(ActionEvent event) throws IOException {
	    	((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(course);
	    }

	    @FXML
	    void SavePressed(ActionEvent event) {
	    	
	    	
	    	if(textQuestion.getText().trim().equals("")) {
	    		GUIControl.popUpMessage("Error", "Question text wasn't inserted.");
	    		return;
	    	}
	    	if(textA.getText().trim().equals("")) {
	    		GUIControl.popUpMessage("Error", "Answer A wasn't inserted.");
	    		return;
	    	}
	    	if(textB.getText().trim().equals("")) {
	    		GUIControl.popUpMessage("Error", "Answer B wasn't inserted.");
	    		return;
	    	}
	    	if(textC.getText().trim().equals("")) {
	    		GUIControl.popUpMessage("Error", "Answer C wasn't inserted.");
	    		return;
	    	}
	    	if(textD.getText().trim().equals("")) {
	    		GUIControl.popUpMessage("Error", "Answer D wasn't inserted.");
	    		return;
	    	}
	    	
	    	if(rbA.isSelected()) {
	    		question.setCorrectAnswer(1);
	    	}else if(rbB.isSelected()) {
	    		question.setCorrectAnswer(2);
	    	}else if(rbC.isSelected()) {
	    		question.setCorrectAnswer(3);
	    	}else if(rbD.isSelected()) {
	    		question.setCorrectAnswer(4);
	    	}else {
	    		GUIControl.popUpMessage("Error", "Correct answer wasn't selected.");
	    		return;
	    	}
	    	
	    	question.setText(textQuestion.getText());
	    	question.setAnsA(textA.getText());
	    	question.setAnsB(textB.getText());
	    	question.setAnsC(textC.getText());
	    	question.setAnsD(textD.getText());
	    	
	    	question.setAuthor(((Teacher)guiControl.getUser()).getId());
	    	question.setModified();
	    	Object[] arr = new Object[2];
	    	arr[0]=question;
	    	arr[1]=list;
	    	
	    	
	    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_ADD_QUESTION, arr);
	    	guiControl.sendToServer(m1);
	    	
	    	if(guiControl.getServerMsg().getType()==ServerMessageTypes.QUESTION_IS_ALREADY_IN_DATABASE) {
	    		GUIControl.popUpMessage("Error", "The question already exists in" + ((String)guiControl.getServerMsg().getMessage())+" question bank." );
	    		return; 
	    	}
	    	else if(guiControl.getServerMsg().getType()==ServerMessageTypes.QUESTION_ADDED) {
	    		GUIControl.popUpMessage("Success", "The question was Created.");
	    		return; 
	    	}if(guiControl.getServerMsg().getType()==ServerMessageTypes.QUESTION_NOT_ADDED) {
	    		GUIControl.popUpMessage("Error", "Could not create question.");
	    		return; 
	    	}  
	    	
	    	//SELECT MAX(q.Qid) FROM Questions q where q.Sid=01

	    }
    
    public void setPage(Course course) {
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    	for(Course cour : ((Teacher)guiControl.getUser()).getCourseList())
    	if((!cour.equals(course))&&cour.getSubject().equals(course.getSubject())){
    	CheckMenuItem a = new CheckMenuItem(cour.getName());
    	a.setOnAction(e->{
    		
    		Course c= cour;
    		if (((CheckMenuItem)e.getSource()).isSelected())
    			list.add(c);
    		else
    			list.remove(c);
    	});
    	
    	btnCourseList.getItems().add(a);
    	}
    	list.add(course);
    }

}
