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
import entity.EntityConstants.OrderStatus;
import entity.Order;
import entity.ParameterUpdate;
import entity.ParkDiscount;
import entity.PersonCEMS;
import entity.ReportDate;
import entity.Subscriber;
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
					if (!returnVal.equals(null)) {
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

		System.out.println("Message received: " + msg + " from " + client);
		System.out.println(userList.toString());
		try {
			if (client != null)
				client.sendToClient(new ServerMessage(type, returnVal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This function gets a canceled order and check the Waiting List for orders
	 * that the date and period of time like order. We get the orders in the Waiting
	 * List with checkWatingList function. In the end we send the notification of
	 * the orders in the queue.
	 * 
	 * @param order the order that is cancelled
	 */
	private void notifyFromWaitingList(Order order) throws NumberFormatException, SQLException, ParseException {
		if (order != null && !order.getStatus().equals(OrderStatus.WAITING)) {
			String parkName = order.getParkName();
			String dateOfOrder = order.getDateOfOrder();
			String timeOfOrder = order.getTimeOfOrder();
			Map<String, Map<String, List<String>>> checkWating = new LinkedHashMap<String, Map<String, List<String>>>();
			Map<String, List<String>> dateAndTime = new LinkedHashMap<String, List<String>>();
			List<String> times = new ArrayList<String>();
			times.add(timeOfOrder);
			dateAndTime.put(dateOfOrder, times);
			checkWating.put(parkName, dateAndTime);
			List<Order> orderList = MySQLConnection.checkWatingList(checkWating);
			sendToAllClients(new ServerMessage(ServerMessageType.WAITING_LIST_APPROVAL_EMAIL_AND_SMS, orderList));
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