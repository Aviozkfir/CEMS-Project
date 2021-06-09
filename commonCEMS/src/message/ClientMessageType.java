package message;
/**
 * ClientMessageType for the type of the client message
 */
public enum ClientMessageType {
	DISCONNECTED,
	LOGOUT,
	CONNECTION,
	LOGIN_PERSON,
	LOGIN_TEACHER,
	LOGIN_STUDENT,
	TEACHER_SUBJECTS_INFORMATION,
	TEACHER_COURSES_INFORMATION,
	PRINCIPAL_SUBJECTS_INFORMATION,
	PRINCIPAL_COURSES_INFORMATION,
	PRINCIPAL_STUDENTS_INFORMATION,
	PRINCIPAL_TEACHERS_INFORMATION,
	PRINCIPAL_REPORT_COURSES_INFORMATION,
	PRINCIPAL_REPORT_STUDENT_INFORMATION,  //still need to add controllers (kfir note)
	PRINCIPAL_REPORT_TEACHER_INFORMATION,  //this too.
	PRINCIPAL_REQUESTS_INFORMATION,
	PRINCIPAL_APPROVED_REQUESTS_UPDATE,
	PRINCIPAL_DECLINED_REQUESTS_UPDATE,
	PRINCIPAL_CHECK_REQUESTS_COUNTING,
	PRINCIPAL_UPDATE_REQUESTS_STATUS,
	VALIDATE_EXAM_CODE,
	GET_EXAM_INFORMATION,
	GET_QUESTION_BY_COURSE,
	GET_QUESTION_BY_EXAM,
	GET_EXAM_BY_COURSE,
	TEACHER_ADD_QUESTION,
	TEACHER_ADD_EXAM,
	TEACHER_DELETE_QUESTION,
	VALIDATE_STUDENT_ID,
	UPLOAD_MANUAL_EXAM,
	DOWNLOAD_MANUAL_EXAM,
	GET_EXAM_QUESTIONS,
	INSERT_EXAM_QUESTIONS,
	INSERT_EXAM_TO_DB, 
	STUDENT_COURSES_INFORMATION,
	STUDENT_SUBJECTS_INFORMATION,
	
	
	
	

	

}
