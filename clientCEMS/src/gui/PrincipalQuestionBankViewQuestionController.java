package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class PrincipalQuestionBankViewQuestionController extends PrincipalMainPageController{
	
	private Question question;
	
	private Course course;
	
	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;
	
	@FXML
	private Text questionName;
	
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
		PrincipalQuestionBankQuestionsController contr = (PrincipalQuestionBankQuestionsController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAL_QUESTION_BANK_QUESTION.path);
		contr.setPrincipalCourse(course);
		contr.setRequestCounter();
	}
	
	public void setPrincipalQuestion(Question question,Course course) throws IOException {

		this.question = question;
		this.course = course;
		
		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());
		questionName.setText(question.getId());
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
