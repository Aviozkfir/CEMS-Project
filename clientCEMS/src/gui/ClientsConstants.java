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
		QUESTION_BANK_PAGE("/gui/questionBankSubjects.fxml"),
		QUESTION_BANK_COURSES("/gui/questionBankCourses.fxml"),
		TEACHER_REPORT_PAGE("/gui/teacherGetReport.fxml"),
		
		
		STUDENT_MAIN_PAGE("/gui/dashBoardStudentView.fxml"), 
		
		
		PRINCIPAL_MAIN_PAGE("/gui/dashBoardPrincipalView.fxml"),
		PRINCIPAl_QUESTION_BANK_PAGE("/gui/PrincipalQuestionBankSubjects.fxml"),
		PRINCIPAL_EXAM_BANK_PAGE("/gui/principalExamBankMain.fxml"),
		PRINCIPAL_REQUESTS_PAGE("/gui/principalRequests.fxml"),
		PRINCIPAL_REPORT_PAGE("/gui/principalGetReportMain.fxml"),
		
		//DIFFERENCE_PARAMETER_UPDATE("/gui/DifferenceParameterUpdate.fxml"),
		//ENTRY_CONTROL_PAGE("/gui/EntryControlPane.fxml"),
		STUDENT_MY_EXAMS_PAGE("/gui/myExams.fxml"),
		STUDENT_START_EXAM_PAGE("/gui/startExamStudent.fxml"),
		EXIT_CONTROL_PAGE("/gui/ExitControlPane.fxml"),
		
		btnFolder("btnFolder.fxml");
		
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
