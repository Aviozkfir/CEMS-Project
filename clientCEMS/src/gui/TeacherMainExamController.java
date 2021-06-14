package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The main controller for the exam bank in menu
 * @author oavioz
 *
 */
public class TeacherMainExamController extends TeacherMainPageController {

		@FXML
	    private Button BtnExam;

	    @FXML
	    private Button btnSolvedExam;

	    /**
	     * This method send us to the subjects in the exam bank.
	     * @param event
	     * @throws IOException
	     */
	    @FXML
	    void btnExamPressed(ActionEvent event) throws IOException {
	    	TeacherExamBankExamsSubjectController a = (TeacherExamBankExamsSubjectController) guiControl.loadStage("TeacherExamBankExamsSubject.fxml");
			a.setTeacherSubject();
	    }

	    /**
	     * this method send us to the subject in the solved exam bank.
	     * @param event
	     * @throws IOException
	     */
	    @FXML
	    void btnSolvedExamPressed(ActionEvent event) throws IOException {
	    	TeacherExamBankSolvedByStudentSubjectController a = ((TeacherExamBankSolvedByStudentSubjectController) guiControl.loadStage("TeacherExamBankSolvedByStudentSubjects.fxml"));
			a.setTeacherSubject();
	    }
}
