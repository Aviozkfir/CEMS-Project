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
	QUESTION_IS_ALREADY_IN_DATABASE,
	QUESTION_ADDED,
	QUESTION_NOT_ADDED,
	QUESTION_DELETED,
	QUESTION_NOT_DELETED,
	QUESTION_NOT_DELETED_IN_USE,
	
	AVAILABLE_DATES,
	PARK_LIST,
	PARK_VISITATION_REPORT,
	PARK_CAPACITY_REPORT,
	PARK_INCOME_REPORT,
	DEPARTMENT_VISITATION_REPORT,
	DEPARTMENT_CANCELLATION_REPORT,
	ORDER_SUCCESS,
	ORDER_FAILURE,
	PARAMETER_UPDATE,
	WAITING_LIST,
	DISCOUNT_REQUEST,
	OCCASIONAL_ORDER,
	GET_ORDERS_BY_ID,
	CANCEL_ORDER,
	APPROVE_ORDER,
	ACTIVATE_ORDER_FROM_WATING_LIST,
	GET_DISCOUNT_REQUESTS_FROM_DB,
	VALIDATE_ORDER_ENTRY,
	VALIDATE_ORDER_EXIT,
	REQUESTS_PARAMETERS,
	DEP_MANAGER_GET_DISCOUNT_REQUESTS,
	APPROVE_PARAMETER,
	DECLINE_PARAMETER,
	APPROVE_DISCOUNT,
	DECLINE_DISCOUNT,
	REGISTRATION_SUCCESSED,
	REGISTRATION_FAILED,
	FINAL_APPROVAL_EMAIL_AND_SMS,
	WAITING_LIST_APPROVAL_EMAIL_AND_SMS,
	CANCEL_EMAIL_AND_SMS,
	DISCOUNT_IS_ALREADY_EXIST,
	DISCOUNT_EXIST_BETWEEN_DATES,
	CAN_INSERT_NEW_DISCOUNT,
	GET_PARK,
	WAITING_DISCOUNT, 

}
