
package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import entity.Course;
import entity.Exam;
import entity.QuestionInExam;
import entity.SolvedExamToView;
import entity.SolvedQuestionToView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import message.ClientMessage;
import message.ClientMessageType;

public class StudentExamTableRowController {
	public  SolvedExamToView exam;
	public  ArrayList<SolvedQuestionToView> questions;
	public Course course;

	@FXML
	private Button btnView;

	@FXML
	private ImageView watch;

	@FXML
	private Label ExamID;

	@FXML
	private Label ExamTitle;

	@FXML
	private Label ExecutionDate;

	@FXML
	private Label Grade;

	@FXML
	void btnViewExamPressed(ActionEvent event) throws IOException {
		GUIControl guiControl = GUIControl.getInstance();
		StudentExamViewController a = (StudentExamViewController) guiControl.loadStage(gui.ClientsConstants.Screens.STUDENT_EXAM_VIEW.path);
	    			a.setQuestions(questions);
	    			a.setExam(exam);
	    			a.setCourse(course);
	    			questions.sort(new Comparator<SolvedQuestionToView>() {
	    				@Override
	    				public int compare(SolvedQuestionToView o1, SolvedQuestionToView o2) {

	    					if (o1.getQuestionNum() == o2.getQuestionNum())
	    						return 0;
	    					else if (o1.getQuestionNum() < o2.getQuestionNum())
	    						return -1;
	    					else
	    						return 1;

	    				}

	    			});
	    			a.setQuestion(0);

	}

	public void setExam(SolvedExamToView exam, Course course) {
		this.exam = exam;
		this.course = course;
		Grade.setText(exam.getGrade());
		ExecutionDate.setText(exam.getFinishDate());
		ExamID.setText(exam.getEid());
		ExamTitle.setText(exam.getExamName());
	}

	public void setQuestions(SolvedExamToView exam) {
		ClientMessage m = new ClientMessage(ClientMessageType.GET_QUESTIONS_FOR_SOLVED_EXAM, exam.getSEid());
		GUIControl guiControl = GUIControl.getInstance();
		guiControl.sendToServer(m);
		questions = (ArrayList<SolvedQuestionToView>) guiControl.getServerMsg().getMessage();
		
		
		
		

	}

	public ArrayList<SolvedQuestionToView> getQuestions() {
		return questions;

	}

}