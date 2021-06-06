package gui;

import java.io.IOException;

import entity.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherExamBankSolvedByStudentExamsController  extends TeacherMainPageController{

    @FXML
    private AnchorPane myRoot;

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text numberOfExams;

    @FXML
    private VBox vTable;

    @FXML
    private Button btnBack;

    private Course course;

    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	((TeacherExamBankSolvedByStudentCoursesController) GUIControl.instance.loadStage("TeacherExamBankSolvedByStudentCourses.fxml")).setTeacherCourse(course.getSubject());
    }

    
public void setTeacherCourse(Course course) throws IOException {
    	
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
//    	ClientMessage m1 = new ClientMessage(ClientMessageType.GET_EXAM_BY_COURSE, course);
//    	guiControl.sendToServer(m1);
//    	
//    	allQuestions = (ArrayList<Exam>) guiControl.getServerMsg().getMessage();
//    	
//    	numberOfExams.setText(""+allQuestions.size());
//    	
//    	for(Exam q : allQuestions)
//    	{
//    		TeacherQuestionTableRowController controller;
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherQuestionTableRow.fxml"));
//			AnchorPane root = fxmlLoader.load();
//			controller = (TeacherQuestionTableRowController) fxmlLoader.getController();
//			controller.setQuestion(q);
//			controller.setCourse(course);
//			vTable.getChildren().add(root);
//			
//    	}
    	
    }
}
