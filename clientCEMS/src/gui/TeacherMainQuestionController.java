package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entity.Course;
import entity.PersonCEMS;
import entity.Subject;
import entity.Teacher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for the question bank screen.
 * @author oavioz
 *
 */
public class TeacherMainQuestionController extends TeacherMainPageController {

    @FXML
    private GridPane grid;

    
    /**
     * This method setting the subjects in the question bank controller.
     */
    public void setTeacherSubject() {
    	int i;
		Teacher teacher = (Teacher) guiControl.getUser();
		
		ArrayList<Subject> subjectList = teacher.getSubjectList();
		
		
		for(i=0;i<subjectList.size();i++)
			try {
			
				btnFolderController controller;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
				StackPane root = fxmlLoader.load();
				controller = (btnFolderController) fxmlLoader.getController();

				controller.setChosenPath("TeacherQuestionBankCourses.fxml");
				controller.setText(subjectList.get(i).getName());
				controller.setObject(subjectList.get(i));
				
				controller.setConsumer((fxmlLocation, subject)->{   
					try {
						TeacherQuestionBankCoursesController contr =(TeacherQuestionBankCoursesController) GUIControl.instance.loadStage(fxmlLocation);
					
						contr.setTeacherCourse((Subject)subject);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				});
				
				
				grid.add(root,i%4 , i/4);
				
						
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }
    
    
    
    
    
	
    
    
    

}
