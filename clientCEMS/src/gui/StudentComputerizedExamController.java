package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import entity.Exam;
import entity.ExamForStudent;
import entity.QuestionInExam;
import entity.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

/**
 * @author Shalom and Omer
 * @extend StudentMainPageController
 * Controller for entering ID before starting a computerized exam.
 *
 */
public class StudentComputerizedExamController extends StudentMainPageController implements Initializable {
	/**
	 * holds current exam
	 */
	public static Exam exam;
	/**
	 * holds current exam for student which has relevant parameters
	 */
	public static ExamForStudent currentExam;
	/**
	 * holds the current student
	 */
	public Student student;

	/**
	 * holds the duration of the exam
	 */
	@FXML
	private Text ExamDuration;

	/**
	 * holds the name of the exam
	 */
	@FXML
	private Text NameOfExam;

	/**
	 * Used for entering ID
	 */
	@FXML
	private TextField IdTextField;

	/**
	 * Used to go to next page.
	 */
	@FXML
	private Button ForwardButton;

	/**
	 * Used to go to back page
	 */
	@FXML
	private Button BackButton;

	/**
	 * Used to go to the previous controller page where exam code is being inserted.
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void BackButtonPressed(ActionEvent event) throws IOException {
		guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	}

	/**
	 * Used to check whther the ID is true or false, if true goes to computerized exam page execution.
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void ForwardButtonPressed(ActionEvent event) throws IOException {
		if (IdTextField.getText().isEmpty()) {
			guiControl.popUpMessage("System Message", "ID field is empty");
		} else if (IdTextField.getText().length() != 9) {
			guiControl.popUpMessage("System Message", "Length of ID must be 9 digits");
		} else if (IdTextField.getText().matches("[a-zA-Z]+")) {
			guiControl.popUpMessage("System Message", "ID must consist only digits");
		} else if (!IdTextField.getText().equals(student.getId())) {
			guiControl.popUpMessage("System Message", "ID is not yours\nPlease Enter your unique ID");
		} else {
			ClientMessage msg1 = new ClientMessage(ClientMessageType.VALIDATE_STUDENT_ID, IdTextField.getText());
			guiControl.sendToServer(msg1);
			if ((boolean) guiControl.getServerMsg().getMessage() == true) {
				ArrayList<QuestionInExam> questions = null;
				ClientMessage questionMessage = new ClientMessage(ClientMessageType.GET_EXAM_QUESTIONS, exam.getEid());
				guiControl.sendToServer(questionMessage);
				questions = (ArrayList<QuestionInExam>) guiControl.getServerMsg().getMessage();
				if (questions == null) {
					guiControl.popUpMessage("System Message", "Questions import failed");

				} else {

					currentExam = new ExamForStudent(exam.getEid(), exam.getSid(), exam.getCid(), exam.getName(),
							exam.getDate(), exam.getTdescription(), exam.getSdescription(), exam.getID(),
							exam.getTotalTime(), exam.getCode(), exam.getMode(), questions);
					guiControl.loadStage(ClientsConstants.Screens.STUDENT_EXAM_COMPUTERIZED_START.path);

				}

			} else {
				guiControl.popUpMessage("System Message", "Student ID not found!!");
			}

		}

	}

	/**
	 *Sets text of duration of exam and name of exam.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exam = StudentStartExamController.exam;
		student = (Student) guiControl.getUser();
		String toPrint = null;
		String Time = exam.getTotalTime();
		String[] TotalTimeString = Time.split(":");
		int minutes = Integer.parseInt(TotalTimeString[0]);
		int seconds = Integer.parseInt(TotalTimeString[1]);
		if (minutes >= 100) {
			toPrint = String.format("%03d:%02d%n", minutes, seconds);
		} else {
			toPrint = String.format("%02d:%02d%n", minutes, seconds);
		}
		ExamDuration.setText(toPrint);
		NameOfExam.setText(((Exam) guiControl.getServerMsg().getMessage()).getName());
	}

}