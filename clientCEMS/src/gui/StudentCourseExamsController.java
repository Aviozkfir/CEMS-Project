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

/**
 *  Sets the exam as rows in table and has a back button for previous page.
 * @author Shalom and Omer
 * @extend StudentMainPageController
 *
 */
public class StudentCourseExamsController extends StudentMainPageController {
	/**
	 * A spesific course, used to hold the course we want to see exams of.
	 */
	private Course course;


	/**
	 * Used to hold all exams of course.
	 */
	private ArrayList<SolvedExamToView> allExams;

	

	/**
	 * Used to hold the window of controller
	 */
	@FXML
	private AnchorPane myRoot;

	/**
	 * used to show the current subject.
	 */
	@FXML
	private Text subjectName;

	/**
	 * used to show the current course.
	 */
	@FXML
	private Text courseName;


	/**
	 * Used to show the total number of exams.
	 */
	@FXML
	private Text numberOfExams;

	/**
	 * Used to hold the table
	 */
	@FXML
	private VBox vTable;

	/**
	 * Used to go back to previous page.
	 */
	@FXML
	private Button Back;



	/**
	 * Used to go back to subjects of student.
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		StudentCourseController contr = (StudentCourseController) GUIControl.instance.loadStage("myExamsCourses.fxml");
		contr.setStudentCourse(course.getSubject());
	}



	/**
	* Used to set all exams of student given the subject and course.
	 * @param Course course
	 * @throws IOException
	 */
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
