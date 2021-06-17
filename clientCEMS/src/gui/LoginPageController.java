package gui;

import java.io.IOException;
import java.util.ArrayList;

import application.UpdateThread;
import entity.Course;
import entity.CourseReport;
import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import entity.Subject;
import entity.Teacher;
import javafx.application.Platform;
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
import message.ServerMessage;
import message.ServerMessageTypes;

/**
 * Controller for log in
 * 
 * @author Shalom,Omer
 * 
 */
public class LoginPageController {
	GUIControl guiControl = GUIControl.getInstance();
	@FXML
	private PasswordField PasswordFieldText;

	@FXML
	private Button LoginButton;

	@FXML
	private TextField UserIDTextField;
	
	
	private IServerClientCommunication iServerClientCommunication = new LoginServerClientCommunication();
	
	public void setiServerClientCommunication(IServerClientCommunication iServerClientCommunication) {
		this.iServerClientCommunication = iServerClientCommunication;
	}
	
	private ILoginGetUserInput iLoginGetUserInput = new LoginILoginGetUserInput();

	public void setiLoginGetUserInput(ILoginGetUserInput iLoginGetUserInput) {
		this.iLoginGetUserInput = iLoginGetUserInput;
	}

	private class LoginServerClientCommunication implements IServerClientCommunication{

	@Override
	public void sendToServer(Object msg) {
		guiControl.sendToServer(msg);
		
	}

	@Override
	public ServerMessage getServerMsg() {
		
		return guiControl.getServerMsg();
	}

	@Override
	public void popUpError(String msg) {
		guiControl.popUpError(msg);
		
	}

	@Override
	public void popUpMessage(String msg) {
		guiControl.popUpMessage(msg);
		
	}

	@Override
	public Object getUser() {
		return guiControl.getUser();
	}
	
	@Override
	public void setUser(Object user) {
		guiControl.setUser(user);
	}
		
	}

	
	private class LoginILoginGetUserInput implements ILoginGetUserInput{

		@Override
		public String getUserID() {
			
			return UserIDTextField.getText();
		}

