package gui;

import entity.Course;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TeacherCreateExamPage2Controller {

    @FXML
    private VBox vTable;
    
    private Course course;
    
    private VBox myVbox;
    
    private AnchorPane page1;
    private AnchorPane page3;

    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
	}

	public void setPage1(AnchorPane page1) {
		this.page1 = page1;
	}
    
    public void setPage3(AnchorPane page3) {
  		this.page3 = page3;
  	}

	public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
    
    @FXML
    void btnBackPressed(ActionEvent event) {
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page1);
    }
    
    @FXML
    void btnNextPressed(ActionEvent event) {
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page3);
    }

}
