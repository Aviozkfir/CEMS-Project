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
import message.ClientMessage;
import message.ClientMessageType;

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
    	TeacherExamBankSolvedByStudentExamsController a = ((TeacherExamBankSolvedByStudentExamsController) guiControl.loadStage("/gui/TeacherExamBankSolvedByStudentExams.fxml"));
    	a.setTeacherCourse(exam.getCourse());
    	
    }

    
   
    public void setExamType(SolvedExamType exam) throws IOException {
    	this.exam=exam;	
    	this.numOfExams.setText(exam.getTotal()+"");
    	this.numOfGraded.setText(exam.getChecked()+"");
    	this.numOfNotGraded.setText((exam.getTotal()-exam.getChecked())+"");
    	this.subjectName.setText(exam.getCourse().getSubject().getName());
    	this.courseName.setText(exam.getCourse().getName());
    	this.examName.setText(exam.getName());
    	
    	String[] a = {exam.getEid(),exam.getDate()};
    	guiControl.sendToServer(new ClientMessage(ClientMessageType.TEACHER_GET_EXAMS,a));
    	ArrayList<String[]> list = (ArrayList<String[]>) guiControl.getServerMsg().getMessage();
    	
    	for(String[] e : list) {
    		TeacherExamBankSolvedByStudentApproveRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSolvedByStudentApproveRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherExamBankSolvedByStudentApproveRowController) fxmlLoader.getController();
			controller.setRow(e, exam);
			controller.setSetAmounts(()->{
				
				
				exam.setChecked(exam.getChecked()+1);
				this.numOfGraded.setText(exam.getChecked()+"");
		    	this.numOfNotGraded.setText((exam.getTotal()-exam.getChecked())+"");
		    	return 1;
			});
			{
				
				
				exam.setChecked(exam.getChecked()+1);
				this.numOfGraded.setText(exam.getChecked()+"");
		    	this.numOfNotGraded.setText((exam.getTotal()-exam.getChecked())+"");
			}
			vTable.getChildren().add(root);
    	}
    }

}
