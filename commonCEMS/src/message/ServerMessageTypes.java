package message;
/**
 * ServerMessageType is the type of the message of ServerMessage
 */
public enum ServerMessageTypes {
	LOGIN,
	LOGOUT_SUCCESS,
	TEACHER_SUBJECTS_ADDED,
	TEACHER_SUBJECTS_NOT_ADDED,
	TEACHER_COURSES_ADDED,
	TEACHER_COURSES_NOT_ADDED,
	TEACHER_VALID_CODE,
	TEACHER_EXAM_UPLOADED_SUCCECFULLY,
	TEACHER_EXAM_UPLOADING_FAILED,
	TEACHER_INVALID_CODE,
	PRINCIPAL_SUBJECTS_ADDED,
	PRINCIPAL_SUBJECTS_NOT_ADDED,
	PRINCIPAL_COURSES_ADDED,
	PRINCIPAL_COURSES_NOT_ADDED,
	PRINCIPAL_STUDENTS_ADDED,
	PRINCIPAL_STUDENTS_NOT_ADDED,
	PRINCIPAL_TEACHERS_ADDED,
	PRINCIPAL_TEACHERS_NOT_ADDED,
	PRINCIPAL_REPORT_COURSES_ADDED,
	PRINCIPAL_REPORT_COURSES_NOT_ADDED,
	PRINCIPAL_REPORT_STUDENT_ADDED,      //mark to easy find :kfir
	PRINCIPAL_REPORT_STUDENT_NOT_ADDED, //this too.
	PRINCIPAL_REPORT_TEACHER_ADDED,      //this too.
	PRINCIPAL_REPORT_TEACHER_NOT_ADDED,	//this too.
	PRINCIPAL_REQUESTS_ADDED,
	PRINCIPAL_REQUESTS_NOT_ADDED,
	PRINCIPAL_APPROVED_REQUESTS_ADDED,
	PRINCIPAL_DECLINED_REQUESTS_ADDED,
	PRINCIPAL_DIDNT_GOT_REQUESTS_COUNTING,
	PRINCIPAL_GOT_REQUESTS_COUNTING,
	PRINCIPAL_UPDATE_REQUESTS_STATUS_SUCCESS,
	QUESTION_BY_EXAM_NOT_RECIVED,
	LOGIN_PERSON_NOT_FOUND,
	LOGIN_TEACHER,
	LOGIN_STUDENT,
	LOGIN_PRINCIPAL,
	SERVER_ERROR,
	EXAM_CODE_NOT_FOUND,
	EXAM_CODE_FOUND,
	EXAM_INFORMATION_RECIVED,
	EXAM_INFORMATION_NOT_RECIVED,
	QUESTION_BY_COURSE_NOT_RECIVED,
	QUESTION_BY_COURSE_RECIVED,
	EXAM_BY_COURSE_RECIVED,
	EXAM_BY_COURSE_NOT_RECIVED,
	QUESTION_IS_ALREADY_IN_DATABASE,
	QUESTION_ADDED,
	QUESTION_NOT_ADDED,
	EXAM_NOT_ADDED,
	EXAM_ADDED,
	QUESTION_DELETED,
	QUESTION_NOT_DELETED,
	QUESTION_NOT_DELETED_IN_USE,
	STUDENT_ID_NOT_FOUND,
	STUDENT_ID_FOUND,
	EXAM_UPLOADED_SUCCECFULLY,
	EXAM_UPLOADING_FAILED,
	EXAM_DOWNLOAD_FAIL,
	EXAM_DOWNLOAD_SUCCESS,
	GET_EXAM_QUESTIONS_FAILED,
	GET_EXAM_QUESTIONS_SUCCEDED,
	INSERT_EXAM_QUESTIONS_SUCCEEDED,
	INSERT_EXAM_QUESTIONS_FAILED,
	STUDENT_COURSES_ADDED,
	STUDENT_COURSES_NOT_ADDED,
	STUDENT_SUBJECTS_ADDED,
	STUDENT_SUBJECTS_NOT_ADDED, 
	QUESTION_BY_EXAM_RECIVED,
	SOLVED_EXAM_BY_COURSE_RECIVED,
	SOLVED_EXAM_BY_COURSE_NOT_RECIVED,
	TEAHCER_GET_SOLVED_EXAMS_BY_COURSE_RECIVED,//input: course, output: arraylist of exams types
	TEAHCER_GET_SOLVED_EXAMS_BY_EXAM_TYPE_RECIVED,//input: exam type, output: arraylist of sovledExams
	TEACHER_GET_SOLVED_EXAM_QUESTIONS_BY_STUDENT_RECIVED,//input solvedExam output: arraylist of solvedQuestions.
	TEAHCER_GET_SOLVED_EXAMS_BY_COURSE_NOT_RECIVED,//input: course, output: arraylist of exams types
	TEAHCER_GET_SOLVED_EXAMS_BY_EXAM_TYPE_NOT_RECIVED,//input: exam type, output: arraylist of sovledExams
	TEACHER_GET_SOLVED_EXAM_QUESTIONS_BY_STUDENT_NOT_RECIVED, EXAM_NOT_STARTED_YET,//input solvedExam output: arraylist of solvedQuestions.
	TEACHER_GET_CURRENT_EXAM_NOT_RECIVED,
	TEACHER_GET_CURRENT_EXAMS_RECIVED,
	TECHER_EXAM_IS_DONE,
	TEACHER_GET_REPORT_EXAM_LIST_ADDED,
	TEACHER_GET_REPORT_EXAM_LIST_NOT_ADDED,
	TEACHER_REPORT_DATA_ADDED,
	TEACHER_REPORT_DATA_NOT_ADDED,
	STUDENT_SOLVED_EXAMS_IMPORTED,
	STUDENT_SOLVED_EXAMS_NOT_IMPORTED,
	STUDENT_SOLVED_QUESTIONS_IMPORTED,
	STUDENT_SOLVED_QUESTIONS_NOT_IMPORTED,
	TEACHER_PUBLISH_EXAM_RECIVED,
	TEACHER_PUBLISH_EXAM_NOT_RECIVED,
	TEACHER_REFRSH_ONGOING_EXAM_PAGE,
	STOP_EXAM,
	TEACHER_EDIT_QUESTION_ADDED,
	TEACHER_EDIT_QUESTION_NOT_ADDED,

}
