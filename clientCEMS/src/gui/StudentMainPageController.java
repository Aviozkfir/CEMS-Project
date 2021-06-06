package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class StudentMainPageController extends MainPageController  {
	
	
	

	    @FXML
	    private Button Home;

	    @FXML
	    private Button MyExamsButton;

	    @FXML
	    private Button StartExamButton;
	    

	    @FXML
	    void HomeButtonPressed(ActionEvent event) throws IOException {
	    	dashBoardStudentControllerHome a = (dashBoardStudentControllerHome) guiControl
					.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);
	    }

	    @FXML
	    void MyExamsButtonPressed(ActionEvent event) throws IOException {
	    	StudentMyExamsController a = (StudentMyExamsController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path);
	    	a.setStudentSubject();
	    	
	    }

	    @FXML
	    void StartExamButtonPressed(ActionEvent event) throws IOException {
	    	StudentStartExamController a = (StudentStartExamController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	    }
	    
	    
	

	}

