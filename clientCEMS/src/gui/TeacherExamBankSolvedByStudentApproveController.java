package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.SolvedExamType;
import entity.Subject;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherExamBankSolvedByStudentApproveController extends TeacherMainPageController {

    @FXML
    private AnchorPane myRoot;

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text examName;

    @FXML
    private Text numOfExams;

    @FXML
    private Text numOfGraded;

    @FXML
    private Text numOfNotGraded;

    @FXML
    private Button btnPublishAll;

    @FXML
    private VBox vTable;

    @FXML
    private Button btnBack;

    
    private SolvedExamType exam;
    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	TeacherExamBankSolvedByStudentExamsController a = ((TeacherExamBankSolvedByStudentExamsController) guiControl.loadStage("TeacherExamBankSolvedByStudentExams.fxml"));
    	a.setTeacherCourse(exam.getCourse());
    }

    @FXML
    void btnPublishAllPressed(ActionEvent event) {

    }
   
    public void setExamType(SolvedExamType exam) {
    	
    }

}
