package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class StudentExamChoiceController extends StudentMainPageController implements Initializable {

    @FXML
    private Text ExamName;

    @FXML
    private Button StartComputerizedExamButton;

    @FXML
    private Button StartManualExamButton;

    @FXML
    private Button BackButton;

    @FXML
    void BackButtonPressed(ActionEvent event) {

    }

    @FXML
    void StartComputerizedExamButtonPressed(ActionEvent event) {

    }


    @FXML
    void StartManualExamButtonPressed(ActionEvent event) {

    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	ExamName.setText(((Exam)guiControl.getServerMsg().getMessage()).getName());
		
	}

}
