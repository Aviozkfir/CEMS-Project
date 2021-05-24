package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class StudentStartExamController extends StudentMainPageController {

    @FXML
    private TextField CodeTextField;

    @FXML
    private Button SubmitStartExam;

    @FXML
    void SubmitStartExamPressed(ActionEvent event) throws IOException {
		if (CodeTextField.getText().isEmpty()) {
			guiControl.popUpError("Code field is empty");
		} else if (CodeTextField.getText().length() != 4) {
			guiControl.popUpError("Length of code must be 4 characters");
		} else if (CodeTextField.getText().matches("[a-zA-Z]+")) {
			guiControl.popUpError("Code must consist letters and digits");
		} else if (CodeTextField.getText().matches("[0-9]+")) {
			guiControl.popUpError("Code must consist letters and digits");
		}
		else {
			String examCode=CodeTextField.getText();
			ClientMessage msg=new ClientMessage(ClientMessageType.VALIDATE_EXAM_CODE,examCode);
			guiControl.sendToServer(msg);
			if(guiControl.getServerMsg().getType()==ServerMessageTypes.EXAM_CODE_NOT_FOUND) {
				guiControl.popUpError("Exam code not found");
			}
			else {
				ClientMessage msg2=new ClientMessage(ClientMessageType.GET_EXAM_INFORMATION,examCode);
				guiControl.sendToServer(msg2);
				if(guiControl.getServerMsg().getType()==ServerMessageTypes.EXAM_INFORMATION_NOT_RECIVED) {
					guiControl.popUpError("Exam code not found");
				}
				else {
				StudentExamChoiceController a = (StudentExamChoiceController) guiControl
			      .loadStage(ClientsConstants.Screens.STUDENT_CHOICE_EXAM_PAGE.path);
				}
					
			}
		
		}

    }
    
	

}

	
	
	
