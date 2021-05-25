package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrincipalReportCourseControl extends PrincipalMainPageController {
    @FXML
    private Button Back;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	PrincipalExamBankSubjectsController a = (PrincipalExamBankSubjectsController) guiControl.loadStage(ClientsConstants.Screens.PRINCIPAL_EXAM_BANK_PAGE.path);
		a.setPrincipalSubject();
    }


}
