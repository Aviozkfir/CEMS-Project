package gui;

import entity.Course;
import entity.Exam;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TeacherCreateManualExamController extends TeacherMainPageController{

    @FXML
    private VBox myVbox;

 //   @FXML
 //   private Text subjectName;

  //  @FXML
  //  private Text courseName;
    private Exam exam;

    @FXML
    private TextField examTitle;

    @FXML
    private TextField examDuration;

    @FXML
    private TextField examCode;

    @FXML
    private Button btnSelectExam;

    @FXML
    private TextField filePath;

    @FXML
    private Button publish;

    @FXML
    private Button btnBack;

	private Course course;

    @FXML
    void ExamPageButtonPressed(ActionEvent event) {

    }

    @FXML
    void HomePageButtonPressed(ActionEvent event) {

    }

    @FXML
    void LogOutButtonPressed(ActionEvent event) {

    }

    @FXML
    void OnGoingPageButtonPressed(ActionEvent event) {

    }

    @FXML
    void QuestionPageButtonPressed(ActionEvent event) {

    }

    @FXML
    void ReportPageButtonPressed(ActionEvent event) {

    }

    @FXML
    void btnBackPressed(ActionEvent event) {

    }

    @FXML
    void btnPublishPressed(ActionEvent event) {

    }

    @FXML
    void btnSelectPressed(ActionEvent event) {

    }

	public void setTeacherCreateExam(Course course) {
		this.course=course;
    	exam.setCid(course.getId());
    	exam.setSid(course.getSubject().getId());
    	exam.setID(((Teacher)guiControl.getUser()).getId());
    	exam.setMode("Manual");
		
	}

}
