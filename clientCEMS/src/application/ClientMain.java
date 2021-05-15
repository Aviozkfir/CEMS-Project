package application;
	
import java.io.IOException;
import java.net.ConnectException;

import client.ClientCEMS;
import gui.GUIControl;
import gui.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

/**
 * main class for running the application,sets up a client,connects to default port and opens the login scene
 */
public class ClientMain extends Application {
	private final int DEFAULT_PORT=5555;
	ClientCEMS client;
	String hi="sadsadsad";
	String change="Change";
	GUIControl guiControl=GUIControl.getInstance();
	@Override
	public void start(Stage primaryStage) {
		try {
			client=new ClientCEMS("localhost",DEFAULT_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		guiControl.setClient(client);
		guiControl.setStage(primaryStage);
		guiControl.openLoginPage();
		try {
			client.openConnection();
		}catch(IOException ex) {
			GUIControl.popUpErrorExitOnClick("Caution!\nServer is not running.\nYour client will now close,please try reopening it later.");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}