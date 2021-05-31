// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.ServerMain;
import entity.Course;
import entity.PersonCEMS;
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
	private ArrayList<Object> userList;

	// Constructors ******************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port The port number to connect on.
	 * 
	 */
	public ServerCEMS(int port) {
		super(port);
		userList = new ArrayList<>();
	}

	// Instance methods ****************

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
						userList.remove(clientMsg.getMessage());
					ServerMain.guiController.disconnectClient(client);
					break;
				case LOGOUT:
					if (clientMsg.getMessage() != null)
						userList.remove(clientMsg.getMessage());
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
						}
						if (userList.contains(returnVal)) // user already logged in
							returnVal = "logged in";
						else if (returnVal != null) // user isn't already logged in and was found in the database
							userList.add(returnVal);
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
					returnVal = MySQLConnection.getExamInformation((String) clientMsg.getMessage());
					if (returnVal == null) {
						type = ServerMessageTypes.EXAM_INFORMATION_NOT_RECIVED;
					} else {
						type = ServerMessageTypes.EXAM_INFORMATION_RECIVED;
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
					returnVal = MySQLConnection.updatePrincipalApprovedRequests((ArrayList<String>) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_APPROVED_REQUESTS_ADDED;
					}
					break;
					
				case PRINCIPAL_DECLINED_REQUESTS_UPDATE:
					returnVal = MySQLConnection.updatePrincipalDeclinedRequests((ArrayList<String>) clientMsg.getMessage());
					if (returnVal != null) {
						type = ServerMessageTypes.PRINCIPAL_DECLINED_REQUESTS_ADDED;
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