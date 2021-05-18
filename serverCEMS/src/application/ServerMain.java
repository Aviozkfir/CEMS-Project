package application;
	
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gui.ServerScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import message.ServerMessage;
import message.ServerMessageTypes;
import mySQL.MySQLConnection;
import server.ServerCEMS;

/**
 * main class for running the application,opens main server screen
 */
public class ServerMain extends Application {
	public static ServerScreenController guiController;
	public static ServerCEMS server;
	final public static int DEFAULT_PORT = 5555;
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader =  new FXMLLoader(getClass().getResource("/gui/ServerScreen.fxml"));
		Parent root = loader.load();
		guiController = loader.getController();
		Scene serverScene = new Scene(root);
		primaryStage.setTitle("CEMS Server Window");
		//serverScene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		primaryStage.setScene(serverScene);
		primaryStage.setOnCloseRequest(e -> stopServer());//make sure safe shutdown
		primaryStage.show();
	}
	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	/**
	 * static method that upon being called,sets up a server connection to default port and connects to database
	 */
	public static boolean runServer(){
	    	if(server==null)
	    		server = new ServerCEMS(DEFAULT_PORT);
	        try 
	        {
	        	server.listen(); //Start listening for connections
	        	guiController.serverConnected();
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	          showError("Server encountered an issue and cannot run,please try again later");
	          return false;
	        }
	        try {
	        	 MySQLConnection.connectToDB();
	        	 guiController.dataBaseConnected();
	        }catch(Exception e) {
	        	showError("Could not connect to SQL, please try again later");
	        	server.stopListening();
	        	return false;
	        }
	        return true;
	       
	       
	}
	/**
	 * static method that upon being called sends a crash message to all clients and exits the application
	 */
	public static void stopServer() {
		if(server!=null)
			server.sendToAllClients(new ServerMessage(ServerMessageTypes.SERVER_ERROR,"Server crashed!\nSorry for the inconvenience\nClick 'OK' to exit..."));
		System.exit(0);
		
	}

 static void showError(String msg) {
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("");
				alert.setContentText(msg);
				alert.showAndWait();
			});
	}
}
