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
public class StudentCourseController extends StudentMainPageController {
	
   
    @FXML
    private Text subjectName;

    @FXML
    private GridPane grid;

    @FXML
    private Button Back;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	StudentMyExamsController a = (StudentMyExamsController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path);
		a.setStudentSubject();
    }
	public void setStudentCourse(Subject sub) {
		
		this.subjectName.setText(sub.getName());
    	int i;
		Student student = (Student) GUIControl.instance.getUser();
	
		ArrayList<Course> courseList = student.getCourseList(); 
		
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
					//try {
						
						//((TeacherQuestionBankQuestionsController)GUIControl.instance.loadStage(fxmlLocation)).setTeacherCourse((Course) course);
						
						
					
					//} catch (IOException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
				//	}
				
				});
				
				
				grid.add(root, counter%4, counter/4);
				counter++;
						
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }

 

}
