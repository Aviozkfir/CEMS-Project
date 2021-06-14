package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import message.ClientMessage;
import message.ClientMessageType;

/**
 * Controller for the row that represents exams in the exam bank
 * 
 * @author On Sharon, Guy 
 *
 */
public class TeacherExamBankRowController{

	/**
	 * button in order to view question
	 */
    @FXML
    private Button btnView;

    /**
	 * represents question ID
	 */
    @FXML
    private Label questionID;

    /**
   	 * represents exam title
   	 */
    @FXML
    private Label examTitle;

    /**
   	 * date-represents the date of the exam creation
   	 */
    @FXML
    private Label date;

    /**
   	 * author-represents the author of the exam
   	 */
    @FXML
    private Label author;

    /**
   	 * btnPublish-in order to publish the exam to be viable to students
   	 */
    @FXML
    private Button btnPublish;

    /**
   	 * exam- entity of the exam
   	 */
    private Exam exam;
    
    /**
   	 * course- entity of the course
   	 */
    private Course course;
    
    /**
   	 * setExam- setting the exams properties
   	 */
    public void setExam(Exam exam,Course course) {
		this.exam = exam;
		this.course= course;
		author.setText(exam.getID());
		date.setText(exam.getDate());
		examTitle.setText(exam.getName());
		questionID.setText(exam.getEid());
	}

    /**
   	 * btnPublishPressed- pulishe the exam to all students
   	 */
    @FXML
    void btnPublishPressed(ActionEvent event) {
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_PUBLISH_EXAM, exam);
    	GUIControl.instance.sendToServer(m1);
    }

    /**
   	 * btnViewQuestionPressed- opens the question with all of it's information
   	 */
    @FXML
    void btnViewQuestionPressed(ActionEvent event) throws IOException {
    	GUIControl guiControl = GUIControl.getInstance();
		TeacherExamBankViewExamController a = (TeacherExamBankViewExamController) guiControl
				.loadStage(gui.ClientsConstants.Screens.TEACHER_EXAM_VIEW.path);
		a.setTeacherExam(exam, course);
    }

}
