package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class TeacherQuestionBankCoursesController extends TeacherMainPageController  {

	@FXML
    private Text subjectName;

	@FXML
    private Button Back;

    @FXML
    private GridPane grid;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	TeacherMainQuestionController a = (TeacherMainQuestionController) guiControl.loadStage(ClientsConstants.Screens.TEACHER_MAIN_QUESTION_PAGE.path);
		a.setTeacherSubject();
    }

    
	
    



	public void setTeacherCourse(Subject sub) {
		
		this.subjectName.setText(sub.getName());
    	int i;
		Teacher teacher = (Teacher) GUIControl.instance.getUser();
	
		ArrayList<Course> courseList = teacher.getCourseList(); 
		System.out.print(courseList.toString());
		int counter =0;
		for(i=0;i<courseList.size();i++)
			if(courseList.get(i).getSubject().getId().equals(sub.getId()))
				try {
				btnFolderController controller;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
				StackPane root = fxmlLoader.load();
				controller = (btnFolderController) fxmlLoader.getController();

				controller.setChosenPath("/gui/TeacherQuestionBankQuestions.fxml");
				controller.setText(courseList.get(i).getName());
				controller.setObject(courseList.get(i));
				controller.setConsumer((fxmlLocation, course)->{   
					try {
						
						((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage(fxmlLocation)).setTeacherCourse((Course) course);
						
						
					
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
