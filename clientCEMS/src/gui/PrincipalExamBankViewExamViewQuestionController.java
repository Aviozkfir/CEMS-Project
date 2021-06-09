package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class PrincipalExamBankViewExamViewQuestionController extends PrincipalMainPageController{

	
    private Question question;
	
	private Course course;
	
	private Exam exam;
	
	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;
	
	@FXML
	private Text questionName;
	
	@FXML
	private Text examID;
	
	@FXML
	private Text textQuestion;
	
	@FXML
	private RadioButton rbA;
	
	@FXML
	private Text textA;
	
	@FXML
	private RadioButton rbB;
	
	@FXML
	private Text textB;
	
	@FXML
	private RadioButton rbC;
	
	@FXML
	private Text textC;
	
	@FXML
	private RadioButton rbD;
	
	@FXML
	private Text textD;
	
	@FXML
	private Button Back;
	
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankViewExamController contr = (PrincipalExamBankViewExamController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_VIEW.path);
		contr.setPrincipalExam(exam,course);
		contr.setRequestCounter();
	}
	
	public void setPrincipalQuestion(Question question,Course course, Exam exam) throws IOException {

		this.question = question;
		this.course = course;
		this.exam = exam;
		
		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());
		questionName.setText(question.getId());
		examID.setText(exam.getEid());
		textQuestion.setText(question.getText());
		textA.setText(question.getAnsA());
		textB.setText(question.getAnsB());
		textC.setText(question.getAnsC());
		textD.setText(question.getAnsD());
		setCorrectAns();
	}

	private void setCorrectAns() {
		int correct = question.getCorrectAnswer();
		switch(correct) {
		
		case 1: rbA.setSelected(true);
			break;
		
		case 2:rbB.setSelected(true);
			break;
		
		case 3:rbC.setSelected(true);
			break;
		
		case 4:rbD.setSelected(true);
			break;
		}
		
		
	}	

}
