package gui;

import java.io.IOException;

import entity.Course;
import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import message.ClientMessage;
import message.ClientMessageType;

public class TeacherExamBankRowController{

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
    private Course course;
    public void setExam(Exam exam,Course course) {
		this.exam = exam;
		this.course= course;
		author.setText(exam.getID());
		date.setText(exam.getDate());
		examTitle.setText(exam.getName());
		questionID.setText(exam.getEid());
	}

	@FXML
    void btnDeletePressed(ActionEvent event) {

    }

    @FXML
    void btnEditPressed(ActionEvent event) {
    	//((TeacherEditExamController) GUIControl.instance.loadStage("TeacherEditExam.fxml")).setTeacherEditExam(course);
    }

    @FXML
    void btnPublishPressed(ActionEvent event) {
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_PUBLISH_EXAM, exam);
    	GUIControl.instance.sendToServer(m1);
    }

    @FXML
    void btnViewQuestionPressed(ActionEvent event) throws IOException {
    	GUIControl guiControl = GUIControl.getInstance();
		TeacherExamBankViewExamController a = (TeacherExamBankViewExamController) guiControl
				.loadStage(gui.ClientsConstants.Screens.TEACHER_EXAM_VIEW.path);
		a.setTeacherExam(exam, course);
    }

}
