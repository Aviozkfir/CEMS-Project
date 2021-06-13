package gui;

import java.io.IOException;
import java.util.ArrayList;

import entity.Course;
import entity.Exam;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

public class TeacherOngoingExamsController extends TeacherMainPageController{


	

    @FXML
    private VBox vTable;
    
    private ArrayList<Exam> allExams;

    public void setOngoingExams() throws IOException {
    	
    	ClientMessage m1 = new ClientMessage(ClientMessageType.TEACHER_GET_CURRENT_EXAM,null);
    	guiControl.sendToServer(m1);
    	
    	allExams = (ArrayList<Exam>) guiControl.getServerMsg().getMessage();
    	
    	for(Exam e : allExams)
    	{
    		TeacherOngioingExamRowController controller;
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/TeacherOngioingExamRow.fxml"));
			AnchorPane root = fxmlLoader.load();
			controller = (TeacherOngioingExamRowController) fxmlLoader.getController();
			
			controller.setExam(e);
			vTable.getChildren().add(root);
			

    	}
    	
    	
    	
    }

}
