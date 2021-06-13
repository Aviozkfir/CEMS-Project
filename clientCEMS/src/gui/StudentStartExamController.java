package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Exam;
import entity.ExamForStudent;
import entity.QuestionInExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * Used as a controller to enter Exam Code.
 * 
 * @author Shalom and Omer
 * @extend StudentMainPageController
 *
 */
public class StudentStartExamController extends StudentMainPageController {
	/**
	 * used to hold the exam details.
	 */
	public static Exam exam;

	/**
	 * Used to hold the written code.
	 */
	@FXML
	private TextField CodeTextField;

	/**
	 * Used as a button in order to start the exam
	 */
	@FXML
	private Button SubmitStartExam;

	/**
	 * Used as a method to start the exam after code is inserted and determine
	 * whether the exam is manual or computerized
	 * @param ActionEvent event
	 * @throws IOException
	 * 
	 */
	@FXML
	void SubmitStartExamPressed(ActionEvent event) throws IOException {
		if (CodeTextField.getText().isEmpty()) {
			guiControl.popUpMessage("System Message", "Code field is empty");
		} else if (CodeTextField.getText().length() != 4) {
			guiControl.popUpMessage("System Message", "Length of code must be 4 characters");
		} else if (CodeTextField.getText().matches("[a-zA-Z]+")) {
			guiControl.popUpMessage("Code must consist letters and digits");
		} else if (CodeTextField.getText().matches("[0-9]+")) {
			guiControl.popUpMessage("Code must consist letters and digits");
		} else {
			String examCode = CodeTextField.getText();
			ClientMessage msg = new ClientMessage(ClientMessageType.VALIDATE_EXAM_CODE, examCode);
			guiControl.sendToServer(msg);
			if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_CODE_NOT_FOUND) {
				guiControl.popUpError("Exam code not found");
			} else {
				ClientMessage msg2 = new ClientMessage(ClientMessageType.GET_EXAM_INFORMATION, examCode);
				guiControl.sendToServer(msg2);
				if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_INFORMATION_NOT_RECIVED) {
					guiControl.popUpError("Exam code not found");
				} else if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_NOT_STARTED_YET)
					guiControl.popUpError("Exam hasn't been started yet");
				else {
					exam = ((Exam) guiControl.getServerMsg().getMessage());
					if (exam.getMode().equals("Computerized")) {
						guiControl.loadStage(ClientsConstants.Screens.STUDENT_COMPUTERIZED_EXAM_EXECUTION.path);
					} else {
						guiControl.loadStage(ClientsConstants.Screens.STUDENT_MANUAL_EXAM_EXECUTION.path);
					}

				}

			}

		}

	}

}
