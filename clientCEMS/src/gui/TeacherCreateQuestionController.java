package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class TeacherCreateQuestionController extends TeacherMainPageController {

	 	private Course course;
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
	    void BackPressed(ActionEvent event) throws IOException {
	    	((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(course);
	    }

	    @FXML
	    void SavePressed(ActionEvent event) {

	    }
    
    public void setPage(Course course) {
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    }

}
