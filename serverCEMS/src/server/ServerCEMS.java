// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import application.ServerMain;
import entity.Course;
import entity.Exam;
import entity.PersonCEMS;
import entity.Question;
import entity.QuestionInExam;
import entity.SolvedExam;
import entity.Student;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageTypes;
import mySQL.MySQLConnection;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 
 
 
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
/**
 * GoNatureServer is responsible to handling client messages and sending
 * messages to client
 */
public class ServerCEMS extends AbstractServer {
	// Class variables *****************
	private MySQLConnection cemsDataBase;

	private ArrayList<ConnectionToClient> conToClientMng;
	private ArrayList<currentExam> currentExams;
	// Constructors ******************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public ServerCEMS(int port) {
		super(port);

		conToClientMng=new ArrayList<>();
		currentExams = new ArrayList<>();
	}

	// Instance methods ****************

	
	
	
	
	private void removeStudentFromTest(Exam exam, ConnectionToClient client) throws IOException {
		Iterator<currentExam> it=currentExams.iterator();
		currentExam ce=null;
		while(it.hasNext())
			ce=it.next();
			if(ce.getEid().equals(exam.getEid())) {
				ce.getConToClientStudent().remove(client);
				
				if(ce.allStudentAreFinished()) {
					currentExams.remove(ce);
					ce.getTeacher().sendToClient(new ServerMessage(ServerMessageTypes.TEACHER_REFRSH_ONGOING_EXAM_PAGE, ce.getEid()));
				}
					
			}
	}
	
	
	
	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Object returnVal = null;
		ServerMessageTypes type = null;

		try {
			if (msg instanceof ClientMessage) {
				ClientMessage clientMsg = (ClientMessage) msg;
				switch (clientMsg.getType()) {
				case DISCONNECTED:
					if (clientMsg.getMessage() != null)
//						userList.remove(clientMsg.getMessage());
					ServerMain.guiController.disconnectClient(client);
					break;
				case LOGOUT:
					if (clientMsg.getMessage() != null)
//						userList.remove(clientMsg.getMessage());
					returnVal = null;
					type = ServerMessageTypes.LOGOUT_SUCCESS;
					break;
				case LOGIN_PERSON:
					returnVal = MySQLConnection.validatePerson((String[]) (clientMsg.getMessage()));
					if (returnVal != null) {
						PersonCEMS returnPerson = (PersonCEMS) returnVal;
						switch (returnPerson.getRole()) {
						case "Teacher":
							type = ServerMessageTypes.LOGIN_TEACHER;
						case "Student":
							type = ServerMessageTypes.LOGIN_STUDENT;
						case "Principal":
							type = ServerMessageTypes.LOGIN_PRINCIPAL;
							conToClientMng.add(client);	
						}
//						if (userList.contains(returnVal)) // user already logged in
//							returnVal = "logged in";
//						else if (returnVal != null) // user isn't already logged in and was found in the database
//							userList.add(returnVal);
						break;

					} else {
						type = ServerMessageTypes.LOGIN_PERSON_NOT_FOUND;
					}
				case TEACHER_SUBJECTS_INFORMATION:
					returnVal = MySQLConnection.getTeacherSubjects((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.TEACHER_SUBJECTS_ADDED;
					} else {
						type = ServerMessageTypes.TEACHER_SUBJECTS_NOT_ADDED;
					}
					break;
				case TEACHER_COURSES_INFORMATION:
					returnVal = MySQLConnection.getTeacherCourses((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.TEACHER_COURSES_ADDED;
					} else {
						type = ServerMessageTypes.TEACHER_COURSES_NOT_ADDED;
					}
					break;
				case TEACHER_PUBLISH_EXAM:
					
					type = ServerMessageTypes.TEACHER_PUBLISH_EXAM_RECIVED;
					Exam exam1= (Exam) clientMsg.getMessage();
					for(currentExam oce: currentExams)
						if(oce.getEid().equals(exam1.getEid()))
							type = ServerMessageTypes.TEACHER_PUBLISH_EXAM_NOT_RECIVED;
					if(type == ServerMessageTypes.TEACHER_PUBLISH_EXAM_RECIVED)
						currentExams.add(new currentExam(exam1,MySQLConnection.numOfStudentsInCourse(exam1.getCid()),client));
					break;
					
				case PRINCIPAL_SUBJECTS_INFORMATION:
					returnVal = MySQLConnection.getPrincipalSubjects((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_SUBJECTS_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_SUBJECTS_NOT_ADDED;
					}
					break;
				case PRINCIPAL_COURSES_INFORMATION:
					returnVal = MySQLConnection.getPrincipalCourses((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_COURSES_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_COURSES_NOT_ADDED;
					}
					break;
				case PRINCIPAL_REPORT_COURSES_INFORMATION:
					returnVal = MySQLConnection.getPrincipalReportCourses((String[]) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_REPORT_COURSES_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_REPORT_COURSES_NOT_ADDED;
					}
					break;
				case VALIDATE_EXAM_CODE:
					
					returnVal = MySQLConnection.validateExamCode((String) clientMsg.getMessage());
					if ((boolean) returnVal == false) {
						type = ServerMessageTypes.EXAM_CODE_NOT_FOUND;
					} else {
						
						type = ServerMessageTypes.EXAM_CODE_FOUND;
					}
					break;
				case GET_EXAM_INFORMATION:
					type = ServerMessageTypes.EXAM_NOT_STARTED_YET;
					returnVal = MySQLConnection.getExamInformation((String) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.EXAM_INFORMATION_NOT_RECIVED;
					} else {
						Iterator<currentExam> itce = currentExams.iterator();
						while(itce.hasNext()) {
							currentExam ce =itce.next();
							if(((Exam)returnVal).getEid().equals(ce.getEid())) {
									type = ServerMessageTypes.EXAM_INFORMATION_RECIVED;
									ce.getConToClientStudent().add(client);

							}
						}
						if(type != ServerMessageTypes.EXAM_INFORMATION_RECIVED)
							returnVal=null;
						
					}
					break;

				case GET_QUESTION_BY_COURSE:
					returnVal = MySQLConnection.getQuestionByCourse((Course) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.QUESTION_BY_COURSE_NOT_RECIVED;
					} else {
						type = ServerMessageTypes.QUESTION_BY_COURSE_RECIVED;
					}
					break;
				case TEACHER_ADD_QUESTION:
					type = ServerMessageTypes.QUESTION_ADDED;
					Object[] arr = (Object[]) clientMsg.getMessage();
					try {
						MySQLConnection.addQuestionByCourses((Question) arr[0], (ArrayList<Course>) arr[1]);
					} catch (Exception e1) {
						type = ServerMessageTypes.QUESTION_NOT_ADDED;
						e1.printStackTrace();
					}
					break;
				case TEACHER_DELETE_QUESTION:
					type = ServerMessageTypes.QUESTION_DELETED;
					Question q = (Question) clientMsg.getMessage();
					try {
						MySQLConnection.deletedQuestion(q);
					} catch (Exception e1) {
						type = ServerMessageTypes.QUESTION_NOT_DELETED;
						e1.printStackTrace();
					}
					break;
				case GET_EXAM_BY_COURSE:
					type = ServerMessageTypes.EXAM_BY_COURSE_RECIVED;
					Course c = (Course) clientMsg.getMessage();
					try {
						returnVal = MySQLConnection.getTeacherExamByCourse(c);
					} catch (Exception e1) {
						type = ServerMessageTypes.EXAM_BY_COURSE_NOT_RECIVED;
						e1.printStackTrace();
					}
					break;
				case GET_SOLVED_EXAM_BY_COURSE:
					type = ServerMessageTypes.SOLVED_EXAM_BY_COURSE_RECIVED;
					
					try {
						returnVal = MySQLConnection.getTeacherSolvedExamByCourse((Course) clientMsg.getMessage());
					} catch (Exception e1) {
						type = ServerMessageTypes.SOLVED_EXAM_BY_COURSE_NOT_RECIVED;
						e1.printStackTrace();
					}
					break;
				case TEACHER_ADD_EXAM:
					type = ServerMessageTypes.EXAM_ADDED;
					Object[] examInfo = (Object[]) clientMsg.getMessage();
					try {
						returnVal = MySQLConnection.addExam((Exam)examInfo[0],(ArrayList<QuestionInExam>)examInfo[1]);
					} catch (Exception e1) {
						type = ServerMessageTypes.EXAM_NOT_ADDED;
						e1.printStackTrace();
					}
			
					break;
				case PRINCIPAL_STUDENTS_INFORMATION:
					returnVal = MySQLConnection.getPrincipalStudentList();
					if (returnVal == null) {
						type = ServerMessageTypes.PRINCIPAL_STUDENTS_NOT_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_STUDENTS_ADDED;
					}
					break;

				case PRINCIPAL_TEACHERS_INFORMATION:
					returnVal = MySQLConnection.getPrincipalTeacherList();
					if (returnVal == null) {
						type = ServerMessageTypes.PRINCIPAL_TEACHERS_NOT_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_TEACHERS_ADDED;
					}
					break;

				case PRINCIPAL_REPORT_TEACHER_INFORMATION:
					returnVal = MySQLConnection.getPrincipalReportTeachers((String[]) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_REPORT_TEACHER_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_REPORT_TEACHER_NOT_ADDED;
					}
					break;

				case PRINCIPAL_REPORT_STUDENT_INFORMATION:
					returnVal = MySQLConnection.getPrincipalReportStudents((String[]) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_REPORT_STUDENT_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_REPORT_STUDENT_NOT_ADDED;
					}
					break;

				case PRINCIPAL_REQUESTS_INFORMATION:
					returnVal = MySQLConnection.getPrincipalRequests();
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_REQUESTS_ADDED;
					} else {
						type = ServerMessageTypes.PRINCIPAL_REQUESTS_NOT_ADDED;
					}
					break;

				case PRINCIPAL_APPROVED_REQUESTS_UPDATE:
					returnVal = MySQLConnection
							.updatePrincipalApprovedRequests((ArrayList<String>) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_APPROVED_REQUESTS_ADDED;
						for(currentExam ce: currentExams)
							for(int i=0; i< ((ArrayList<String>) clientMsg.getMessage()).size();i++) {
								if(ce.getEid().equals(((ArrayList<String>) clientMsg.getMessage()).get(i))) {
									String newTime = ((ArrayList<String>) returnVal).get(i);
									for(ConnectionToClient cl : ce.getConToClientStudent())
										cl.sendToClient(new ServerMessage(ServerMessageTypes.STUDENT_EXTEND_TIME, newTime));
									ce.getTeacher().sendToClient(new ServerMessage(ServerMessageTypes.TEACHER_REFRSH_ONGOING_EXAM_PAGE, ((ArrayList<String>) clientMsg.getMessage()).get(i)));
								}
							}
					}
					break;

				case PRINCIPAL_DECLINED_REQUESTS_UPDATE:
					returnVal = MySQLConnection
							.updatePrincipalDeclinedRequests((ArrayList<String>) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_DECLINED_REQUESTS_ADDED;
					}
					break;

				case PRINCIPAL_CHECK_REQUESTS_COUNTING:
					returnVal = MySQLConnection.getRequestCount();
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_GOT_REQUESTS_COUNTING;
					}
					break;

				case PRINCIPAL_UPDATE_REQUESTS_STATUS:
					returnVal = MySQLConnection
							.updatePrincipalRequestStatus((ArrayList<String>) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_UPDATE_REQUESTS_STATUS_SUCCESS;
						
					}
					break;

				case VALIDATE_STUDENT_ID:
					returnVal = MySQLConnection.validateStudentID((String) clientMsg.getMessage());
					if ((boolean) returnVal == false) {
						type = ServerMessageTypes.STUDENT_ID_NOT_FOUND;
					} else {
						type = ServerMessageTypes.STUDENT_ID_FOUND;
					}
					break;
				case UPLOAD_MANUAL_EXAM:
					Object[] t= (Object[]) clientMsg.getMessage();
					removeStudentFromTest((Exam) t[3],client);
					returnVal = MySQLConnection.uploadManualExam((Object[]) clientMsg.getMessage());
					if ((boolean) returnVal == true) {
						type = ServerMessageTypes.EXAM_UPLOADED_SUCCECFULLY;
						
					} else {
						type = ServerMessageTypes.EXAM_UPLOADING_FAILED;

					}
					
					break;
				case UPLOAD_TEACHER_MANUAL_EXAM:
					type = ServerMessageTypes.TEACHER_EXAM_UPLOADED_SUCCECFULLY;
					Object[] EaF = (Object[]) clientMsg.getMessage();
					try {
					returnVal = MySQLConnection.addManualExam((Exam)EaF[0],(byte[])EaF[1]);
					}catch (Exception e1) {
						e1.printStackTrace();
						type = ServerMessageTypes.TEACHER_EXAM_UPLOADING_FAILED;
					}
					break;
				case DOWNLOAD_MANUAL_EXAM:
					returnVal = MySQLConnection.downloadManualExam((Object[]) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.EXAM_DOWNLOAD_FAIL;
					} else {
						type = ServerMessageTypes.EXAM_DOWNLOAD_SUCCESS;
					}

					break;
				case TEACHER_GET_CURRENT_EXAM:
					ArrayList<Exam> returnValAsList = new ArrayList<Exam>();
					
					for( currentExam myce : currentExams)
						if(myce.getTeacher()==client) {
							returnValAsList.add(myce.getExam());
						}
					
					type = ServerMessageTypes.TEACHER_GET_CURRENT_EXAMS_RECIVED;
						
					returnVal=returnValAsList;

					break;
				case GET_EXAM_QUESTIONS:
					returnVal = MySQLConnection.returnExamQuestions((String) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.GET_EXAM_QUESTIONS_FAILED;
					} else {
						type = ServerMessageTypes.GET_EXAM_QUESTIONS_SUCCEDED;
					}

					break;
				case INSERT_EXAM_TO_DB:
					returnVal = MySQLConnection.insertExamToDB((SolvedExam) clientMsg.getMessage());
					removeStudentFromTest((Exam) clientMsg.getMessage(), client);
					if (returnVal == null) {
						type = ServerMessageTypes.GET_EXAM_QUESTIONS_FAILED;
						
					} else {
						type = ServerMessageTypes.GET_EXAM_QUESTIONS_SUCCEDED;
						
					}

					break;
				case TEACHER_GET_REPORT_EXAM_LIST:
					returnVal = MySQLConnection.getTeacherExamList((String) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.TEACHER_GET_REPORT_EXAM_LIST_NOT_ADDED;
					} else {
						type = ServerMessageTypes.TEACHER_GET_REPORT_EXAM_LIST_ADDED;
					}
					break;
				case TEACHER_REPORT_DATA:
					returnVal = MySQLConnection.getTeacherSolvedExamReport((String[]) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
					} else {
						type = ServerMessageTypes.TEACHER_REPORT_DATA_NOT_ADDED;
					}
					break;
				case INSERT_EXAM_QUESTIONS:

					returnVal = MySQLConnection.insertExamQuestions((Object[]) clientMsg.getMessage());
					if ((boolean) returnVal == true) {
						type = ServerMessageTypes.INSERT_EXAM_QUESTIONS_SUCCEEDED;
						
					} else {
						type = ServerMessageTypes.INSERT_EXAM_QUESTIONS_FAILED;
					}

					break;
				case TEACHER_CHECK_VALID_CODE:

					
					if (MySQLConnection.teacherValidExamCode((String) clientMsg.getMessage())) {
						type = ServerMessageTypes.TEACHER_VALID_CODE;
					} else {
						type = ServerMessageTypes.TEACHER_INVALID_CODE;
					}

					break;
				case TEAHCER_GET_SOLVED_EXAMS_BY_COURSE:

					type = ServerMessageTypes.TEAHCER_GET_SOLVED_EXAMS_BY_COURSE_RECIVED;
					try {
						returnVal=MySQLConnection.getTeacherSolvedExamByCourse((Course) clientMsg.getMessage());
					}catch (Exception e) {
						type = ServerMessageTypes.TEAHCER_GET_SOLVED_EXAMS_BY_COURSE_NOT_RECIVED;
						
						e.printStackTrace();
						
					}
					break;
				case STUDENT_SUBJECTS_INFORMATION:
					returnVal = MySQLConnection.getStudentSubjects((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.STUDENT_SUBJECTS_ADDED;
					} else {
						type = ServerMessageTypes.STUDENT_SUBJECTS_NOT_ADDED;
					}

					break;
				case STUDENT_COURSES_INFORMATION:
					returnVal = MySQLConnection.getStudentCourses((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.STUDENT_COURSES_ADDED;
					} else {
						type = ServerMessageTypes.STUDENT_COURSES_NOT_ADDED;
					}
					break;
					
				case GET_QUESTION_BY_EXAM:
					returnVal = MySQLConnection.getQuestionByExam((Exam) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.QUESTION_BY_EXAM_NOT_RECIVED;
					} else {
						type = ServerMessageTypes.QUESTION_BY_EXAM_RECIVED;
					}
					break;
				case GET_QUESTIONS_FOR_SOLVED_EXAM:
					returnVal = MySQLConnection.getQuestionsForExamSEid((String) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.STUDENT_SOLVED_QUESTIONS_IMPORTED;
					} else {
						type = ServerMessageTypes.STUDENT_SOLVED_QUESTIONS_NOT_IMPORTED;
					}
					
					break;
				case GET_SOLVED_EXAMS:
					returnVal = MySQLConnection.getStudentExamCourses((Student) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.STUDENT_SOLVED_EXAMS_IMPORTED;
					} else {
						type = ServerMessageTypes.STUDENT_SOLVED_EXAMS_NOT_IMPORTED;
					}
					break;
				case TEACHER_LOCK_EXAM:
					
					Iterator<currentExam> it= currentExams.iterator();
					while(it.hasNext()) {
						currentExam dce =it.next();
						if(dce.getTeacher().equals(client)&&dce.getEid().equals(((Exam)clientMsg.getMessage()).getEid())) {
							it.remove();
							type=ServerMessageTypes.TEACHER_REFRSH_ONGOING_EXAM_PAGE;
							
							for(ConnectionToClient ctc:dce.getConToClientStudent())
								ctc.sendToClient(new ServerMessage(ServerMessageTypes.STOP_EXAM, clientMsg.getMessage()));
						}
					}
					break;
					
				case TEACHER_SEND_REQUEST:
					returnVal = MySQLConnection.sendNewRequest((ArrayList<String>) clientMsg.getMessage());
					
					if ((boolean) returnVal) {
						type = ServerMessageTypes.TEACHER_REQUEST_RECIVED;
						for(ConnectionToClient mn : conToClientMng)
							mn.sendToClient(new ServerMessage(ServerMessageTypes.PRINCIPAL_GOT_NEW_REQUEST, null));
					}
					else {
						type = ServerMessageTypes.TEACHER_REQUEST_NOT_RECIVED;
					}
					break;
			
			
				}
			}
		} catch (Exception e) {
			try {
				if (client != null)
					client.sendToClient(new ServerMessage(ServerMessageTypes.SERVER_ERROR, null));
				e.printStackTrace();
			} catch (IOException e1) {
			}
		}

		// System.out.println("Message received: " + msg + " from " + client);
		// System.out.println(userList.toString());
		try {
			if (client != null)
				client.sendToClient(new ServerMessage(type, returnVal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		
		
		ServerMain.guiController.connectClient(client);
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		ServerMain.guiController.disconnectClient(client);
	}
}
//End of EchoServer class