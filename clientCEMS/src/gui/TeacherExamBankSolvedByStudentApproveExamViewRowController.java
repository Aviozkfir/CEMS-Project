package gui;

import entity.SolvedQuestionToView;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class TeacherExamBankSolvedByStudentApproveExamViewRowController {

    @FXML
    private Text qNum;

    @FXML
    private Text textQuestion;

    @FXML
    private Text textQuestion1;

    @FXML
    private Text correctAns;

    @FXML
    private RadioButton rbA;

    @FXML
    private ToggleGroup rbGroup;

    @FXML
    private Text ans1;

    @FXML
    private RadioButton rbB;

    @FXML
    private Text ans2;

    @FXML
    private RadioButton rbC;

    @FXML
    private Text ans3;

    @FXML
    private RadioButton rbD;

    @FXML
    private Text ans4;

    @FXML
    private TextArea studentNotesText;
    
    public void setQuestion(SolvedQuestionToView q) {
    	qNum.setText(q.getQuestionNum()+"");
    	textQuestion.setText(q.getQuestionText());
    	correctAns.setText(q.getCorrectAns()+"");
    	switch (q.getChosenAnswer()) {

		case 1:
			rbA.setSelected(true);
			break;

		case 2:
			rbB.setSelected(true);
			break;

		case 3:
			rbC.setSelected(true);
			break;

		case 4:
			rbD.setSelected(true);
			break;
    	}
    	ans1.setText(q.getAnsA());
    	ans2.setText(q.getAnsB());
    	ans3.setText(q.getAnsC());
    	ans4.setText(q.getAnsD());
    	
    }
    
    public String getComment() {
    	return studentNotesText.getText();
    }

}
