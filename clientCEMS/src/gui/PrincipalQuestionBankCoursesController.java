package gui;

import java.io.IOException;

import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PrincipalQuestionBankCoursesController extends PrincipalMainPageController {

	@FXML
    private Text name;

    @FXML
    private Button Back;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	PrincipalQuestionBankSubjectsController a = (PrincipalQuestionBankSubjectsController) guiControl.loadStage(ClientsConstants.Screens.PRINCIPAl_QUESTION_BANK_PAGE.path);
		a.setPrincipalSubject();
    }

    
	
    
	public void setName(String name) {
		this.name.setText(name);
	}




	public void setPrincipalCourse(Subject sub) {
//    	int i;
//		Teacher teacher = (Teacher) GUIControl.instance.getUser();
//	
//		ArrayList<Course> courseList = teacher.getCourseList(); 
//		Course course;
//		Button butt;
//		for(i=0;i<5;i++)
//			try {
////				btnFolderController controller;
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientsConstants.Screens.btnFolder.path));
//				StackPane root = fxmlLoader.load();
//				controller = (btnFolderController) fxmlLoader.getController();
//
//				controller.setChosenPath(ClientsConstants.Screens.QUESTION_BANK_COURSES.path);
//				controller.setText(subjectList.get(i).getName());
//				controller.setSubject(subjectList.get(i));
//				controller.setConsumer((fxmlLocation, subject)->{   
//					try {
//						questionBankCoursesController contr =(questionBankCoursesController) GUIControl.instance.loadStage(fxmlLocation);
//					
//						contr.setTeacherCourse((Subject)subject);
//					
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//				});
//				
//				
//				grid.add(root, i%4, i/4);
//				
//						
//				
//				
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    	
    }
}
