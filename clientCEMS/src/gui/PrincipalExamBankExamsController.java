package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import entity.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class PrincipalExamBankExamsController extends PrincipalMainPageController {

	private ArrayList<Exam> allExams;
	ArrayList<PrincipalExamBankRowController> examControllerList = new ArrayList<PrincipalExamBankRowController> ();
	
	
    @FXML
    private AnchorPane myRoot;

    
    @FXML
    private Text subjectName;

    @FXML
    private Text courseName;

    @FXML
    private Text numberOfExams;

    @FXML
    private VBox vTable;

    @FXML
    private Button Back;
    
	@FXML
	private TextField searchBar;
	
	@FXML
	private Button btnSearch;
	
	@FXML
	private Button showAll;

    
    private Course course;

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
    	PrincipalExamBankCoursesController contr = (PrincipalExamBankCoursesController) GUIControl.instance
				.loadStage(gui.ClientsConstants.Screens.PRINCIPAl_EXAM_BANK_COURSES.path);
		contr.setPrincipalCourse(course.getSubject());
		contr.setRequestCounter();
		
    }
    
	@FXML
	void btnSearchPressed(ActionEvent event) throws IOException {
		String examID = searchBar.getText();
		if(CheckInput()) {
		vTable.getChildren().clear(); 
		for (Exam e : allExams) {
			if (e.getEid().equals(examID)) {
				AddTableRow(e);
				break;
			}
		}
		}
	}
	
	private boolean CheckInput() {
		String input = searchBar.getText();
		boolean exist = false;
		
		for (Exam q : allExams) {
			if (q.getEid().equals(input)) {
				exist = true;
			}
		}
		
		
		
		 if (input.isEmpty()) {
			GUIControl.popUpError("Error - Field is empty, please write correct exam number.");
			return false;
			}
		
		else if(!input.matches("[0-9]+")) {
			GUIControl.popUpError("Error - Please insert numbers only.");
			return false;
		}
		
		else if(input.length() != 6) {
			GUIControl.popUpError("Error - Exam number length should be 6, please insert again.");
			return false;
		}
		 
		else if(!exist) {
			GUIControl.popUpError("Error - There is no such a Exam in the list.");
			return false;
		}
		return true;
		}
	
	
	@FXML
	void showAllPressed(ActionEvent event) throws IOException {
		vTable.getChildren().clear(); 
		for (Exam e : allExams) {
				AddTableRow(e);
			}
		}

    
 public void setPrincipalCourse(Course course) throws IOException {
    	
    	this.course=course;
    	
    	courseName.setText(course.getName());
    	subjectName.setText(course.getSubject().getName());
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.GET_EXAM_BY_COURSE, course);
    	guiControl.sendToServer(m1);
    	if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_BY_COURSE_NOT_RECIVED) {
			GUIControl.popUpError("Error-getting Exam list for principal.");
		}
    	allExams = (ArrayList<Exam>) guiControl.getServerMsg().getMessage();
    	
    	numberOfExams.setText(""+allExams.size());
    	for(Exam e : allExams)
    	{
    		AddTableRow(e);
    	}
    }
 
	public void AddTableRow(Exam exam) throws IOException {
		PrincipalExamBankRowController controller;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gui.ClientsConstants.Screens.PRINCIPAL_EXAM_TABLE_ROW.path));
		AnchorPane root = fxmlLoader.load();
		controller = (PrincipalExamBankRowController) fxmlLoader.getController();
		controller.setExam(exam,course);
		vTable.getChildren().add(root);
		examControllerList.add(controller);
	}

}
