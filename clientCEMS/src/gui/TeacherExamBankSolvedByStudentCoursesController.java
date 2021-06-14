package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controller that holds the exam bank solved by student by courses.
 * 
 * @author fjdf
 *
 */
public class TeacherExamBankSolvedByStudentCoursesController extends TeacherMainPageController {

	@FXML
	private Text subjectName;

	@FXML
	private Button Back;

	@FXML
	private GridPane grid;

	/**
	 * * This method getting back to the screen Solved exams by subjects.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		TeacherExamBankSolvedByStudentSubjectController a = ((TeacherExamBankSolvedByStudentSubjectController) guiControl
				.loadStage("TeacherExamBankSolvedByStudentSubjects.fxml"));
		a.setTeacherSubject();
	}

	/**
	 * This method sets the courses of the solved exams by student dynamically by
	 * after the user chose specific subject .
	 * 
	 * @param sub the subject we want.
	 */
	public void setTeacherCourse(Subject sub) {

		this.subjectName.setText(sub.getName());
		int i;
		Teacher teacher = (Teacher) GUIControl.instance.getUser();

		ArrayList<Course> courseList = teacher.getCourseList();
		System.out.print(courseList.toString());
		int counter = 0;
		for (i = 0; i < courseList.size(); i++)
			if (courseList.get(i).getSubject().getId().equals(sub.getId()))
				try {
					btnFolderController controller;
					FXMLLoader fxmlLoader = new FXMLLoader(
							getClass().getResource(ClientsConstants.Screens.btnFolder.path));
					StackPane root = fxmlLoader.load();
					controller = (btnFolderController) fxmlLoader.getController();

					controller.setChosenPath("/gui/TeacherExamBankSolvedByStudentExams.fxml");
					controller.setText(courseList.get(i).getName());
					controller.setObject(courseList.get(i));
					controller.setConsumer((fxmlLocation, course) -> {
						try {

							((TeacherExamBankSolvedByStudentExamsController) GUIControl.instance
									.loadStage(fxmlLocation)).setTeacherCourse((Course) course);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					});

					grid.add(root, counter % 4, counter / 4);
					counter++;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	}
}
