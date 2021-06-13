package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Principal;
import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * @author On Avioz ,Kfir Avioz
 * @extend PrincipalMainPageController 
 * Controller for courses inside chosen subject inside exam bank screen.
 *
 */
public class PrincipalExamBankCoursesController extends PrincipalMainPageController {
	/**
	 * Text for subject name.
	 */
	@FXML
	private Text subjectName;
	/**
	 * back button.
	 */
	@FXML
	private Button Back;
	/**
	 * grid for courses.
	 */
	@FXML
	private GridPane grid;

	/**
	 * @param event ActionEvent
	 * @throws IOException when Back button pressed, loading exam bank screen,
	 *                     setting new requests text if necessary.
	 */
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankSubjectsController a = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_PAGE.path);
		a.setPrincipalSubject();
		a.setRequestCounter();
	}

	/**
	 * @param sub - the subject that holds the courses. This method sending request
	 *            message to server, and getting back course list, setting courses
	 *            dynamically inside the grid. for each course, setting exam list
	 *            and setting text new request if necessary. when clicking on course
	 *            the user get his exams in other screen.
	 */
	public void setPrincipalCourse(Subject sub) {
		this.subjectName.setText(sub.getName());
		int i;
		Principal principal = (Principal) GUIControl.instance.getUser();

		ArrayList<Course> courseList = principal.getCourseList();
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

					controller.setChosenPath(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_EXAM.path);
					controller.setText(courseList.get(i).getName());
					controller.setObject(courseList.get(i));
					controller.setConsumer((fxmlLocation, course) -> {
						try {

							PrincipalExamBankExamsController contr = (PrincipalExamBankExamsController) GUIControl.instance
									.loadStage(fxmlLocation);
							contr.setPrincipalCourse((Course) course);
							contr.setRequestCounter();

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
