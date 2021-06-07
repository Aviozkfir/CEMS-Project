package gui;

import java.io.IOException;

import entity.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherCreateExamPage4Controller {

	 private VBox myVbox;
	 
	 private Course course;
    @FXML
    private Text examID;

    @FXML
    private Button btnBack;

    public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}
    
    public void setCourse(Course course) {
		this.course = course;
	}
    
    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	((TeacherExamBankExamsController)GUIControl.instance.loadStage("/gui/TeacherExamBankExams.fxml")).setTeacherCourse(course);
    }

}
