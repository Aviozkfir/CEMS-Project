package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TeacherMainExamController extends TeacherMainPageController {

		@FXML
	    private Button BtnExam;

	    @FXML
	    private Button btnSolvedExam;

	    @FXML
	    void btnExamPressed(ActionEvent event) throws IOException {
	    	TeacherExamBankExamsSubjectController a = (TeacherExamBankExamsSubjectController) guiControl.loadStage("TeacherExamBankExamsSubject.fxml");
			a.setTeacherSubject();
	    }

	    @FXML
	    void btnSolvedExamPressed(ActionEvent event) throws IOException {
	    	TeacherExamBankSolvedByStudentSubjectController a = ((TeacherExamBankSolvedByStudentSubjectController) guiControl.loadStage("TeacherExamBankSolvedByStudentSubjects.fxml"));
			a.setTeacherSubject();
	    }
}
