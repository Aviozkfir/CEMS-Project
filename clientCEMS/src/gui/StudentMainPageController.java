package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class StudentMainPageController extends MainPageController {
	

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
			//a.setWelcomeName();
	    }

	    @FXML
	    void MyExamsButtonPressed(ActionEvent event) {
	    	//StudentStartExamController a = (StudentStartExamController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	    	
	    	

	    }

	    @FXML
	    void StartExamButtonPressed(ActionEvent event) throws IOException {
	    	StudentStartExamController a = (StudentStartExamController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	    }
	    
	    
	

	}

