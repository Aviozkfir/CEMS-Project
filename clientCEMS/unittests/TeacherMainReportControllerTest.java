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
import gui.TeacherFinalReportControll;
import gui.TeacherMainReportController;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageTypes;

public class TeacherMainReportControllerTest {
   //Stub class for server-client communication.
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

		@Override
		public void setUser(Object user) {
		}

	}
	//Stub class for some of fxml buttons in TeacherMainReportController.
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

	
	//Creating veriables.
	public Object returnVal;
	public ArrayList<String> gradesList;
	ServerMessageTypes type;
	String popUpmsg;
	String popUpError;
	ArrayList<TeachersExam> examList;
	TeachersExam exam;
	TeacherMainReportController teacherMainReportController;
	IServerClientCommunication serverClientCommunicationStub;  //serverClient stub.
	IFxmlManager fxmlManagerStub;         //fxml buttons stub.
	String[] inputData;
	Teacher teacher;
	int PickedYear;

	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception {
		teacherMainReportController = new TeacherMainReportController();
		serverClientCommunicationStub = new ServerClientCommunicationStub();
		fxmlManagerStub = new FxmlManagerStub();
		teacherMainReportController.setServerClientCommunication(serverClientCommunicationStub);  //injection by static method
		teacherMainReportController.setFxmlManager(fxmlManagerStub);  //injection by static method
		exam = new TeachersExam("123456", "exam", "2000-05-03");
		inputData = new String[2];
		gradesList = new ArrayList<String>();
		examList = new ArrayList<TeachersExam>();
		examList.add(exam);
		teacher = new Teacher("Shula", "Bula", "12345678", "Sh@gmail.com", "Teacher");
	}
	// Checking that The user Input (id,date) for the report isnt empty and giving the right message.
	// Input: inputData,examList.
	// Result: False (and Equal on message given :true).
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
	// Checking that The user Input  id is valid (6 digits only!)
	// Input: inputData,examList.
	// Result: False (and Equal on message given :true).
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
	// Checking that The user Input  date is valid ("yyyy-MM-dd" format only).
	// Input: inputData,examList.
	// Result: False (and Equal on message given :true).
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
	// Checking the case when an valid id input that entered is in the list of exams of the current teacher.
	// Input: inputData,examList.
	// Result: True.
	@Test
	public void validateInputIdInList() {
		inputData[0] = "123456";
		inputData[1] = "2000-05-04";
		assertTrue(teacherMainReportController.validateInput(inputData, examList));
	}
	// Checking the case when an valid id input that entered isnt in the list of exams of the current teacher.
	// Input: inputData,examList.
	// Result: False (getting Equal true with the expected error msg.).
	@Test
	public void validateInputIdNotInList() {
		inputData[0] = "123457";
		inputData[1] = "2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));
		assertEquals("Id is not in the list.\n Please insert id from the list.", popUpError);
	}
	// Checking that exception has catched when: the date is too short(throws null),or one of validateinput args are null.
	// Input1: inputData,examList.
	// Input2: null, examList.
	// Input3: inputData, null.
	// Result: False (and Equal on message given :true).
	@Test
	public void validateInputCatchException() {
		inputData[0] = "123456";
		inputData[1] = "2000-05";
		assertFalse(teacherMainReportController.validateInput(inputData, examList));   //input1
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError);
		assertFalse(teacherMainReportController.validateInput(null, examList));        //input2
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError); //input2
		assertFalse(teacherMainReportController.validateInput(inputData, null));       //input3
		assertEquals("Invalid Date input manualy.\nPlease insert legal date or use the button", popUpError);
	}
	
	// Checking successfully getting report data from db by the right type returned from servercems.
	// Input: inputData, teacher.
	// Result: True.
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
	// Checking successfully getting report data from db and calculating Average.
	// Input: inputData,teacher.
	// Result: True.
	@Test
		public void teacherReportDataAddedAndAverageCalculated() {
		    String Expected = "50";
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
				assertEquals(Expected,teacher.getReport().getAverage());
			} catch (IOException e) {
				fail();
			}
	}
	// Checking successfully getting report data from db and calculating Median.
	// Input: inputData,teacher.
	// Result: True.
	@Test
	public void teacherReportDataAddedAndMedianCalculated() {
	    String Expected = "60";
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("70");
		gradesList.add("100");
		PickedYear = 2000;
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData, teacher);
			assertEquals(Expected,teacher.getReport().getMedian());
		} catch (IOException e) {
			fail();
		}
}
	
	// Checking successfully getting report data from db and setting year range.
	// Input: inputData,teacher.
	// Result: True.
	@Test
	public void teacherReportDataAddedAndYearSetted() {
	    String Expected = "2000-2021";
		inputData[0] = "010101";
		inputData[1] = "2000-05-04";
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("70");
		gradesList.add("100");
		PickedYear = 2000;
		type = ServerMessageTypes.TEACHER_REPORT_DATA_ADDED;
		returnVal = gradesList;
		try {
			teacherMainReportController.produceReport(inputData, teacher);
			assertEquals(Expected,teacher.getReport().getYearRange());
		} catch (IOException e) {
			fail();
		}
}
	// Checking unsuccessfully getting report data from db and getting the right msg back from the server.
	// Input: inputData, teacher.
	// Result: assertEquals-True.
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
	// Checking the case when the report data that got back from db is empty and the user get the right msg.
	// Input: inputData, teacher.
	// Result: assertEquals-True.
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
	// Checking the case when produceReport method gets null arguments, and catch a null exception.
	// Input: null, null.
	// Result: Catch null excpetion (assertEquals- true. on nullexception msg).
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
