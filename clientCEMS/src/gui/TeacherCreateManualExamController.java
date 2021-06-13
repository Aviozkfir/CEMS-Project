package gui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import entity.Course;
import entity.Exam;
import entity.Student;
import entity.Teacher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

public class TeacherCreateManualExamController extends TeacherMainPageController implements Initializable{

	
	private static File fileToUpload;
	boolean fileUploaded = false;
    @FXML
    private VBox myVbox;

    @FXML
    private TextArea studentNotesText;

    @FXML
    private TextArea teacherNotesText;

 //   @FXML
 //   private Text subjectName;

  //  @FXML
  //  private Text courseName;
    private Exam exam = new Exam();

    @FXML
    private TextField examTitle;

    @FXML
    private TextField examDuration;

    @FXML
    private TextField examCode;

    @FXML
    private Button btnSelectExam;

    @FXML
    private Text  filePath;

    @FXML
    private Button publish;

    @FXML
    private Button btnBack;

    List<String> lstFile;
    
	private Course course;

	private byte[] f;
   

    @FXML
    void btnBackPressed(ActionEvent event) throws IOException {
    	((TeacherExamBankExamsController)GUIControl.instance.loadStage("/gui/TeacherExamBankExams.fxml")).setTeacherCourse(course);
    }

    @FXML
    void btnPublishPressed(ActionEvent event) throws IOException {
    	if(examTitle.getText().trim().equals("")) {
    		GUIControl.popUpMessage("Error", "Must enter exam title.");
    		return;
    	}
    	exam.setName(examTitle.getText());
    	
    	
    	try {
    	if((Integer.parseInt(examDuration.getText())<=0))
    	{
    		GUIControl.popUpMessage("Error", "Must enter a positive number of minutes.");
    		return;
    	}
    	}catch(NumberFormatException e) {
    		
    		GUIControl.popUpMessage("Error", "Invalid exam duartion input.");
    		return;
    		
    	}
    	if((Integer.parseInt(examDuration.getText())>3599)){
    		GUIControl.popUpMessage("Error", "Exam is too long");
    		return;
    	}
    	String h = (Integer.parseInt(examDuration.getText())/60)+"";
    	String min = (Integer.parseInt(examDuration.getText())%60)+"";
    	if(h.length()==1)
    		h="0"+h;
    	if(min.length()==1)
    		min="0"+min;
    	exam.setTotalTime(h+":"+min);
    	
    	
    	
    	if(!Pattern.compile("\\w{4}").matcher(examCode.getText()).matches()) {
    		GUIControl.popUpMessage("Error", "Must enter a code with 4 digits and letters.");
    		return;
    	}
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_CHECK_VALID_CODE, examCode.getText());
    	GUIControl.instance.sendToServer(m1);
    	
    	if(GUIControl.instance.getServerMsg().getType()==ServerMessageTypes.TEACHER_INVALID_CODE){
    		GUIControl.popUpMessage("Error", "This code is already being used in another exam");
    		return;
    	}
    	exam.setCode(examCode.getText());
    	
    	
    	if(!teacherNotesText.getText().trim().equals("")) 
    		exam.setTdescription(teacherNotesText.getText());
    	else 
    		exam.setTdescription("");
    	
    	if(!studentNotesText.getText().trim().equals(""))
    		exam.setSdescription(studentNotesText.getText());
    	else
    		exam.setSdescription("");
    	
    	exam.setDate();
    	if(fileUploaded==false)
    	{
    		GUIControl.popUpMessage("Error", "Must select a file.");
    		return;
    	}
    	Object[] toSend = { exam, f};
		ClientMessage FileMessage = new ClientMessage(ClientMessageType.UPLOAD_TEACHER_MANUAL_EXAM, toSend);
		guiControl.sendToServer(FileMessage);
		
		if (guiControl.getServerMsg().getType()!= ServerMessageTypes.TEACHER_EXAM_UPLOADED_SUCCECFULLY) {
			guiControl.popUpMessage("Error", "Failed to upload manual exam.");
			return;
		}
		((TeacherExamBankExamsController)GUIControl.instance.loadStage("/gui/TeacherExamBankExams.fxml")).setTeacherCourse(course);
    }

    @FXML
    void btnSelectPressed(ActionEvent event) {
    	FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Word Files", lstFile));
		fileToUpload = fc.showOpenDialog(null);
		if (fileToUpload != null) {
			fileUploaded = true;
			filePath.setText("Selected File::" + fileToUpload.getAbsolutePath());
			f= readFileToByteArray(fileToUpload);
		}
    }

	public void setTeacherCreateExam(Course course) {
		this.course=course;
    	exam.setCid(course.getId());
    	exam.setSid(course.getSubject().getId());
    	exam.setID(((Teacher)guiControl.getUser()).getId());
    	exam.setMode("Manual");
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		lstFile = new ArrayList<>();
		lstFile.add("*.doc");
		lstFile.add("*.docx");
		lstFile.add("*.DOC");
		lstFile.add("*.DOCX");
	}
	
	
	 private static byte[] readFileToByteArray(File file){
		    FileInputStream fis = null;
		    // Creating a byte array using the length of the file
		    // file.length returns long which is cast to int
		    byte[] bArray = new byte[(int) file.length()];
		    try{
		      fis = new FileInputStream(file);
		      fis.read(bArray);
		      fis.close();                   
		    }catch(IOException ioExp){
		      ioExp.printStackTrace();
		    }
		    return bArray;
		  }

}
