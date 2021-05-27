package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class PrincipalReportController extends PrincipalMainPageController implements Initializable{
	private String SelectedReport=null;
	@FXML
	private ComboBox<String> ReportTypeCombo;
	@FXML
	private Button CreateButton;
	
	

	@FXML void ChooseReportCombo(ActionEvent event) throws IOException {
		SelectedReport = ReportTypeCombo.getSelectionModel().getSelectedItem();
	}

	@FXML
	void CreateButtonPressed(ActionEvent event) throws IOException {
		if (Istype("Course")) {
			PrincipalReportCourseControl controller = (PrincipalReportCourseControl) guiControl
					.loadStage(ClientsConstants.Screens.PRINCIPAL_REPORT_COURSE_PAGE.path);
		}

	}

	public boolean Istype(String type) {
		if (SelectedReport.equals(type))
			return true;
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReportTypeCombo.getItems().addAll("Teacher","Course","Student");
	}

}
