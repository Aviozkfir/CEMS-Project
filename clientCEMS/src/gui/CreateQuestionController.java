package gui;

import entity.Question;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.control.ToggleGroup;

public class CreateQuestionController {

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
	    private TextArea textQuestion;

	    @FXML
	    private RadioButton rbA;

	    @FXML
	    private ToggleGroup rbGroup;

	    @FXML
	    private TextArea textA;

	    @FXML
	    private RadioButton rbB;

	    @FXML
	    private TextArea textB;

	    @FXML
	    private RadioButton rbC;

	    @FXML
	    private TextArea textC;

	    @FXML
	    private RadioButton rbD;

	    @FXML
	    private TextArea textD;

	    @FXML
	    private Button btnSave;

	    @FXML
	    private Button btnBack;
	
    @FXML
    void handleSaveButtonAction(ActionEvent event) {

    }

}
