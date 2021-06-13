
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

/**
 * @author Shalom and Omer
 * 
 *         Used to present an exam in course as a row in table
 *
 */
public class StudentExamTableRowController {
	/**
	 * Holds the exam details
	 */
	public SolvedExamToView exam;

	/**
	 * Holds the questions of the exam
	 */
	public ArrayList<SolvedQuestionToView> questions;
	/**
	 * used to hold the current course
	 */
	public Course course;

	/**
	 * A button in order to view the exam details when pressed.
	 */
	@FXML
	private Button btnView;

	/**
	 * Holds the image of the view button
	 */
	@FXML
	private ImageView watch;

	/**
	 * Holds the number of exam (ID)
	 */
	@FXML
	private Label ExamID;

	/**
	 * Holds the description for the exam
	 */
	@FXML
	private Label ExamTitle;

	/**
	 * Holds the date of submission
	 */
	@FXML
	private Label ExecutionDate;

	/**
	 * Holds the final grade of exam
	 */
	@FXML
	private Label Grade;

	/**
	 * Shows all the exam questions when pressed
	 * 
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void btnViewExamPressed(ActionEvent event) throws IOException {
		GUIControl guiControl = GUIControl.getInstance();
		StudentExamViewController a = (StudentExamViewController) guiControl
				.loadStage(gui.ClientsConstants.Screens.STUDENT_EXAM_VIEW.path);
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

	/**
	 * Sets the exam and its course in order to take details to show.
	 * 
	 * @param exam
	 * @param course
	 */
	public void setExam(SolvedExamToView exam, Course course) {
		this.exam = exam;
		this.course = course;
		Grade.setText(exam.getGrade());
		ExecutionDate.setText(exam.getFinishDate());
		ExamID.setText(exam.getEid());
		ExamTitle.setText(exam.getExamName());
	}

	/**
	 * Used to take all questions of certain exam.
	 * 
	 * @param SolvedExamToView exam
	 */
	public void setQuestions(SolvedExamToView exam) {
		ClientMessage m = new ClientMessage(ClientMessageType.GET_QUESTIONS_FOR_SOLVED_EXAM, exam.getSEid());
		GUIControl guiControl = GUIControl.getInstance();
		guiControl.sendToServer(m);
		questions = (ArrayList<SolvedQuestionToView>) guiControl.getServerMsg().getMessage();

	}

	/**
	 * used to return all questions of exam
	 * 
	 * @return questions
	 */
	public ArrayList<SolvedQuestionToView> getQuestions() {
		return questions;

	}

}