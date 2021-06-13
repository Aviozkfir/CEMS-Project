package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Principal;
import entity.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * @author On Avioz ,Kfir Avioz
 * Controller of principal "Exam Bank" screen.
 * @extends PrincipalMainPageController.
 */
public class PrincipalExamBankSubjectsController extends PrincipalMainPageController {
	@FXML
	private GridPane grid;

	/**
	 * This method sending request message to server, and getting back subject list, setting subjects dynamically inside the grid.
	 * each subject, setting courses list and setting text new request if necessary.
	 * when clicking on subject we get his courses in other screen.
	 */
	public void setPrincipalSubject() {
		int i;
		Principal principal = (Principal) guiControl.getUser();

		ArrayList<Subject> subjectList = principal.getSubjectList();

		System.out.println(subjectList.toString() + " " + principal.getCourseList().toString());
		for (i = 0; i < subjectList.size(); i++)
			try {

				btnFolderController controller;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
				StackPane root = fxmlLoader.load();
				controller = (btnFolderController) fxmlLoader.getController();

				controller.setChosenPath(ClientsConstants.Screens.PRINCIPAl_EXAM_BANK_COURSES.path);
				controller.setText(subjectList.get(i).getName());
				controller.setObject(subjectList.get(i));
				controller.setConsumer((fxmlLocation, subject) -> {
					try {
						PrincipalExamBankCoursesController contr = (PrincipalExamBankCoursesController) GUIControl.instance
								.loadStage(fxmlLocation);

						contr.setPrincipalCourse((Subject) subject);
						contr.setRequestCounter();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				});

				grid.add(root, i % 4, i / 4);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
