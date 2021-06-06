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

public class StudentMyExamsController extends StudentMainPageController {

	@FXML
	private GridPane grid;

	public void setStudentSubject() {
	   	int i;
			Student student = (Student) guiControl.getUser();
			
			ArrayList<Subject> subjectList = student.getSubjectList();
			
		
			for(i=0;i<subjectList.size();i++)
				try {
				
					btnFolderController controller;
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
					StackPane root = fxmlLoader.load();
					controller = (btnFolderController) fxmlLoader.getController();

					controller.setChosenPath("myExamsCourses.fxml");
					controller.setText(subjectList.get(i).getName());
					controller.setObject(subjectList.get(i));
					
					controller.setConsumer((fxmlLocation, subject)->{   
						try {
							StudentCourseController contr =(StudentCourseController) GUIControl.instance.loadStage(fxmlLocation);
						
							contr.setStudentCourse((Subject)subject);
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
