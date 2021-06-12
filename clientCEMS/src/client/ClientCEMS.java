package client;

import java.io.IOException;
import gui.GUIControl;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import message.ServerMessageTypes;
import ocsf.client.AbstractClient;

/**
 * This class controls the communication between the client and the server using
 * handleMessageFromClientUI and handleMessageFromClient
 */
public class ClientCEMS extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	public static boolean awaitResponse = false;
	private GUIControl guiControl;
	
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ClientCEMS(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		guiControl = GUIControl.getInstance();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 * @throws Exception
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.print("--> handleMessageFromServer : ");
		if (msg instanceof ServerMessage) {
			ServerMessage serverMsg = (ServerMessage) msg;
			switch (serverMsg.getType()) {
			case LOGIN_TEACHER:
				guiControl.setServerMsg(serverMsg);
				break;
			case LOGIN_PERSON_NOT_FOUND:
				if (serverMsg.getMessage() == null) {
					guiControl.setServerMsg(serverMsg);
				} else {
					GUIControl.popUpErrorExitOnClick((String) serverMsg.getMessage());
				}
				break;
			case LOGOUT_SUCCESS:
				GUIControl.popUpMessage("Logged out");
				break;
			case PRINCIPAL_GOT_REQUESTS_COUNTING:
				guiControl.setServerMsg(serverMsg);
				guiControl.SetRequestCount((int) serverMsg.getMessage());
//				PrincipalMainPageController controller = (PrincipalMainPageController) guiControl.getController();
//				controller.setRequestCounter((int) serverMsg.getMessage());
				break;
			case QUESTION_BY_COURSE_RECIVED:

				guiControl.getUpdateThread().Check();
				break;
				
			case TECHER_EXAM_IS_DONE:
				break;
				
			
			default:
				guiControl.setServerMsg(serverMsg);
				break;

			}
		}
		awaitResponse = false;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();// in order to send more than one message
			if (message instanceof ClientMessage) {
				if (((ClientMessage) message).getType().equals(ClientMessageType.DISCONNECTED)) {
					sendToServer(message);
					return;
				}
			}
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			quit();
		}
	}
	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}


}
