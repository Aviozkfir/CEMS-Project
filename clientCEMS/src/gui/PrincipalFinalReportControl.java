package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class PrincipalFinalReportControl extends PrincipalMainPageController implements Initializable {
    @FXML
    private Text Average;

    @FXML
    private Text Median;
    
    Principal principal = (Principal) guiControl.getUser();
	@FXML
	void BackPressed(ActionEvent event) throws IOException {
		PrincipalExamBankSubjectsController a = (PrincipalExamBankSubjectsController) guiControl
				.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
		a.setPrincipalSubject();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Average.setText(principal.getReport().getAverage());
		Median.setText(principal.getReport().getMedian());
	}


	
}
