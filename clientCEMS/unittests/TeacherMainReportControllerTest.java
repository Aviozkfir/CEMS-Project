import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import gui.TeacherMainReportController;
import gui.IServerClientCommunication;
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

	}

	public Object returnVal;
	public ArrayList<String> gradesList = new ArrayList<String>();
	String date = "2000-04-05";
	String ID = "123";
	ServerMessageTypes msg;
	String[] inputData=new String[2];

	@Before
	public void setUp() throws Exception {
		ServerClientCommunicationStub serverClientCommunicationStub = new ServerClientCommunicationStub();
		TeacherMainReportController.setServerClientCommunication(serverClientCommunicationStub);
		gradesList.add("0");
		gradesList.add("50");
		gradesList.add("100");

	}

	@Test
	public void ReportsDateIsEmpty() {

	}

}
