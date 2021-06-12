package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * @author On Avioz
 *Controller for each question in the question list inside specific course.
 */
public class PrincipalQuestionTableRowController {

	/**
	 * The question.
	 */
	private Question question;
	
	/**
	 * The course of the question.
	 */
	private Course course;

    /**
     * Blue eye image button.
     */
    @FXML
    private Button btnView;

    /**
     * The question id.
     */
    @FXML
    private Label questionID;

    /**
     * The question text.
     */
    @FXML
    private Label questionText;

    /**
     * Date of question.
     */
    @FXML
    private Label date;

    
    /**
     * @param q The question the user chose.
     * This method setting the information of the question.
     */
    public void setQuestion(Question question) {
    	this.question=question;
    	questionText.setText(question.getText());
    	date.setText(question.getModified().toString());
    	questionID.setText(""+question.getId());
  
    }
    
    /**
     * @param course The course of the question the user chose.
     * This method setting the course of the question the user chose.
     */
    public void setCourse(Course course) {
    	this.course=course;
    }

    /**
     * @param event ActionEvent
     * @throws IOException
     * When blue eye image button pressed, the user will see the information of the question, setting text new request if necessary.
     */
    @FXML
    void btnViewQuestionPressed(ActionEvent event) throws IOException {
     GUIControl guiControl = GUIControl.getInstance();
    	PrincipalQuestionBankViewQuestionController a = (PrincipalQuestionBankViewQuestionController) guiControl.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_QUESTION_VIEW.path);
    			a.setPrincipalQuestion(question,course);
    			a.setRequestCounter();
    }
}
