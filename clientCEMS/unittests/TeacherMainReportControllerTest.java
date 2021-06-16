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
import gui.IFxmlManager;
import gui.IServerClientCommunication;
import gui.MainPageController;
import gui.TeacherFinalReportControll;
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

	class FxmlManagerStub implements IFxmlManager {

		@Override
		public int setPickedYear() {
			return PickedYear;
		}

		@Override
		public TeacherFinalReportControll loadStage(String path) throws IOException {
			TeacherFinalReportControll difController = new TeacherFinalReportControll();
			return difController;
		}

	}

	public Object returnVal;
	public ArrayList<String> gradesList;
	ServerMessageTypes type;
	String popUpmsg;
	String popUpError;
	ArrayList<TeachersExam> examList;
	TeachersExam exam;
	TeacherMainReportController teacherMainReportController;
	IServerClientCommunication serverClientCommunicationStub;
	IFxmlManager fxmlManagerStub;
	String[] inputData;
	Teacher teacher;
	int PickedYear;

	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception {
		teacherMainReportController = new TeacherMainReportController();
		serverClientCommunicationStub = new ServerClientCommunicationStub();
		fxmlManagerStub = new FxmlManagerStub();
		teacherMainReportController.setServerClientCommunication(serverClientCommunicationStub);
		teacherMainReportController.setFxmlManager(fxmlManagerStub);
		exam = new TeachersExam("123456", "exam", "2000-05-03");
		inputData = new String[2];
		gradesList = new ArrayList<String>();
		examList = new ArrayList<TeachersExam>();
		examList.add(exam);
		teacher = new Teacher("Shula", "Bula", "12345678", "Sh@gmail.com", "Teacher");
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
		inputData[0] = "123456";
		inputData[1] = "2000-05-04-03";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] = "123456";
		inputData[1] = "2000/05/04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);

	}

	@Test
	public void validateInputIdInList() {
		inputData[0] = "123456";
		inputData[1] = "2000-05-04";
		assertTrue(teacherMainReportController.validateInput(inputData, examList));
	}

	@Test
	public void validateInputIdNotInList() {
		inputData[0] = "123457";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Id is not in the list.\n Please insert id from the list.", popUpError);
	}

	@Test
	public void validateInputCatchException() {
		inputData[0] = "123456";
		inputData[1] = "2000-05";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError);
		assertFalse(teacherMainReportController.validateInput(null, examList));
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError);
		assertFalse(teacherMainReportController.validateInput(inputData, null));
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError);
	}

	@Test
	public void teacherReportDataAdded() {
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("100");
		PickedYear = 2000;
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData, teacher);
			assertTrue(true);
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void teacherReportDataNotAdded() {
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("100");
		PickedYear = 2000;
		type = ServerMessageTypes.TEACHER_REPORT_DATA_NOT_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData, teacher);
			assertEquals("Error in loading the report data", popUpError);
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void teacherReportDataIsEmpty() {
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		PickedYear = 2000;
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData, teacher);
			assertEquals("The chosen Id and Date range for the report contains 0 solved exams.", popUpmsg);
		} catch (IOException e) {
			fail();
		}
	}

	@Test
	public void teacherReportDataIsNull() {
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		gradesList.add("0");
		try {
			teacherMainReportController.produceReport(null, null);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), null);
		}
	}

}
