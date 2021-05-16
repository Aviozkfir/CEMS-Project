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
import entity.ReportDate;
import entity.Subscriber;
import message.ClientMessage;
import message.ServerMessage;
import message.ServerMessageType;
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
		ServerMessageType type = null;
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
					type = ServerMessageType.LOGOUT_SUCCESS;
					break;
				case LOGIN_PERSON:
					returnVal = MySQLConnection.validateVisitor((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal)) // user already logged in
						returnVal = "logged in";
					else if (returnVal != null) // user isn't already logged in and was found in the database
						userList.add(returnVal);
					break;
				case LOGIN_SUBSCRIBER:
					returnVal = MySQLConnection.validateSubscriber((String) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case LOGIN_EMPLOYEE:
					returnVal = MySQLConnection.validateEmployee((String[]) (clientMsg.getMessage()));
					type = ServerMessageType.LOGIN;
					if (userList.contains(returnVal))
						returnVal = "logged in";
					else if (returnVal != null)
						userList.add(returnVal);
					break;
				case GET_PARKS:
					returnVal = MySQLConnection.getParks();
					type = ServerMessageType.PARK_LIST;
				case CONNECTION:
					break;
				case VISITOR_REPORT:
					returnVal = MySQLConnection.getVisitorReport((ReportDate) (clientMsg.getMessage()));
					type = ServerMessageType.PARK_VISITATION_REPORT;
					break;
				case ORDER:
					List<Object> orderDes = (List<Object>) clientMsg.getMessage();
					Order order = (Order) orderDes.get(0);
					Boolean payInAdvance = (Boolean) orderDes.get(1);
					returnVal = MySQLConnection.createOrder(order, payInAdvance);
					if (returnVal == null)
						type = ServerMessageType.ORDER_FAILURE;
					else
						type = ServerMessageType.ORDER_SUCCESS;
					break;
				case WAITING_LIST:
					List<Object> orderDesWaitingList = (List<Object>) clientMsg.getMessage();
					Order orderWaitingList = (Order) orderDesWaitingList.get(0);
					Boolean payInAdvanceWaitingList = (Boolean) orderDesWaitingList.get(1);
					returnVal = MySQLConnection.enterWaitingist(orderWaitingList, payInAdvanceWaitingList);
					type = ServerMessageType.WAITING_LIST;
					break;
				case INCOME_REPORT:
					returnVal = MySQLConnection.getIncomeReport((ReportDate) (clientMsg.getMessage()));
					type = ServerMessageType.PARK_INCOME_REPORT;
					break;
				case PICK_AVAILABLE_DATES:
					returnVal = MySQLConnection.getAvailableDates((Order) clientMsg.getMessage());
					type = ServerMessageType.AVAILABLE_DATES;
					break;
				case PARAMETER_UPDATE:
					returnVal = MySQLConnection.createParameterUpdate((ParameterUpdate) clientMsg.getMessage());
					type = ServerMessageType.PARAMETER_UPDATE;
					break;
				case OCCASIONAL_ORDER:
					returnVal = MySQLConnection.OccasionalcreateOrder((Order) clientMsg.getMessage());
					type = ServerMessageType.OCCASIONAL_ORDER;
					break;
				case DISCOUNT_REQUEST:
					returnVal = MySQLConnection.insertNewDiscountRequest((ParkDiscount) clientMsg.getMessage());
					type = ServerMessageType.DISCOUNT_REQUEST;
					break;
				case PARK_MNG_CAPACITY_REPORT:
					returnVal = MySQLConnection.getParkCapacityReport((String) (clientMsg.getMessage()));
					type = ServerMessageType.PARK_CAPACITY_REPORT;
					break;
				case GET_ORDERS_BY_ID:
					returnVal = MySQLConnection.getUnfinishedOrdersById((String) (clientMsg.getMessage()));
					type = ServerMessageType.GET_ORDERS_BY_ID;
					break;
				case CANCEL_ORDER:
					Order cancelledOrder = MySQLConnection.getCertainOrder((String) (clientMsg.getMessage()));
					returnVal = MySQLConnection.changeOrderStatus((String) (clientMsg.getMessage()),
							OrderStatus.CANCELLED);
					if (((Boolean) returnVal).booleanValue())
						notifyFromWaitingList(cancelledOrder);
					type = ServerMessageType.CANCEL_ORDER;
					break;
				case APPROVE_ORDER:
					returnVal = MySQLConnection.changeOrderStatus((String) (clientMsg.getMessage()),
							OrderStatus.APPROVED);
					type = ServerMessageType.APPROVE_ORDER;
					break;
				case ACTIVATE_ORDER_FROM_WATING_LIST:
					returnVal = MySQLConnection.activateOrderFromWatingList((Order) (clientMsg.getMessage()));
					type = ServerMessageType.ACTIVATE_ORDER_FROM_WATING_LIST;
					break;
				case DEP_MNG_CANCELLATION_REPORT:
					returnVal = MySQLConnection.getCancellationReport();
					type = ServerMessageType.DEPARTMENT_CANCELLATION_REPORT;
					break;

				case DEP_MNG_VISITION_REPORT:
					returnVal = MySQLConnection.getVisitionReport((String) (clientMsg.getMessage()));
					type = ServerMessageType.DEPARTMENT_VISITATION_REPORT;
					break;
				case GET_DISCOUNT_REQUESTS_FROM_DB:
					returnVal = MySQLConnection.getDiscountRequests((String) (clientMsg.getMessage()));
					type = ServerMessageType.GET_DISCOUNT_REQUESTS_FROM_DB;
					break;
				case VALIDATE_ORDER_ENTRY:
					returnVal = MySQLConnection.validateOrderAndReturnPrice((String[]) clientMsg.getMessage());
					type = ServerMessageType.VALIDATE_ORDER_ENTRY;
					break;
				case VALIDATE_ORDER_EXIT:
					returnVal = MySQLConnection.validateOrderAndRegisterExit((String[]) clientMsg.getMessage());
					type = ServerMessageType.VALIDATE_ORDER_EXIT;
					break;

				case REQUESTS_PARAMETERS:
					returnVal = MySQLConnection.getParameterRequests();
					type = ServerMessageType.REQUESTS_PARAMETERS;
					break;
				case DEP_MANAGER_GET_DISCOUNT_REQUESTS:
					returnVal = MySQLConnection.getDepManagerDiscountRequests();
					type = ServerMessageType.REQUESTS_PARAMETERS;
					break;

				case APPROVE_PARAMETER:
					returnVal = MySQLConnection.approveParameterUpdate((ParameterUpdate) (clientMsg.getMessage()));
					type = ServerMessageType.APPROVE_PARAMETER;
					break;
				case DECLINE_PARAMETER:
					returnVal = MySQLConnection.declineParameterUpdate((ParameterUpdate) (clientMsg.getMessage()));
					type = ServerMessageType.DECLINE_PARAMETER;
					break;
				case APPROVE_DISCOUNT:
					returnVal = MySQLConnection.approveDiscountUpdate((ParkDiscount) (clientMsg.getMessage()));
					type = ServerMessageType.APPROVE_DISCOUNT;
					break;
				case DECLINE_DISCOUNT:
					returnVal = MySQLConnection.declineDiscountUpdate((ParkDiscount) (clientMsg.getMessage()));
					type = ServerMessageType.DECLINE_DISCOUNT;
					break;

				case REGISTRATION:
					ServerMessage message = MySQLConnection.registerSubscriber((Subscriber) clientMsg.getMessage());
					returnVal = message.getMessage();
					type = message.getType();
					break;
				case DISCOUNT_VALIDATION:
					ServerMessage discountValidationMessage = MySQLConnection
							.discountValidation((ParkDiscount) (clientMsg.getMessage()));
					returnVal = discountValidationMessage.getMessage();
					type = discountValidationMessage.getType();
					break;
				case GET_PARK:
					returnVal = MySQLConnection.getPark((String) (clientMsg.getMessage()));
					type = ServerMessageType.GET_PARK;
					break;

				}
			}
		} catch (Exception e) {
			try {
				if(client!=null)
					client.sendToClient(new ServerMessage(ServerMessageType.SERVER_ERROR, null));
				e.printStackTrace();
			} catch (IOException e1) {
			}
		}

		System.out.println("Message received: " + msg + " from " + client);
		System.out.println(userList.toString());
		try {
			if(client!=null)
				client.sendToClient(new ServerMessage(type, returnVal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This function gets a canceled order and check the Waiting List for orders that the
	 * date and period of time like order. We get the orders in the Waiting List with
	 * checkWatingList function. In the end we send the notification of the orders
	 * in the queue.
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