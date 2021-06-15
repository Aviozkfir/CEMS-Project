package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import entity.Course;
import entity.QuestionInExam;
import entity.SolvedExamToView;
import entity.SolvedQuestionToView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

/**
 * Used to view the solved exam.
 * 
 * @author Shalom and Omer
 * @extend StudentMainPageController
 *
 */
public class StudentExamViewController extends StudentMainPageController {
    @FXML
    private Text NotesForExam;
	/**
	 * Holds the index of the current question
	 */
	private int currentQuestion = 0;
	/**
	 * Used to hold the details for solved exam.
	 */
	private SolvedExamToView exam;
	/**
	 * Used to hold the details for course
	 */
	private Course course;
	/**
	 * Used to hold the questions of solved exam.
	 */
	private ArrayList<SolvedQuestionToView> questions;

	/**
	 * Used to hold notes of teacher for each question.
	 */
	@FXML
	private Text NotesForStudent;
	/**
	 * Used to go back to exams table.
	 */
	@FXML
	private Button BackToMyExamsButton;

	/**
	 * Used to show name of exam
	 */
	@FXML
	private Text NameOfExam2;

	/**
	 * Used to show number of question
	 */
	@FXML
	private Text QuestionNumberTitle;

	/**
	 * Used to show content of question
	 */
	@FXML
	private Text ContentOfQuestion;

	/**
	 * Used to show the correct answer option
	 */
	@FXML
	private Text CorrectAnswerText;

	/**
	 * Used as a radio button for answer option
	 */
	@FXML
	private RadioButton OptionA;

	/**
	 * Used to show Option 1 text.
	 */
	@FXML
	private Text OptionAText;

	/**
	 * Used as a radio button for answer option
	 */
	@FXML
	private RadioButton OptionB;

	/**
	 * Used to show Option 2 text.
	 */
	@FXML
	private Text OptionBText;

	/**
	 * Used as a radio button for answer option
	 */
	@FXML
	private RadioButton OptionC;

	/**
	 * Used to show Option 3 text.
	 */
	@FXML
	private Text OptionCText;

	/**
	 * Used as a radio button for answer option
	 */
	@FXML
	private RadioButton OptionD;

	/**
	 * Used to show Option 4 text.
	 */
	@FXML
	private Text OptionDText;

	/**
	 * Used to go to previous question when pressed
	 */
	@FXML
	private Button PreviousQuestionButton;

	/**
	 * Used to go to next question when pressed
	 */
	@FXML
	private Button NextQuestionButton;

	/**
	 * Used to go to next question and sets all the details of next question
	 * 
	 * @param ActionEvent event
	 */
	@FXML
	void NextQuestionButtonPressed(ActionEvent event) {
		try {
			if (currentQuestion < questions.size() - 1) {
				currentQuestion++;
				setQuestion(currentQuestion);
			}

			else {
				currentQuestion = questions.size() - 1;
				throw new IndexOutOfBoundsException();
			}

		} catch (IndexOutOfBoundsException e) {
			guiControl.popUpMessage("System Error", "This is the last question");

		}

	}

	/**
	 * Used to go to previous question and sets all the details of previous question
	 * 
	 * @param ActionEvent event
	 */
	@FXML
	void PreviousQuestionButtonPressed(ActionEvent event) {
		try {

			if (currentQuestion > 0) {
				currentQuestion--;
				setQuestion(currentQuestion);
			} else {
				currentQuestion = 0;
				throw new IndexOutOfBoundsException();
			}

		} catch (IndexOutOfBoundsException e) {
			guiControl.popUpMessage("System Error", "This is the first question");
		}

	}

	/**
	 * returns current exam
	 * 
	 * @return SolvedExamToView exam
	 */
	public SolvedExamToView getExam() {
		return exam;
	}

	/**
	 * A button when pressed goes back to table of exams.
	 * 
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void BackToMyExamsButtonPressed(ActionEvent event) throws IOException {
		StudentCourseExamsController contr = (StudentCourseExamsController) GUIControl.instance
				.loadStage("myExamsResults.fxml");
		contr.setStudentExams(course);

	}

	/**
	 * Used to set questions details and the answer the student chose and correct
	 * answer.
	 * 
	 * @param int num
	 */
	public void setQuestion(int num) {
		NotesForExam.setText("Exam notes: "+exam.getNotesForExam());
		SolvedQuestionToView que = questions.get(num);
		ContentOfQuestion.setText(que.getQuestionText());
		OptionAText.setText(que.getAnsA());
		OptionBText.setText(que.getAnsB());
		OptionCText.setText(que.getAnsC());
		OptionDText.setText(que.getAnsD());
		NotesForStudent.setText("Notes for Student: "+que.getNotesForStudent());
		QuestionNumberTitle.setText("Question: " + que.getQuestionNum());
		NameOfExam2.setText(exam.getExamName());
		CorrectAnswerText.setText("Correct Answer is: " + que.getCorrectAns());
		if (que.getChosenAnswer() == 1) {
			OptionA.setDisable(true);
			OptionA.setSelected(true);
			OptionB.setDisable(true);
			OptionC.setDisable(true);
			OptionD.setDisable(true);
			OptionB.setSelected(false);
			OptionC.setSelected(false);
			OptionD.setSelected(false);
		} else if (que.getChosenAnswer() == 2) {
			OptionA.setDisable(true);
			OptionB.setSelected(true);
			OptionB.setDisable(true);
			OptionC.setDisable(true);
			OptionD.setDisable(true);
			OptionA.setSelected(false);
			OptionC.setSelected(false);
			OptionD.setSelected(false);
		} else if (que.getChosenAnswer() == 3) {
			OptionA.setDisable(true);
			OptionC.setSelected(true);
			OptionB.setDisable(true);
			OptionC.setDisable(true);
			OptionD.setDisable(true);
			OptionA.setSelected(false);
			OptionB.setSelected(false);
			OptionD.setSelected(false);
		} else if (que.getChosenAnswer() == 4) {
			OptionA.setDisable(true);
			OptionD.setSelected(true);
			OptionB.setDisable(true);
			OptionC.setDisable(true);
			OptionD.setDisable(true);
			OptionA.setSelected(false);
			OptionB.setSelected(false);
			OptionC.setSelected(false);
		} else if (que.getChosenAnswer() == -1) {
			OptionA.setDisable(true);
			OptionB.setDisable(true);
			OptionC.setDisable(true);
			OptionD.setDisable(true);
			OptionA.setSelected(false);
			OptionB.setSelected(false);
			OptionC.setSelected(false);
			OptionD.setSelected(false);
		}

		else {

		}
	}

	/**
	 * Sets the relevant exam
	 * 
	 * @param SolvedExamToView exam
	 */
	public void setExam(SolvedExamToView exam) {
		this.exam = exam;
	}

	/**
	 * sets the array of questions
	 * 
	 * @param ArrayList<SolvedQuestionToView> questions
	 */
	public void setQuestions(ArrayList<SolvedQuestionToView> questions) {
		this.questions = questions;
	}

	/**
	 * sets the course
	 * 
	 * @param course
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

}
