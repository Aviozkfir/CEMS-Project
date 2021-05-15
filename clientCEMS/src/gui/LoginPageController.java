package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import message.ClientMessage;
import message.ClientMessageType;

public class LoginPageController {
	GUIControl guiControl = GUIControl.getInstance();
	

	@FXML
	private PasswordField PasswordFieldText;

	@FXML
	private Button LoginButton;

	@FXML
	private TextField UserIDTextField;

	@FXML
	void LoginButtonPressed(ActionEvent event) {
    	if(validateLogin()) {
    		Stage primaryStage=guiControl.getStage();
    		primaryStage.hide();
    		
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/CEMSlogin.fxml"));
    		AnchorPane root = fxmlLoader.load();
    		ClientMainPageController cmpc = (ClientMainPageController)fxmlLoader.getController();
    		guiControl.setClientMainPageController(cmpc);
    		cmpc.setUser(guiControl.getUser());
			Scene scene = new Scene (root);
			scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e->{
				guiControl.disconnect();
			});
			primaryStage.show();
    	}  

	}

	private boolean validateLogin() {
		ClientMessage msg = null;
		if (UserIDTextField.getText().isEmpty()) {
			GUIControl.popUpError("No UserID was inserted\n Please enter UserID");
			return false;
		}
		if (PasswordFieldText.getText().isEmpty()) {
			GUIControl.popUpError("No Password was inserted\n Please enter Password");
			return false;
		}
		String[] IdAndPassword = { UserIDTextField.getText(), PasswordFieldText.getText() };
		msg = new ClientMessage(ClientMessageType.LOGIN_PERSON, IdAndPassword);
		guiControl.sendToServer(msg);
		if (guiControl.getServerMsg().getMessage() == null) {
			GUIControl.popUpError("Login information doesn't exist\n Please try again");
			return false;
		} else if (guiControl.getServerMsg().getMessage().equals("logged in")) {
			GUIControl.popUpError("This user is already logged in");
			return false;
		}
		guiControl.setUser(guiControl.getServerMsg().getMessage());
		return true;
	}

}
