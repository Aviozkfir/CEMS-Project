package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Question;
import entity.Teacher;
import entity.TeachersExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * TeacherEditQuestionController - controller of the page that handls the edit 
 * of a question in a relevant question bank
 * @author Sharo
 *
 */
public class TeacherEditQuestionController extends TeacherMainPageController{

	private ArrayList<Course> list= new ArrayList<Course>();
 	private Question question = new Question();
 	
	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;

    @FXML
    private Text qNum;

    @FXML
    private TextArea textQuestion;

    @FXML
    private RadioButton rbA;

    @FXML
    private ToggleGroup rbGroup;

    @FXML
    private TextArea textA;

    @FXML
    private RadioButton rbB;

    @FXML
    private TextArea textB;

    @FXML
    private RadioButton rbC;

    @FXML
    private TextArea textC;

    @FXML
    private RadioButton rbD;

    @FXML
    private TextArea textD;

    @FXML
    private MenuButton btnCourseList;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnBack;

    private Course course;
    
    /**
     * setTeacherQuestion
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
		textA.setText(question.getAnsA());
		textB.setText(question.getAnsB());
		textC.setText(question.getAnsC());
		textD.setText(question.getAnsD());
		setCorrectAns();
		
		for(Course cour : ((Teacher)guiControl.getUser()).getCourseList())
	    	if((!cour.equals(course))&&cour.getSubject().equals(course.getSubject())){
	    	CheckMenuItem a = new CheckMenuItem(cour.getName());
	    	a.setOnAction(e->{
	    		
	    		Course c= cour;
	    		if (((CheckMenuItem)e.getSource()).isSelected())
	    			list.add(c);
	    		else
	    			list.remove(c);
	    	});
	    	
	    	btnCourseList.getItems().add(a);
	    	}
	    	list.add(course);

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
	
    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(course);
    }

    @FXML
    void SavePressed(ActionEvent event) throws IOException {
    	if(textQuestion.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Question text wasn't inserted.");
    		return;
    	}
    	if(textA.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer A wasn't inserted.");
    		return;
    	}
    	if(textB.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer B wasn't inserted.");
    		return;
    	}
    	if(textC.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer C wasn't inserted.");
    		return;
    	}
    	if(textD.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Answer D wasn't inserted.");
    		return;
    	}
    	if(rbA.isSelected()) {
    		question.setCorrectAnswer(1);
    	}else if(rbB.isSelected()) {
    		question.setCorrectAnswer(2);
    	}else if(rbC.isSelected()) {
    		question.setCorrectAnswer(3);
    	}else { 
    		question.setCorrectAnswer(4);
    	}
    	
    	question.setText(textQuestion.getText());
    	question.setAnsA(textA.getText());
    	question.setAnsB(textB.getText());
    	question.setAnsC(textC.getText());
    	question.setAnsD(textD.getText());
    	question.setAuthor(((Teacher)guiControl.getUser()).getId());
    	question.setModified();
    	
    	Object[] arr = new Object[2];
    	arr[0]=question;
    	arr[1]=list;
    	SaveEditedQuestion(arr);
    	
    }
    
    public void SaveEditedQuestion(Object[] arr ) throws IOException{
		ClientMessage msg = new ClientMessage(ClientMessageType.TEACHER_EDIT_QUESTION,(arr));

		guiControl.sendToServer(msg);

		if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_EDIT_QUESTION_ADDED) {
			GUIControl.popUpMessage("Question was edited successfully");
			((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage("/gui/TeacherQuestionBankQuestions.fxml")).setTeacherCourse(course);
		}
 }

}
