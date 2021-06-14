package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.SolvedExamType;
import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Controller that holds the exam that solved by student.
 * 
 * @author oavioz
 *
 */
public class TeacherExamBankSolvedByStudentApproveController extends TeacherMainPageController {

	@FXML
	private AnchorPane myRoot;

	@FXML
	private Text subjectName;

	@FXML
	private Text courseName;

	@FXML
	private Text examName;

	@FXML
	private Text numOfExams;

	@FXML
	private Text numOfGraded;

	@FXML
	private Text numOfNotGraded;

	@FXML
	private Button btnPublishAll;

	@FXML
	private VBox vTable;

	@FXML
	private Button btnBack;

	private SolvedExamType exam;

	/**
	 * This method getting back to the screen Solved exams.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void btnBackPressed(ActionEvent event) throws IOException {
		TeacherExamBankSolvedByStudentExamsController a = ((TeacherExamBankSolvedByStudentExamsController) guiControl
				.loadStage("/gui/TeacherExamBankSolvedByStudentExams.fxml"));
		a.setTeacherCourse(exam.getCourse());

	}

	@FXML
	void btnPublishAllPressed(ActionEvent event) {

	}

	/**
	 * This method setting the solved exam.
	 * 
	 * @param exam The exam.
	 */
	public void setExamType(SolvedExamType exam) {
		this.exam = exam;
		this.numOfExams.setText(exam.getTotal() + "");
		this.numOfGraded.setText(exam.getChecked() + "");
		this.numOfNotGraded.setText((exam.getTotal() - exam.getChecked()) + "");
		this.subjectName.setText(exam.getCourse().getSubject().getName());
		this.courseName.setText(exam.getCourse().getName());
		this.examName.setText(exam.getName());
	}

}
