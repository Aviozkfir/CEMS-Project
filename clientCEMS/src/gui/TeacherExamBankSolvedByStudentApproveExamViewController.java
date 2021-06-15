package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.SolvedExamType;
import entity.SolvedQuestionToView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

public class TeacherExamBankSolvedByStudentApproveExamViewController  extends TeacherMainPageController{

    @FXML
    private AnchorPane myRoot;

   

    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text examName;

    @FXML
    private Text studentID;

    @FXML
    private TextField newGrade;

    @FXML
    private TextArea changeReason;

    @FXML
    private Button btnChange;

    @FXML
    private VBox vTable;

    @FXML
    private Text finishStatus;

    @FXML
    private Text exeTime;

    @FXML
    private Text studentNotes;

    @FXML
    private Text teacherNotes;

    @FXML
    private Button btnBack;

    
    private String SEid;
    
    SolvedExamType exam;
    private String Cgrade;
    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {

    	TeacherExamBankSolvedByStudentApproveController a = ((TeacherExamBankSolvedByStudentApproveController) GUIControl.instance.loadStage("TeacherExamBankSolvedByStudentApprove.fxml"));
    	a.setExamType(exam);
    	
    }

    @FXML
    void btnChangePressed(ActionEvent event) throws IOException {
    	for(int i=0; i<list.size();i++) 
    		list.get(i).setNotesForStudent(listC.get(i).getComment());
    	
    	try {
    	if((Integer.parseInt(newGrade.getText())<0)||(Integer.parseInt(newGrade.getText())>100))
    	{
    		GUIControl.popUpMessage("Error", "Grade must be between 0 to 100.");
    		return;
    	}
    	}catch(NumberFormatException e) {
    		
    		GUIControl.popUpMessage("Error", "Must enter grade of exam (number between 0 to 100).");
    		return;
    	}
    	
    	String grade =newGrade.getText();
    	
    	if(changeReason.getText().trim().equals("")&&(!grade.equals(Cgrade))) {
    		GUIControl.popUpMessage("Error", "Must a reason for changing grade.");
    		return;
    	}
    	String comment = changeReason.getText();
    	
    	Object[] l = {grade,comment, SEid , list};
    	
    	guiControl.sendToServer(new ClientMessage( ClientMessageType.TEACHER_ADD_GRADE_WITH_COMMENTS ,l));
    	guiControl.popUpMessage("Grade was published successfully");
    	TeacherExamBankSolvedByStudentApproveController a = ((TeacherExamBankSolvedByStudentApproveController) GUIControl.instance.loadStage("TeacherExamBankSolvedByStudentApprove.fxml"));
    	a.setExamType(exam);
    }
    
    private ArrayList<SolvedQuestionToView> list;
    
    private ArrayList<TeacherExamBankSolvedByStudentApproveExamViewRowController> listC = new ArrayList<TeacherExamBankSolvedByStudentApproveExamViewRowController>();
    
    
    public void setExam(String[] a, SolvedExamType exam) throws IOException {
    	Cgrade=a[3];
    	SEid = a[0];
    	this.exam=exam;
    	subjectName.setText(exam.getCourse().getSubject().getName());
    	courseName.setText(exam.getCourse().getName());
    	examName.setText(exam.getName());
    	studentID.setText(a[1]);
    	finishStatus.setText(a[5]);
    	exeTime.setText(a[6]);
    	
    	guiControl.sendToServer(new ClientMessage( ClientMessageType.GET_QUESTIONS_FOR_SOLVED_EXAM, a[0]));
    	list = (ArrayList<SolvedQuestionToView>) guiControl.getServerMsg().getMessage();
    	
    	for(int i=0; i<list.size();i++) {
    		
    		
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSolvedByStudentApproveExamViewRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			listC.add((TeacherExamBankSolvedByStudentApproveExamViewRowController) fxmlLoader.getController());
			listC.get(i).setQuestion(list.get(i));
			
			vTable.getChildren().add(root);
    	}
    }

}
