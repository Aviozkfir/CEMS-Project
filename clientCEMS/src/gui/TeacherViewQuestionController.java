package gui;

import java.io.IOException;

import entity.Course;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * Takes care of viewing quesition
 * @author Sharon and Guy
 *
 */
public class TeacherViewQuestionController extends TeacherCreateExamController{

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text qNum;

    @FXML
    private Text textQuestion;

    @FXML
    private RadioButton rbA;

    @FXML
    private ToggleGroup rbGroup;

    @FXML
    private Text ans1;

    @FXML
    private RadioButton rbB;

    @FXML
    private Text ans2;

    @FXML
    private RadioButton rbC;

    @FXML
    private Text ans3;

    @FXML
    private RadioButton rbD;

    @FXML
    private Text ans4;

    @FXML
    private Button btnBack;

    private Question question;
    private Course course;
    
	/**
	 * sets a question in table
	 * @param question
	 * @param course
	 * @throws IOException
	 */
	public void setTeacherQuestion(Question question, Course course) throws IOException {

		this.question = question;
		this.course = course;
		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());
		qNum.setText("#"+question.getId());
		textQuestion.setText(question.getText());
		ans1.setText(question.getAnsA());
		ans2.setText(question.getAnsB());
		ans3.setText(question.getAnsC());
		ans4.setText(question.getAnsD());
		setCorrectAns();

	}
	
	/**
	 * This method setting the correct answer in the question.
	 */
	private void setCorrectAns() {
		int correct = question.getCorrectAnswer();
		switch (correct) {
		case 1:
			rbA.setSelected(true);
			break;
		case 2:
			rbB.setSelected(true);
			break;

		case 3:
			rbC.setSelected(true);
			break;

		case 4:
			rbD.setSelected(true);
			break;
		}
	}
    /**
     * loads previous page when pressed
     * @param event
     * @throws IOException
     */
    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(course);
    }
}
