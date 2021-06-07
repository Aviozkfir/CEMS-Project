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
    private AnchorPane page4;
    
    private TeacherCreateExamPage1Controller page1Control;
    private TeacherCreateExamPage2Controller page2cortrol;
    private TeacherCreateExamPage3Controller page3cortrol;
    private TeacherCreateExamPage4Controller page4cortrol;
    
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
		
		FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam3.fxml"));
		page3 = fxmlLoader3.load();
		page3cortrol = (TeacherCreateExamPage3Controller) fxmlLoader3.getController();
		
		FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam4.fxml"));
		page4 = fxmlLoader4.load();
		page4cortrol = (TeacherCreateExamPage4Controller) fxmlLoader4.getController();
		
		page1Control.setCourse(course);
		page1Control.setMyVbox(myVbox);
		page1Control.setPage2(page2);
		
		page2cortrol.setCourse(course);
		page2cortrol.setMyVbox(myVbox);
		page2cortrol.setPage1(page1);
		page2cortrol.setPage3(page3);
		
		
		page3cortrol.setCourse(course);
		page3cortrol.setMyVbox(myVbox);
		page3cortrol.setPage2(page1);
		page3cortrol.setPage4(page4);
		
		page4cortrol.setCourse(course);
		page4cortrol.setMyVbox(myVbox);
		
		myVbox.getChildren().add(page1);
		
    }
    

}
