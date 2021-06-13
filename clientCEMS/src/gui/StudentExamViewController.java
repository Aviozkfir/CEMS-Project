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

public class StudentExamViewController extends StudentMainPageController  {
	private int currentQuestion=0;
	private SolvedExamToView exam;
	private Course course;
	private ArrayList<SolvedQuestionToView> questions;

	@FXML
	private Text NotesForStudent;
	@FXML
	private Button BackToMyExamsButton;

	@FXML
	private Text NameOfExam2;

	@FXML
	private Text QuestionNumberTitle;

	@FXML
	private Text ContentOfQuestion;

	@FXML
	private Text CorrectAnswerText;

	@FXML
	private RadioButton OptionA;

	@FXML
	private Text OptionAText;

	@FXML
	private RadioButton OptionB;

	@FXML
	private Text OptionBText;

	@FXML
	private RadioButton OptionC;

	@FXML
	private Text OptionCText;

	@FXML
	private RadioButton OptionD;

	@FXML
	private Text OptionDText;

	@FXML
	private Button PreviousQuestionButton;

	@FXML
	private Button NextQuestionButton;

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



	public SolvedExamToView getExam() {
		return exam;
	}

	@FXML
	void BackToMyExamsButtonPressed(ActionEvent event) throws IOException {
		StudentCourseExamsController contr = (StudentCourseExamsController) GUIControl.instance
				.loadStage("myExamsResults.fxml");
		contr.setStudentExams(course);

	}

	public void setQuestion(int num) {

		SolvedQuestionToView que = questions.get(num);
		ContentOfQuestion.setText(que.getQuestionText());
		OptionAText.setText(que.getAnsA());
		OptionBText.setText(que.getAnsB());
		OptionCText.setText(que.getAnsC());
		OptionDText.setText(que.getAnsD());
		NotesForStudent.setText(que.getNotesForStudent());
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

	public void setExam(SolvedExamToView exam) {
		this.exam = exam;
	}

	public void setQuestions(ArrayList<SolvedQuestionToView> questions) {
		this.questions = questions;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	

}
