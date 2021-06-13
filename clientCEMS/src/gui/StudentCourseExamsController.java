package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.Exam;
import entity.Question;
import entity.SolvedExam;
import entity.SolvedExamToView;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

public class StudentCourseExamsController extends StudentMainPageController {
	private Course course;


	private ArrayList<SolvedExamToView> allExams;

	

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
	private Button Back;



	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		StudentCourseController contr = (StudentCourseController) GUIControl.instance.loadStage("myExamsCourses.fxml");
		contr.setStudentCourse(course.getSubject());
	}



	public void setStudentExams(Course course) throws IOException {
		this.course = course;
		ArrayList<SolvedExamToView> examsCourse = new ArrayList<SolvedExamToView>();
		courseName.setText(course.getName());
		subjectName.setText(course.getSubject().getName());

		ClientMessage m1 = new ClientMessage(ClientMessageType.GET_SOLVED_EXAMS, (Student) guiControl.getUser());
		guiControl.sendToServer(m1);
		allExams = (ArrayList<SolvedExamToView>) guiControl.getServerMsg().getMessage();
		for (int i = 0; i < allExams.size(); i++) {
			String examID = allExams.get(i).getEid();
			String cid = examID.substring(2, 4);
			if (cid.equals(course.getId()))
				examsCourse.add(allExams.get(i));
		}

		numberOfExams.setText("" + examsCourse.size());

		for (int i = 0; i < examsCourse.size(); i++) {

			StudentExamTableRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/StudentExamsTableRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (StudentExamTableRowController) fxmlLoader.getController();
			controller.setQuestions(examsCourse.get(i));
			controller.setExam(allExams.get(i), course);
			vTable.getChildren().add(root);
		}

	}
	
	


}
