package gui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * @author Shalom and Omer
 * @extend MainPageController
 * Used to set common elements for most of Student pages
 *
 */
public class StudentMainPageController extends MainPageController  {
	
	
	

	    /**
	     * Used as button for Home button tab
	     */
	    @FXML
	    private Button Home;

	    /**
	     * Used as button for My Exams button tab
	     */
	    @FXML
	    private Button MyExamsButton;

	    /**
	     * Used as button for Start Exam button tab
	     */
	    @FXML
	    private Button StartExamButton;
	    

	    /**
	     * @param ActionEvent event
	     * @throws IOException
	     * Used to load home window when pressed
	     */
	    @FXML
	    void HomeButtonPressed(ActionEvent event) throws IOException {
	    	dashBoardStudentControllerHome a = (dashBoardStudentControllerHome) guiControl
					.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);
	    }

	    /**
	     * @param ActionEvent event
	     * @throws IOException
	     * Used to go to my exams window when pressed
	     */
	    @FXML
	    void MyExamsButtonPressed(ActionEvent event) throws IOException {
	    	StudentMyExamsController a = (StudentMyExamsController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path);
	    	a.setStudentSubject();
	    	
	    }

	    /**
	     * @param ActionEvent event
	     * @throws IOException
	     * Used to go to Start Exam window when pressed
	     */
	    @FXML
	    void StartExamButtonPressed(ActionEvent event) throws IOException {
	    	StudentStartExamController a = (StudentStartExamController) guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	    }
	    
	    
	

	}

