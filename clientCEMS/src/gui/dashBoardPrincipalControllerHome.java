package gui;

import java.io.IOException;

import entity.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashBoardPrincipalControllerHome {
	private Principal user;
	private GUIControl guiControl = GUIControl.getInstance();
    private dashBoardPrincipalControllerHome controller = this;

    @FXML
    private Button HomePageButton;

    @FXML
    private Button QuestionBankButton;

    @FXML
    private Button ExamBankButton;

    @FXML
    private Button GetReportButton;

    @FXML
    private Text Name;

    @FXML
    private Button LogOutButton;

    @FXML
    private Text HelloNameMessage;
    
    @FXML
    private Button RequestsButton;

    @FXML
    void ExamBankButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.EXAM_BANK_MAIN_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		//guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();


    }

    @FXML
    void GetReportButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.REPORT_MAIN_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();
    }

    @FXML
    void HomePageButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();
    }

    @FXML
    void LogOutButtonPressed(ActionEvent event) {
    	guiControl.logOut();
		guiControl.openLoginPage();

    }

    @FXML
    void QuestionBankButtonPressed(ActionEvent event) throws IOException {
    	
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.QUESTION_BANK_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();
    }
    
    @FXML
    void RequestsButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.MANAGER_REQUESTS_PAGE.path;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
		AnchorPane root = fxmlLoader.load();
		guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();

    }
    
	public void setUser(Object person) {
		this.user = (Principal)person;
		setNames();

	}
	public void setNames() {
		HelloNameMessage.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());
		Name.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());
	}

}
