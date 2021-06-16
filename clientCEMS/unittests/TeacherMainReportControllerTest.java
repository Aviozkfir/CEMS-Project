import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

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
			return new ServerMessage(null, returnVal);
		}

		@Override
		public void popUpError(String msg) {
			popUpError = msg;

		}

		@Override
		public void popUpMessage(String msg) {
			popUpmsg = msg;

		}

	}

	public Object returnVal;
	public ArrayList<String> gradesList = new ArrayList<String>();
	ServerMessageTypes msg;
	String popUpmsg;
	String popUpError;
	boolean isValid;
	Exception exception;
	TeacherMainReportController teacherMainReportController;
	IServerClientCommunication serverClientCommunicationStub;
	String[] inputData = new String[2];
	@SuppressWarnings("static-access")
	@Before
	public void setUp() throws Exception {
		teacherMainReportController = new TeacherMainReportController();
		serverClientCommunicationStub = new ServerClientCommunicationStub();
		teacherMainReportController.setServerClientCommunication(serverClientCommunicationStub);
		exception = null;
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("100");
		isValid=false;

	}

	@Test
	public void validateInputEmpty() {
		inputData[0] ="";
		inputData[1] ="";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Please fill all the required fields.", popUpError);
		inputData[0] ="123";
		inputData[1] ="";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Please fill all the required fields.", popUpError);
		inputData[0] ="";
		inputData[1] ="2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Please fill all the required fields.", popUpError);

	}
	@Test
	public void validateInputIdInvalid() {
		inputData[0] ="abcdef";
		inputData[1] ="2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] ="1234567";
		inputData[1] ="2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] ="12345";
		inputData[1] ="2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
		inputData[0] ="12345#";
		inputData[1] ="2000-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid ID input.\nPlease insert only 6 digits", popUpError);
	}
	@Test
	public void validateInputDateInvalid() {
		inputData[0] ="123456";
		inputData[1] ="200-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] ="123456";
		inputData[1] ="2000-123-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] ="123456";
		inputData[1] ="2000-05-124";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] ="123456";
		inputData[1] ="200a-05-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] ="123456";
		inputData[1] ="2000-b5-04";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);
		inputData[0] ="123456";
		inputData[1] ="2000-05-c4";
		assertFalse(teacherMainReportController.validateInput(inputData,isValid));
		assertEquals("Invalid Date input.\nPlease insert legal date", popUpError);

	
	}

}
