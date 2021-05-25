package gui;

import entity.Course;
import entity.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeacherQuestionBankQuestionsController extends TeacherMainPageController {

    

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private TextField searchBar;

    @FXML
    private Button btnSearch;

    @FXML
    private Text numberOfQuestions;

    @FXML
    private Button btnCreateQuestion;

    @FXML
    private VBox vTable;

    @FXML
    private Button btnBack;

   

    @FXML
    void btnBackPressed(ActionEvent event) {

    }

    @FXML
    void btnCreateQuestionPressed(ActionEvent event) {

    }

    @FXML
    void btnSearchPressed(ActionEvent event) {

    }
    
    public void setTeacherCourse(Course sub) {
    	
    	
    }

}
