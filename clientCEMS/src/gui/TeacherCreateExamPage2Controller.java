package gui;

import entity.Course;
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

    public void setPage1(AnchorPane page1) {
		this.page1 = page1;
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

}
