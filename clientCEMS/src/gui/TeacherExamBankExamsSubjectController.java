package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Controller that represents all of the subjects related to a subject
 * 
 * @author On Sharon, Guy 
 *
 */
public class TeacherExamBankExamsSubjectController extends TeacherMainPageController {

    @FXML
    private GridPane grid;

    @FXML
    private Button Back;

    /**
     *  BackPressed - navigates one page back
     *
     */
    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	 GUIControl.instance.loadStage(ClientsConstants.Screens.TEACHER_MAIN_EXAM_PAGE.path);
    }
    
    /**
     *  setTeacherSubject- setting everthing related to the teacher subject , meaining teachr courses
     *
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

				controller.setChosenPath("TeacherExamBankExamsCourses.fxml");
				controller.setText(subjectList.get(i).getName());
				controller.setObject(subjectList.get(i));
				
				controller.setConsumer((fxmlLocation, subject)->{   
					try {
						TeacherExamBankExamsCoursesController contr =(TeacherExamBankExamsCoursesController) GUIControl.instance.loadStage(fxmlLocation);
					
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
