package gui;

import java.io.IOException;

import client.ClientCEMS;
import entity.PersonCEMS;
import entity.Principal;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import message.ServerMessageTypes;

/**
 * service class for the client with various service methods this class is
 * implemented as a singleton so that every instance of it holds the same
 * values.
 */
public class GUIControl {
	public static GUIControl instance = new GUIControl();
	private ClientCEMS client;
	private Object currentUser;
	private Stage primaryStage;
	private ServerMessage serverMsg;
	private Object cmpc;
	private int requestCounter = 0;

	

	private GUIControl() {
	}

	public static GUIControl getInstance() {
		return instance;
	}

	public void setController(Object cmpc) {
		this.cmpc = cmpc;
	}

	public Object getController() {
		return cmpc;
	}

	public void setClient(ClientCEMS client) {
		this.client = client;
	}

	public void setStage(Stage stage) {
		primaryStage = stage;
	}

	public Stage getStage() {
		return primaryStage;
	}

	/*
	 * method to send a message to the server that the client is connected to
	 * 
	 * @param msg The message that is sent
	 */
	public void sendToServer(Object msg) {
		client.handleMessageFromClientUI(msg);
	}

	public void setServerMsg(Object msg) {
		serverMsg = (ServerMessage) msg;
	}

	public ServerMessage getServerMsg() {
		return serverMsg;
	}

	public void setUser(Object user) {
		currentUser = user;
	}

	public Object getUser() {
		return currentUser;
	}

	/*
	 * method to disconnect the current client and user (called on sudden
	 * disconnection)
	 */
	public void disconnect() {
		ClientMessage cMsg = new ClientMessage(ClientMessageType.DISCONNECTED, currentUser);
		sendToServer(cMsg);
		currentUser = null;
	}

	/*
	 * method to log out the current user
	 */
	public void logOut() {
		ClientMessage cMsg = new ClientMessage(ClientMessageType.LOGOUT, currentUser);
		sendToServer(cMsg);
		currentUser = null;
	}

	/*
	 * method to open the Log In page
	 */
	public void openLoginPage() {
		try {
	
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEMSlogin.fxml"));
			AnchorPane root = fxmlLoader.load();
			Scene scene = new Scene(root);
			// scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());

			primaryStage.setTitle("CEMS Client Window");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e -> {
				ClientMessage msg = new ClientMessage(ClientMessageType.DISCONNECTED, null);
				client.handleMessageFromClientUI(msg);
			});
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * method that displays an error message in an alert
	 * 
	 * @param msg the message to be displayed
	 */
	public static void popUpError(String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}

	/*
	 * method that displays an error message in an alert and exits the client once
	 * the user exited the error
	 * 
	 * @param msg the message to be displayed
	 */
	public static void popUpErrorExitOnClick(String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.setOnCloseRequest(e -> {
				System.exit(0);
			});
			alert.showAndWait();
		});
	}

	public static void popUpMessage(String msg) {
		popUpMessage("Message", msg);
		}

	/*
	 * method that displays a message in an alert
	 * 
	 * @param title the title of the message
	 * 
	 * @param msg the message
	 */
	public static void popUpMessage(String title, String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}
	
	
	public MainPageController loadStage(String chosenPath) throws IOException {
		MainPageController controller;
		Stage primaryStage = getStage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		controller = (MainPageController) fxmlLoader.getController();
		setController(controller);
		(controller).setUser((PersonCEMS) getUser());
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			disconnect();
		});
		primaryStage.show();
		return controller;
	}
	
	public void loadStage(AnchorPane root) throws IOException {

		Stage primaryStage = getStage();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			disconnect();
		});
		primaryStage.show();
	}

	public int getRequestCount() {
		return requestCounter;
	}
	
	public void SetRequestCount(int requestCounter) {
		this.requestCounter = requestCounter;
	}
	
	public void CountRequest() {

		ClientMessage msg = new ClientMessage(ClientMessageType.PRINCIPAL_CHECK_REQUESTS_COUNTING, ((PersonCEMS) getUser()).getId());
		instance.sendToServer(msg);
		if (instance.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_DIDNT_GOT_REQUESTS_COUNTING) {
			GUIControl.popUpError("There was a problem to check if there is new requests for principal");
		}
	}
	

}
