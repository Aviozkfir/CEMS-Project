package gui;

import java.nio.file.Paths;

import javafx.scene.media.AudioClip;

/**
 *this class holds the relative paths of fxml files
 */
public class ClientsConstants {

	public enum Screens {
		LOGIN_PAGE("/gui/CEMSlogin.fxml"),
		
		TEACHER_WELCOME_PAGE("/gui/TeacherMainDashBoardView.fxml"),
		TEACHER_MAIN_QUESTION_PAGE("/gui/TeacherMainQuestion.fxml"),
		TEACHER_MAIN_REPORT_PAGE("/gui/TeacherMainGetReport.fxml"),
		TEACHER_MAIN_EXAM_PAGE("/gui/TeacherMainExamBank.fxml"),
		
		QUESTION_BANK_COURSES("/gui/questionBankCourses.fxml"),
		

		STUDENT_MAIN_PAGE("/gui/dashBoardStudentView.fxml"), 
		PRINCIPAL_MAIN_PAGE("/gui/dashBoardPrincipalView.fxml"),
		
		
		EXAM_BANK_MAIN_PAGE("/gui/examBankMain.fxml"),
		REPORT_MAIN_PAGE("/gui/getReportMain.fxml"),
		MANAGER_REQUESTS_PAGE("/gui/managerRequests.fxml"),
		//DIFFERENCE_PARAMETER_UPDATE("/gui/DifferenceParameterUpdate.fxml"),
		//ENTRY_CONTROL_PAGE("/gui/EntryControlPane.fxml"),
		STUDENT_MY_EXAMS_PAGE("/gui/myExams.fxml"),
		STUDENT_START_EXAM_PAGE("/gui/startExamStudent.fxml"),
		EXIT_CONTROL_PAGE("/gui/ExitControlPane.fxml"),
		
		btnFolder("btnFolder.fxml"),
		
//
//		
//		TEACHER_MAIN_PAGE("/gui/dashBoardTeacherView.fxml"),
//		QUESTION_BANK_PAGE("/gui/questionBankSubjects.fxml"),
//		QUESTION_BANK_COURSES("/gui/questionBankCourses.fxml"),
//		TEACHER_REPORT_PAGE("/gui/teacherGetReport.fxml"),
//		
		
		
		
		
		
		PRINCIPAL_EXAM_BANK_PAGE("/gui/principalExamBankMain.fxml"),
		PRINCIPAl_QUESTION_BANK_PAGE("/gui/PrincipalQuestionBankSubjects.fxml"),
		PRINCIPAl_QUESTION_BANK_COURSES("/gui/principalQuestionBankCourses.fxml"),
		PRINCIPAL_REPORT_PAGE("/gui/principalGetReportMain.fxml"),
		PRINCIPAL_REPORT_COURSE_PAGE("/gui/principalCourseReport.fxml"),
		PRINCIPAL_REQUESTS_PAGE("/gui/principalRequests.fxml"),
        STUDENT_CHOICE_EXAM_PAGE("/gui/studentExamChoice.fxml");
		
		//DIFFERENCE_PARAMETER_UPDATE("/gui/DifferenceParameterUpdate.fxml"),
		//ENTRY_CONTROL_PAGE("/gui/EntryControlPane.fxml"),
	
		
		
		
		

		

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
