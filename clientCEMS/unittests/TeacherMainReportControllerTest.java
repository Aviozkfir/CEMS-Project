import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import entity.Teacher;
import entity.TeachersExam;
import gui.IServerClientCommunication;
import gui.TeacherMainReportController;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageTypes;

public class TeacherMainReportControllerTest {

	class ServerClientCommunicationStub implements IServerClientCommunication {

		@Override
		public void sendToServer(Object msg) {
			ClientMessage clientMsg = (ClientMessage) msg;
			switch (clientMsg.getType()) {
			case TEACHER_REPORT_DATA:
				returnVal = gradesList;
				break;
			}
		}

		@Override
		public ServerMessage getServerMsg() {
			return new ServerMessage(type, returnVal);
		}

		@Override
		public void popUpError(String msg) {
			popUpError = msg;

		}

		@Override
		public void popUpMessage(String msg) {
			popUpmsg = msg;

		}

		@Override
		public Object getUser() {
			return (Teacher) teacher;
		}

	}

	public Object returnVal;
	public ArrayList<String> gradesList = new ArrayList<String>();
	ServerMessageTypes type;
	String popUpmsg;
	String popUpError;
	ArrayList<TeachersExam> examList;
	TeachersExam exam;
	TeacherMainReportController teacherMainReportController;
	IServerClientCommunication serverClientCommunicationStub;
	String[] inputData = new String[2];
	Teacher teacher = new Teacher("Shula", "Bula", "12345678", "Sh@gmail.com", "Teacher");

	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception {
		teacherMainReportController = new TeacherMainReportController();
		serverClientCommunicationStub = new ServerClientCommunicationStub();
		teacherMainReportController.setServerClientCommunication(serverClientCommunicationStub);
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("100");
		exam = new TeachersExam("123456", "exam", "2000-05-03");
		examList.add(exam);

	}

	@Test
	public void validateInputEmpty() {
		inputData[0] = "";
		inputData[1] = "";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Please fill all the required fields.", popUpError);
		inputData[0] = "123";
		inputData[1] = "";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Please fill all the required fields.", popUpError);
		inputData[0] = "";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Please fill all the required fields.", popUpError);

	}

	@Test
	public void validateInputIdInvalid() {
		inputData[0] = "abcdef";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] = "1234567";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] = "12345";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] = "12345#";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
	}

	@Test
	public void validateInputDateInvalid() {
		inputData[0] = "123456";
		inputData[1] = "200-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "2000-123-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "2000-05-124";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "200a-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "2000-b5-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "2000-05-c4";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);

	}

	@Test
	public void teacherReportDataAdded() {
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData);
			assertTrue(true);
		} catch (IOException e) {
			fail();
		}
		
	}
}
