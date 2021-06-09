package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import entity.QuestionInExam;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class TeacherCreateExamPage3Controller {

	private Course course;
    
    private VBox myVbox;
	@FXML
    private VBox vTable;

    @FXML
    private Text examTitle;

    @FXML
    private Text time;

    @FXML
    private Text qNum;

    @FXML
    private Text examCode;

    @FXML
    private Text studentNotes;

    @FXML
    private Text teacherNotes;

    @FXML
    private Button btnPublish;

    
    private AnchorPane page2;
    private AnchorPane page4;
    private TeacherCreateExamPage4Controller con4;
    
    private ArrayList<Question> myQuestions;
    
    private ArrayList<TeacherExamBankSelectQuestionPointsRowController> controllers = new ArrayList<TeacherExamBankSelectQuestionPointsRowController>();
    
    
    public void setMyQuestions(ArrayList<Question> myQuestions) {
		this.myQuestions = myQuestions;
	}
    
    
    private Exam exam;
    
    public void setExam(Exam exam) {
		this.exam = exam;
	}
    
    public void setPage2(AnchorPane page2) {
		this.page2 = page2;
	}
    
    public void setPage4(AnchorPane page4) {
  		this.page4 = page4;
  	}
    
    public void setMyVbox(VBox myVbox) {
		this.myVbox = myVbox;
	}
    
    public void setCourse(Course course) {
		this.course = course;
		
	}

    public void setDetailsReview() {
    	examTitle.setText(exam.getName());
    	time.setText(exam.getTotalTime()+":00");
    	studentNotes.setText(exam.getSdescription());
    	teacherNotes.setText(exam.getTdescription());
    	examCode.setText(exam.getCode());
    	qNum.setText(""+myQuestions.size());
    }
    
    @FXML
    void btnBackPressed(ActionEvent event) {
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page2);
    }

    @FXML
    void btnPublishPressed(ActionEvent event) {
    	
    	ArrayList<QuestionInExam> qeList = new ArrayList<QuestionInExam>();
    	
    	int sum=0;
    	for(int i=0; i<controllers.size();i++) {
    		
    		try {
    		if((Integer.parseInt(controllers.get(i).getPoints())<=0))
        	{
        		GUIControl.popUpMessage("Error", "Must enter a positive number of points in question num#: "+(i+1)+".");
        		return;
        	}
        	}catch(NumberFormatException e) {
        		
        		GUIControl.popUpMessage("Error", "Must enter a number in question num#: "+(i+1)+".");
        		return;
        	}
    		sum+=Integer.parseInt(controllers.get(i).getPoints());
    		
    	}
    	
    	if(sum!=100) {
    		GUIControl.popUpMessage("Error", "Sum of points is: "+sum+",\n"+"but sum of points in exam must be 100.");
    		return;
		}
    	
    	for(int i=0; i<myQuestions.size();i++) {
    		QuestionInExam qe= new QuestionInExam();
    		qe.setPointsQuestion(Integer.parseInt(controllers.get(i).getPoints()));
    		qe.setId(myQuestions.get(i).getId());
    		qe.setNumOfQuestion(i+1);
    		qeList.add(qe);
    	}
    	
    	
    	Object[] arr = new Object[2];
    	arr[0]=exam;
    	arr[1]=qeList;
    	exam.setDate();
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_ADD_EXAM, arr);
    	GUIControl.getInstance().sendToServer(m1);
    	if(GUIControl.getInstance().getServerMsg().getType()==ServerMessageTypes.EXAM_NOT_ADDED) {
    		GUIControl.popUpMessage("Error", "Can't add exam to bank.");
    		return; 
    	}
    	
    	
    	con4.setexamID((String)GUIControl.getInstance().getServerMsg().getMessage());
    	myVbox.getChildren().remove(2);
    	myVbox.getChildren().add(page4);
    }

    
    public void setTeacherCreateExamPage4Controller(TeacherCreateExamPage4Controller con4) {
    	this.con4=con4;
    }
    public void PrintSelectedQuestions() throws IOException {
    	for(Question q : myQuestions)
    	{
    		TeacherExamBankSelectQuestionPointsRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherExamBankSelectQuestionPointsRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherExamBankSelectQuestionPointsRowController) fxmlLoader.getController();
			controllers.add(controller);
			controller.setQuestion(q);
			vTable.getChildren().add(root);
    	}
    }
}
