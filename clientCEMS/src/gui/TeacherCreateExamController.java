package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import entity.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherCreateExamController extends TeacherMainPageController{

	private Course course;

	private Exam exam = new Exam();
	
	private ArrayList<Question> myQuestions = new ArrayList<Question>();
    
    
    @FXML
    private VBox myVbox;

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    private AnchorPane page1;
    private AnchorPane page2;
    
    private TeacherCreateExamPage1Controller page1Control;
    private TeacherCreateExamPage2Controller page2Control;
    
    
    private AnchorPane page3;
    private AnchorPane page4;
    
    private TeacherCreateExamPage3Controller page3Control;
    private TeacherCreateExamPage4Controller page4Control;
    
    public void setTeacherCreateExam(Course course) throws IOException {
    	this.course=course;
    	subjectName.setText(course.getSubject().getName());
    	courseName.setText(course.getName());
    	
    	
    	exam.setCid(course.getId());
    	exam.setSid(course.getSubject().getId());
    	exam.setID(((Teacher)guiControl.getUser()).getId());
    	exam.setMode("Manual");
    	
    	FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam1.fxml"));
		page1 = fxmlLoader1.load();
		page1Control = (TeacherCreateExamPage1Controller) fxmlLoader1.getController();
		
		FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam2.fxml"));
		page2 = fxmlLoader2.load();
		page2Control = (TeacherCreateExamPage2Controller) fxmlLoader2.getController();
		
		
		
		
		page1Control.setMyQuestions(myQuestions);
		page1Control.setCourse(course);
		page1Control.setMyVbox(myVbox);
		page1Control.setPage2(page2);
		page1Control.setExam(exam);
		
		page2Control.setMyQuestions(myQuestions);
		page2Control.setCourse(course);
		page2Control.setMyVbox(myVbox);
		page2Control.setPage1(page1);
		
		page2Control.setExam(exam);
		page2Control.setPage3setter(()->{
				
			try {
				set3Page();
				page3Control.PrintSelectedQuestions();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return page3;
		});
		myVbox.getChildren().add(page1);
		
		
    }
    
    //the page with the preview
    public void set3Page() throws IOException {
    	FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam3.fxml"));
		page3 = fxmlLoader3.load();
		page3Control = (TeacherCreateExamPage3Controller) fxmlLoader3.getController();
		
		//the page of sucsses
		FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("/gui/TeacherCreateExam4.fxml"));
		page4 = fxmlLoader4.load();
		page4Control = (TeacherCreateExamPage4Controller) fxmlLoader4.getController();
		
		page3Control.setMyQuestions(myQuestions);
		page3Control.setCourse(course);
		page3Control.setMyVbox(myVbox);
		page3Control.setPage2(page2);
		page3Control.setPage4(page4);
		page3Control.setExam(exam);
		page3Control.setDetailsReview();
		page3Control.setTeacherCreateExamPage4Controller(page4Control);
		
		page4Control.setCourse(course);
		
		
		
		
    }
    
    

}
