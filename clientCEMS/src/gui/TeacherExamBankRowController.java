package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class TeacherExamBankRowController {

    @FXML
    private Button btnView;

    @FXML
    private Label questionID;

    @FXML
    private Label examTitle;

    @FXML
    private Label date;

    @FXML
    private Label author;

    @FXML
    private Button btnPublish;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
		author.setText(exam.getID());
		
		//coverting fate from yyyy-mm-dd to dd-mm-yyyy
    	String startDateString = exam.getDate().toString();
        DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	//
    	date.setText(LocalDate.parse(startDateString, oldFormat).format(newFormat));
		examTitle.setText(exam.getName());
	}

	@FXML
    void btnDeletePressed(ActionEvent event) {

    }

    @FXML
    void btnEditPressed(ActionEvent event) {

    }

    @FXML
    void btnPublishPressed(ActionEvent event) {

    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) {
    	
    }

}
