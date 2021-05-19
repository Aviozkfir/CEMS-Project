package gui;

import java.io.IOException;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {
	private PersonCEMS user;
	private GUIControl guiControl = GUIControl.getInstance();
    

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
    private Button MyExamsButton;

    @FXML
    private Button StartExamButton;

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
		//guiControl.setController(controller);
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
		//guiControl.setController(controller);
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
		//guiControl.setController(controller);
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
		//guiControl.setController(controller);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			guiControl.disconnect();
		});
		primaryStage.show();
    }
    
    @FXML
    void MyExamsButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.STUDENT_MY_EXAMS_PAGE.path;
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
    void StartExamButtonPressed(ActionEvent event) throws IOException {
		String chosenPath;
		Stage primaryStage = guiControl.getStage();
		//primaryStage.hide();
		chosenPath=ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path;
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
    
	public void setUser(Object person) {
		if (user instanceof Principal) {
			this.user = (Principal) person;
		} else if (user instanceof Teacher) {
			this.user = (Teacher) person;
		} else if (user instanceof Student) {
			this.user = (Student) person;
		}
		setNames();
	}
	public void setNames() {
		if (user instanceof Principal) {
		HelloNameMessage.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());
		Name.setText(((Principal) user).getFirstName() + " " + ((Principal) user).getLastName());}
		
		else if (user instanceof Teacher) {
			HelloNameMessage.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());
			Name.setText(((Teacher) user).getFirstName() + " " + ((Teacher) user).getLastName());}
		
		else if (user instanceof Student) {
		HelloNameMessage.setText(((Student) user).getFirstName() + " " + ((Student) user).getLastName());
		Name.setText(((Student) user).getFirstName() + " " + ((Student) user).getLastName());}
	}
	
	}

