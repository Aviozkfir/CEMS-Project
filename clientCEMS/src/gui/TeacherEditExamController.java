package gui;

import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherEditExamController extends TeacherMainPageController{

	private Course course;

	private Exam exam = new Exam();
	
	private ArrayList<Question> myQuestions = new ArrayList<Question>();
    
    @FXML
    private VBox myVbox;

    @FXML
    private Text Name;

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text eNum;
    
    

   

}
