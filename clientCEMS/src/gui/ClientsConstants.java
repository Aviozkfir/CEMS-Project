package gui;

import java.nio.file.Paths;

import javafx.scene.media.AudioClip;

/**
 *this class holds the relative paths of fxml files
 */
public class ClientsConstants {

	public enum Screens {
		LOGIN_PAGE("/gui/CEMSlogin.fxml"), 
		TEACHER_MAIN_PAGE("/gui/dashBoardTeacherView.fxml"),
		STUDENT_MAIN_PAGE("/gui/dashBoardStudentView.fxml"), 
		PRINCIPAL_MAIN_PAGE("/gui/dashBoardPrincipalView.fxml"),
		QUESTION_BANK_PAGE("/gui/questionBankSubjects.fxml"),
		EXAM_BANK_MAIN_PAGE("/gui/examBankMain.fxml"),
		REPORT_MAIN_PAGE("/gui/getReportMain.fxml"),
		MANAGER_REQUESTS_PAGE("/gui/managerRequests.fxml"),
		DIFFERENCE_PARAMETER_UPDATE("/gui/DifferenceParameterUpdate.fxml"),
		REQUESTS("/gui/RequestsDepManager.fxml"),
		ENTRY_CONTROL_PAGE("/gui/EntryControlPane.fxml"),
		EXIT_CONTROL_PAGE("/gui/ExitControlPane.fxml");


		public final String path;

		private Screens(String path) {
			this.path = path;
		}

		@Override
		public String toString() {
			return path;
		}

	}

	public enum AlertType {
		Danger("danger"), Success("success"), Info("info"), Warning("warning"), Link("link");

		private String styleClass;

		AlertType(String styleClass) {
			this.styleClass = styleClass;
		}

		public String getAlertTypeStyleClass() {
			return styleClass;
		}

	}
	public enum Sizes {
		Small,Medium,Large;
	}
}
