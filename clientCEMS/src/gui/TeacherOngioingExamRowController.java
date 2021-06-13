package gui;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientCEMS;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class TeacherOngioingExamRowController {
	@FXML
	private Label num;

	@FXML
	private Label title;

	@FXML
	private Label currentDuration;

	@FXML
	private TextField newDurationText;

	@FXML
	private TextArea reasonText;

	@FXML
	private Label examStatus;

	@FXML
	private Button btnChangeTime;

	@FXML
	private Button btnLockExam;

	private Exam exam;

	public void setExam(Exam exam) {
		this.exam = exam;
		title.setText(exam.getName());
		currentDuration.setText(exam.getTotalTime());
		examStatus.setText("Not sent yet");
	}

	@FXML
	void btnChangeTimePressed(ActionEvent event) {
		ArrayList<String> newRequest = new ArrayList<String>();
		if (CheckInput()) {
			newRequest.add(exam.getEid());
			newRequest.add(exam.getName());
			newRequest.add(exam.getTotalTime());
			newRequest.add(exam.getID());
			newRequest.add(newDurationText.getText());

			GUIControl guiControl = GUIControl.getInstance();
			ClientMessage msg = new ClientMessage(ClientMessageType.TEACHER_SEND_REQUEST, newRequest);
			guiControl.sendToServer(msg);
			if (guiControl.getServerMsg().getType() == ServerMessageTypes.TEACHER_REQUEST_NOT_RECIVED) {
				GUIControl.popUpError("Error-sending new request.");
			}
		}
	}

	@FXML
	void btnLockExamPressed(ActionEvent event) throws IOException {

		ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_LOCK_EXAM, exam);
		GUIControl.getInstance().sendToServer(m1);

	}

	private boolean CheckInput() {
		String input = newDurationText.getText();

		if (input.isEmpty()) {
			GUIControl.popUpError("Error - Field is empty, please insert a number.");
			return false;
		}

		else if (!input.matches("[0-9]+")) {
			GUIControl.popUpError("Error - Please insert numbers only.");
			return false;
		}
		return true;
	}

}
