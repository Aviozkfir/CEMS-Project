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

public class PrincipalQuestionBankCoursesController extends PrincipalMainPageController {

	@FXML
    private Text subjectName;

	@FXML
    private Button Back;

    @FXML
    private GridPane grid;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	PrincipalQuestionBankSubjectsController a = (PrincipalQuestionBankSubjectsController) guiControl.loadStage(ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_PAGE.path);
		a.setPrincipalSubject();
    }


	public void setPrincipalCourse(Subject sub) {
		this.subjectName.setText(sub.getName());
    	int i;
		Principal principal = (Principal) GUIControl.instance.getUser();
	
		ArrayList<Course> courseList = principal.getCourseList(); 
		System.out.print(courseList.toString());
		int counter =0;
		for(i=0;i<courseList.size();i++)
			if(courseList.get(i).getSubject().getId().equals(sub.getId()))
				try {
				btnFolderController controller;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
				StackPane root = fxmlLoader.load();
				controller = (btnFolderController) fxmlLoader.getController();

				controller.setChosenPath(ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path);
				controller.setText(courseList.get(i).getName());
				controller.setObject(courseList.get(i));
				controller.setConsumer((fxmlLocation, subject)->{   
					try {
						
						GUIControl.instance.loadStage(fxmlLocation);
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				});
				
				
				grid.add(root, counter%4, counter/4);
				counter++;
						
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }
}
