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

public class StudentComputerizedExamController extends StudentMainPageController implements Initializable {
	public static Exam exam;
	public static ExamForStudent currentExam;
	public Student student;

	@FXML
	private Text ExamDuration;

	@FXML
	private Text NameOfExam;

	@FXML
	private TextField IdTextField;

	@FXML
	private Button ForwardButton;

	@FXML
	private Button BackButton;

	@FXML
	void BackButtonPressed(ActionEvent event) throws IOException {
		guiControl.loadStage(ClientsConstants.Screens.STUDENT_START_EXAM_PAGE.path);
	}

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