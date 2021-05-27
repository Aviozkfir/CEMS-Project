package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Question;
import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

public class TeacherQuestionBankQuestionsController extends TeacherMainPageController {

    private ArrayList<Question> allQuestions;

    @FXML
    private AnchorPane myRoot;
    
    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private TextField searchBar;

    @FXML
    private Button btnSearch;

    @FXML
    private Text numberOfQuestions;

    @FXML
    private Button btnCreateQuestion;

    @FXML
    private VBox vTable;

    @FXML
    private Button btnBack;

    
    private Course course;

    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	TeacherQuestionBankCoursesController contr =(TeacherQuestionBankCoursesController) GUIControl.instance.loadStage("TeacherQuestionBankCourses.fxml");
		contr.setTeacherCourse(course.getSubject());
    }

    @FXML
    void btnCreateQuestionPressed(ActionEvent event) throws IOException {
    	((TeacherCreateQuestionController)GUIControl.instance.loadStage("/gui/TeacherCreateQuestion.fxml")).setPage(course);
    }

    @FXML
    void btnSearchPressed(ActionEvent event) {

    }
    
    public void setTeacherCourse(Course course) throws IOException {
    	
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.GET_QUESTION_BY_COURSE, course);
    	guiControl.sendToServer(m1);
    	
    	allQuestions = (ArrayList<Question>) guiControl.getServerMsg().getMessage();
    	
    	numberOfQuestions.setText(""+allQuestions.size());
    	
    	for(Question q : allQuestions)
    	{
    		TeacherQuestionTableRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherQuestionTableRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherQuestionTableRowController) fxmlLoader.getController();
			controller.setQuestion(q);
			vTable.getChildren().add(root);
			
    	}
    	
    }

}
