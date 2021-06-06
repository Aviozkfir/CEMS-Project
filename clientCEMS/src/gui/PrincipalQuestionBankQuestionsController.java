package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class PrincipalQuestionBankQuestionsController extends PrincipalMainPageController {
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
    private VBox vTable;

    @FXML
    private Button Back;

    
    private Course course;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	PrincipalQuestionBankCoursesController contr =(PrincipalQuestionBankCoursesController) GUIControl.instance.loadStage
    			(gui.ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_COURSES.path);
		contr.setPrincipalCourse(course.getSubject());
		contr.setRequestCounter();
    }

    @FXML
    void btnSearchPressed(ActionEvent event) {

    }
    
    public void setPrincipalCourse(Course course) throws IOException {
    	
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.GET_QUESTION_BY_COURSE, course);
    	guiControl.sendToServer(m1);
    	
    	allQuestions = (ArrayList<Question>) guiControl.getServerMsg().getMessage();
    	
    	numberOfQuestions.setText(""+allQuestions.size());
    	
    	for(Question q : allQuestions)
    	{
    		PrincipalQuestionTableRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/PrincipalQuestionTableRow.fxml"));
			if (guiControl.getServerMsg().getType() == ServerMessageTypes.QUESTION_BY_COURSE_NOT_RECIVED) {
				GUIControl.popUpError("Error-getting Question list for principal.");
			} 
			AnchorPane root = fxmlLoader.load();
			controller = (PrincipalQuestionTableRowController) fxmlLoader.getController();
			controller.setQuestion(q);
			controller.setCourse(course);
			vTable.getChildren().add(root);

			
    	}
    	
    }

}