		@Override
		public String getUserPassword() {
			
			return PasswordFieldText.getText();
		}
		
	}
	/**
	 * This method gets the user to the right Homepage screen by its type
	 * :principal,teacher,student and get the data needed for each type.moreover,
	 * validates that the log in information is right.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void LoginButtonPressed(ActionEvent event) throws IOException {

		if (validateLogin()) {
			Stage primaryStage = guiControl.getStage();
			primaryStage.hide();
			PersonCEMS person = (PersonCEMS) guiControl.getUser();
			String role = person.getRole();
			String chosenPath = null;
			Object controller = null;
			UpdateThread requestThread = new UpdateThread();
			switch (role) {
			case "Teacher":
				chosenPath = ClientsConstants.Screens.TEACHER_WELCOME_PAGE.path;

				ClientMessage msg1 = new ClientMessage(ClientMessageType.TEACHER_SUBJECTS_INFORMATION,

						((Teacher) guiControl.getUser()).getId());

				guiControl.sendToServer(msg1);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_SUBJECTS_ADDED) {

					ArrayList<Subject> subjects = (ArrayList<Subject>) guiControl.getServerMsg().getMessage();

					((Teacher) person).setSubjectList(subjects);

				} else {

					GUIControl.popUpError("Error in loading courses list to Teacher");

				}

				ClientMessage msg2 = new ClientMessage(ClientMessageType.TEACHER_COURSES_INFORMATION,

						((Teacher) guiControl.getUser()).getId());

				guiControl.sendToServer(msg2);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_COURSES_ADDED) {

					ArrayList<Course> courses = (ArrayList<Course>) guiControl.getServerMsg().getMessage();

					((Teacher) person).setCourseList(courses);

				} else {

					GUIControl.popUpError("Error in loading courses list to Teacher");

				}

				break;
			case "Principal":
				chosenPath = ClientsConstants.Screens.PRINCIPAL_MAIN_PAGE.path;
				ClientMessage msg3 = new ClientMessage(ClientMessageType.PRINCIPAL_SUBJECTS_INFORMATION,
						((Principal) guiControl.getUser()).getId());
				guiControl.sendToServer(msg3);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_SUBJECTS_ADDED) {

					ArrayList<Subject> subjects = (ArrayList<Subject>) guiControl.getServerMsg().getMessage();

					((Principal) person).setSubjectList(subjects);

				} else {

					GUIControl.popUpError("Error in loading subject list to Principal");

				}

				ClientMessage msg4 = new ClientMessage(ClientMessageType.PRINCIPAL_COURSES_INFORMATION,

						((Principal) guiControl.getUser()).getId());

				guiControl.sendToServer(msg4);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.PRINCIPAL_COURSES_ADDED) {

					ArrayList<Course> courses = (ArrayList<Course>) guiControl.getServerMsg().getMessage();

					((Principal) person).setCourseList(courses);

				} else {

					GUIControl.popUpError("Error in loading courses list to Principal");

				}
				// guiControl.setRequestThread(requestThread);

				break;
			case "Student":
				ClientMessage studentMsgSubjects = new ClientMessage(ClientMessageType.STUDENT_SUBJECTS_INFORMATION,

						((Student) guiControl.getUser()).getId());

				guiControl.sendToServer(studentMsgSubjects);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.STUDENT_SUBJECTS_ADDED) {

					ArrayList<Subject> subjects = (ArrayList<Subject>) guiControl.getServerMsg().getMessage();

					((Student) person).setSubjectList(subjects);

				} else {

					GUIControl.popUpError("Error in loading subject list to Student");

				}

				ClientMessage studentMsgCourses = new ClientMessage(ClientMessageType.STUDENT_COURSES_INFORMATION,

						((Student) guiControl.getUser()).getId());

				guiControl.sendToServer(studentMsgCourses);

				if (guiControl.getServerMsg().getType() == ServerMessageTypes.STUDENT_COURSES_ADDED) {

					ArrayList<Course> courses = (ArrayList<Course>) guiControl.getServerMsg().getMessage();

					((Student) person).setCourseList(courses);

				} else {

					GUIControl.popUpError("Error in loading courses list to Student");

				}
				chosenPath = ClientsConstants.Screens.STUDENT_MAIN_PAGE.path;
				break;
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(chosenPath));
			AnchorPane root = fxmlLoader.load();

			controller = (MainPageController) fxmlLoader.getController();
			guiControl.setController(controller);
			((MainPageController) controller).setUser((PersonCEMS) guiControl.getUser());

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e -> {
				guiControl.disconnect();
			});
			primaryStage.show();
			guiControl.setUpdateThread(requestThread);
			requestThread.start();
			if (role.equals("Principal")) {
				PrincipalMainPageController con = (PrincipalMainPageController) guiControl.getController();
				guiControl.CountRequest();
				con.setRequestCounter();
			}
		}

	}

	/**
	 * Method that validates that the log in information is correct, valid and exist
	 * in db
	 *
	 */
	public boolean validateLogin() {
		ClientMessage msg = null;
		if (iLoginGetUserInput.getUserID().isEmpty()) {
			iServerClientCommunication.popUpError("No UserID was inserted\n Please enter UserID");
			return false;
		}
		if (iLoginGetUserInput.getUserPassword().isEmpty()) {
			iServerClientCommunication.popUpError("No Password was inserted\n Please enter Password");
			return false;
		}
		String[] IdAndPassword = { iLoginGetUserInput.getUserID(), iLoginGetUserInput.getUserPassword() };
		msg = new ClientMessage(ClientMessageType.LOGIN_PERSON, IdAndPassword);
		iServerClientCommunication.sendToServer(msg);

		if (iServerClientCommunication.getServerMsg().getMessage() == null) {
			iServerClientCommunication.popUpError("Login information doesn't exist\n Please try again");
			return false;
		} else if (iServerClientCommunication.getServerMsg().getMessage().equals("logged in")) {
			iServerClientCommunication.popUpError("This user is already logged in");
			return false;
		}
		iServerClientCommunication.setUser(iServerClientCommunication.getServerMsg().getMessage());
		return true;
	}

}
