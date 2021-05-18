package application;
	
import java.io.IOException;

import client.ClientCEMS;
import gui.GUIControl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * main class for running the application,sets up a client,connects to default port and opens the login scene
 */
public class ClientMain extends Application {	                           
	private final int DEFAULT_PORT=5555;
	ClientCEMS client;
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