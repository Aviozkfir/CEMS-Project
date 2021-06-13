package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Student;
import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author Shalom and omer
 * @extend StudentMainPageController
 * Sets the courses of student as folders which the student can choose from.
 *
 */
public class StudentCourseController extends StudentMainPageController {
/**
 * An array that holds the subjects of student.
 */
public static ArrayList<Subject> subjects=new ArrayList<Subject>() ;


	/**
	 * A variable that is used for adding folders to grid.
	 */
	int counter = 0;
	/**
	 * A text field that shows the name of the subject.
	 */
	@FXML
	private Text subjectName;

	/**
	 * A grid that holds folders of courses
	 */
	@FXML
	private GridPane grid;

	/**
	 * A button that is used to go back to previous page.
	 */
	@FXML
	private Button Back;

	/**
	 * 	Used to go back to subject page.
	 * @param ActionEvent event
	 * @throws IOException
	 * 
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		StudentMyExamsController a = (StudentMyExamsController) guiControl
				.loadStage(ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path);
		a.setStudentSubject();
	}

	/**
	 * Used to set the courses of student as folders.
	 * @param subject
	 */
	public void setStudentCourse(Subject subject) {

		this.subjectName.setText(subject.getName());
		int i;
		Student student = (Student) GUIControl.instance.getUser();

		ArrayList<Course> courseList = student.getCourseList();

		for (i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).getSubject().getId().equals(subject.getId()))
				try {
					
					
				
					btnFolderController controller;
					FXMLLoader fxmlLoader = new FXMLLoader(
							getClass().getResource(ClientsConstants.Screens.btnFolder.path));
					StackPane root = fxmlLoader.load();
					controller = (btnFolderController) fxmlLoader.getController();
					
					controller.setChosenPath("/gui/myExamsResults.fxml");
					controller.setText(courseList.get(i).getName());
					controller.setObject(courseList.get(i));
					controller.setConsumer((fxmlLocation, object) -> {
						try {

							((StudentCourseExamsController) GUIControl.instance.loadStage(fxmlLocation))
									.setStudentExams((Course) object);

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
}
