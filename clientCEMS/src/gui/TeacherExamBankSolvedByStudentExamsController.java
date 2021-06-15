package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.SolvedExamType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

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
    	((TeacherExamBankSolvedByStudentCoursesController) GUIControl.instance.loadStage("/gui/TeacherExamBankSolvedByStudentCourses.fxml")).setTeacherCourse(course.getSubject());
    	
    }

    
    public void setTeacherCourse(Course course) throws IOException {
    	
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEAHCER_GET_SOLVED_EXAMS_BY_COURSE, course);
    	guiControl.sendToServer(m1);
    	
    	ArrayList<SolvedExamType> exams = (ArrayList<SolvedExamType>) guiControl.getServerMsg().getMessage();
    	
    	numberOfExams.setText(""+exams.size());
    	
    	for(SolvedExamType se : exams)
    	{
    		TeacherExamBankSolvedByStudentExamsRowsController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSolvedByStudentExamsRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherExamBankSolvedByStudentExamsRowsController) fxmlLoader.getController();
			se.setCourse(course);
			controller.setExam(se);
			vTable.getChildren().add(root);
			
    	}
    	
    }
}
