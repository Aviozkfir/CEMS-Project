package gui;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class TeacherExamBankSolvedByStudentExamsRowsController {

    @FXML
    private Button btnView;

    @FXML
    private Label examID;

    @FXML
    private Label examTitle;

    @FXML
    private Label date;

    @FXML
    private Text checkNum;

    @FXML
    private Text totalNum;

    @FXML
    private Button btnCheckExam;
    
    
    private  String chosenPath;

    private Object object;
   

	private BiConsumer<String,Object> consumer;

    @FXML
    void btnCheckPressed(ActionEvent event) {

    }

    @FXML
    void btnViewExamPressed(ActionEvent event) {

    }

}
