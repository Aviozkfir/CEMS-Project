package gui;

import java.io.IOException;

import entity.Course;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherCreateExamController extends TeacherMainPageController{

	private Course course;

    @FXML
    private VBox myVbox;

   
    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    private AnchorPane page1;
    private AnchorPane page2;
    private AnchorPane page3;
    
    private TeacherCreateExamPage1Controller page1Control;
    private TeacherCreateExamPage2Controller page2cortrol;
   
    public void setTeacherCreateExam(Course course) throws IOException {
    	this.course=course;
    	subjectName.setText(course.getSubject().getName());
    	courseName.setText(course.getName());
    	
    	FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam1.fxml"));
		page1 = fxmlLoader1.load();
		page1Control = (TeacherCreateExamPage1Controller) fxmlLoader1.getController();
		
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam2.fxml"));
		page2 = fxmlLoader2.load();
		page2cortrol = (TeacherCreateExamPage2Controller) fxmlLoader2.getController();
		
		page1Control.setCourse(course);
		page1Control.setMyVbox(myVbox);
		page1Control.setPage2(page2);
		page2cortrol.setCourse(course);
		page2cortrol.setMyVbox(myVbox);
		page2cortrol.setPage1(page1);
		
		myVbox.getChildren().add(page1);
		
    }
    

}
