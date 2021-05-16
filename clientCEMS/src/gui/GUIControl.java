package gui;

import client.ClientCEMS;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
/**
 * service class for the client with various service methods
 * this class is implemented as a singleton so that every instance of it holds the same values.
 */
public class GUIControl {
	private static GUIControl instance=new GUIControl();
	private  ClientCEMS client;
	private  Object currentUser;
	private  Stage primaryStage;
	private  ServerMessage serverMsg;
	private ClientMainPageController cmpc;
	private GUIControl() {}
	public static GUIControl getInstance() {
		return instance;
	}
	public void setClientMainPageController(ClientMainPageController cmpc)
	{
		this.cmpc=cmpc;
	}
	public ClientMainPageController getClientMainPageController()
	{
		return cmpc;
	}
	public  void setClient(ClientCEMS client) {
		this.client=client;
	}
	public  void setStage(Stage stage) {
		primaryStage=stage;
	}
	public Stage getStage() {
		return primaryStage;
	}
	/*
	 * method to send a message to the server that the client is connected to
	 * @param msg The message that is sent
	 */
	public void sendToServer(Object msg) {
		client.handleMessageFromClientUI(msg);
	}
	public void setServerMsg(Object msg) {
		serverMsg=(ServerMessage)msg;
	}
	public ServerMessage getServerMsg() {
		return serverMsg;
	}
	public void setUser(Object user) {
		currentUser=user;
	}
	public Object getUser() {
		return currentUser;
	}
	/*
	 * method to disconnect the current client and user (called on sudden disconnection)
	 */
	public void disconnect() {
		ClientMessage cMsg=new ClientMessage(ClientMessageType.DISCONNECTED,currentUser);
		sendToServer(cMsg);
		currentUser=null;
	}
	/*
	 * method to log out the current user 
	 */
	public void logOut() {
		ClientMessage cMsg=new ClientMessage(ClientMessageType.LOGOUT,currentUser);
		sendToServer(cMsg);
		currentUser=null;
	}
	/*
	 * method to open the Log In page
	 */
	public void openLoginPage() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ClientConstants.Screens.LOGIN_PAGE.toString()));
			AnchorPane root = fxmlLoader.load();
			Scene scene = new Scene (root);
			primaryStage.setTitle("CEMS");
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e->{
				ClientMessage msg=new ClientMessage(ClientMessageType.DISCONNECTED,null);
				client.handleMessageFromClientUI(msg);
			});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * method that displays an error message in an alert
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
	 * method that displays an error message in an alert and exits the client once the user exited the error
	 * @param msg the message to be displayed
	 */
	public static void popUpErrorExitOnClick(String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.setOnCloseRequest(e->{
				System.exit(0);
			});
			alert.showAndWait();
		});
	}
	public static void popUpMessage(String msg) {
		popUpMessage("Message",msg);
	}
	/*
	 * method that displays a message in an alert
	 * @param title the title of the message
	 * @param msg the message
	 */
	public static void popUpMessage(String title,String msg)
	{
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText("");
			alert.setContentText(msg);
			alert.showAndWait();
		});
	}

	


}
