package gui;

import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Timer;

import java.io.File;
import java.io.FileOutputStream;

import entity.Exam;
import entity.ManualExamFile;
import entity.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessageTypes;

/**
 * Used to manage manual exam
 * 
 * @author Shalom and Omer
 * @extend StudentMainPageController
 *
 * 
 */
public class StudentManualExamController extends StudentMainPageController implements Initializable {
	/**
	 * Holds the exam details
	 */
	public Exam exam;
	/**
	 * Holds the timer
	 */
	public Timer timer;
	/**
	 * Holds minutes of exam
	 */
	private int minutes;
	/**
	 * Holds seconds of exam
	 */
	private int seconds;
	/**
	 * Holds the file needed to be uploaded
	 */
	private static File fileToUpload;
	/**
	 * Used to indicate whether file has been chosen
	 */
	boolean fileUploaded = false;

	/**
	 * Used to indicate whether time is over
	 */
	boolean timeIsOver = false;

	/**
	 * Used to hold extenions for document
	 */
	List<String> lstFile;

	/**
	 * Used to hold total time of exam
	 */
	@FXML
	private Text TimeOfExam2;

	/**
	 * Used to change time as the the time passes
	 */
	@FXML
	private Text TimeTick2;

	/**
	 * Shows time advancment
	 */
	@FXML
	private Text Timer2Text;

	/**
	 * Used as a button for downloading file
	 */
	@FXML
	private Button DownloadFileButton;

	/**
	 * Used as a button for uploading file
	 */
	@FXML
	private Button UploadFileButton;

	/**
	 * Used as a button for submission
	 */
	@FXML
	private Button SubmitButton2;

	/**
	 * Used to show name of exam
	 */
	@FXML
	private Text NameOfExam3;

	/**
	 * Used to show the path of the chosen file
	 */
	@FXML
	private Text FilePathText;
	
	boolean addition=false;

	/**
	 * Used as a method to download file when download button is pressed.
	 * 
	 * @param ActionEvent event
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
	void DownloadFileButtonPressed(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
		Object[] data = { exam.getEid(), exam };
		ClientMessage downloadMessage = new ClientMessage(ClientMessageType.DOWNLOAD_MANUAL_EXAM, data);
		guiControl.sendToServer(downloadMessage);
		if ((ManualExamFile) guiControl.getServerMsg().getMessage() == null) {
			guiControl.popUpMessage("System Message",
					"There was a problem with downloading the exam please contact your Teacher");

		} else {
			ManualExamFile manualExam = (ManualExamFile) guiControl.getServerMsg().getMessage();
			InputStream inputStream = new ByteArrayInputStream(manualExam.getExam());
			DirectoryChooser dc = new DirectoryChooser();
			File file = dc.showDialog(null);
			if (file != null) {
				String InitialPath;
				InitialPath = file.getAbsolutePath();
				try {
					Files.copy(inputStream, Paths.get(InitialPath + "/ExamFile.docx"));
				} catch (FileAlreadyExistsException e) {
					guiControl.popUpMessage("System Message", "File already exists in that directory!");
					inputStream.close();
					return;
				}
				inputStream.close();
				guiControl.popUpMessage("System Message", "Exam has been downloaded succesfully");
			} else {
				guiControl.popUpMessage("System Message", "Try downloading again and choose a directory!");

			}

		}

	}

	/**
	 * Used as a method to take care of submmision
	 * 
	 * @param ActionEvent event
	 * @throws IOException
	 */
	@FXML
	void SubmitButon2Pressed(ActionEvent event) throws IOException {
		GUIControl.popUpMessageYesNo("System Message", "Are you sure you want to submit your exam?");
		if (GUIControl.res == true) {

			if (fileUploaded == false) {
				guiControl.popUpMessage("System Message", "Please upload file before submission");
			}

			else {
				timer.stop();
				Object[] toSend = { exam.getEid(), fileToUpload, ((Student) guiControl.getUser()).getId(), exam };
				ClientMessage FileMessage = new ClientMessage(ClientMessageType.UPLOAD_MANUAL_EXAM, toSend);
				guiControl.sendToServer(FileMessage);
				if (guiControl.getServerMsg().getType() == ServerMessageTypes.EXAM_UPLOADED_SUCCECFULLY) {
					guiControl.popUpMessage("System Message", "Exam has been uploaded");
					guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);

				} else {

					guiControl.popUpMessage("System Message",
							"Exam has not been uploaded due to error\nPlease contact your Teacher");
				}

			}
		}

	}

	/**
	 * Used as a button to upload a file
	 * 
	 * @param ActionEvent event
	 * @throws FileNotFoundException
	 * 
	 */
	@FXML
	void UploadFileButtonPressed(ActionEvent event) throws FileNotFoundException {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Word Files", lstFile));
		fileToUpload = fc.showOpenDialog(null);
		if (fileToUpload != null) {
			fileUploaded = true;
			FilePathText.setText("Selected File::" + fileToUpload.getAbsolutePath());

		}

	}

	/**
	 * Sets the timer and file extensions
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		lstFile = new ArrayList<>();
		lstFile.add("*.doc");
		lstFile.add("*.docx");
		lstFile.add("*.DOC");
		lstFile.add("*.DOCX");

		String toPrint = null;
		String toInitilaize = null;
		exam = StudentStartExamController.exam;
		String Time = exam.getTotalTime();

		String[] TotalTimeString = Time.split(":");
		minutes = Integer.parseInt(TotalTimeString[0]);
		seconds = Integer.parseInt(TotalTimeString[1]);
		if (minutes >= 100) {
			toPrint = String.format("Total Time is:\n %03d:%02d%n", minutes, seconds);
			toInitilaize = String.format("%03d:%02d%n", 0, 0);
		} else {
			toPrint = String.format("Total Time is:\n %02d:%02d%n", minutes, seconds);
			toInitilaize = String.format("%02d:%02d%n", 0, 0);
		}
		TimeOfExam2.setText(toPrint);
		TimeTick2.setText(toInitilaize);
		NameOfExam3.setText(exam.getName());

		timer = new Timer(1000, new ActionListener() {
			int Minutes = 0;
			int Seconds = 0;
			int TotalMinutes = minutes;
			int TotalSeconds = seconds;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String toPrintEveryTime = null;
				if (minutes >= 100) {
					toPrintEveryTime = String.format("%03d:%02d%n", Minutes, Seconds);
				} else {
					toPrintEveryTime = String.format("%02d:%02d%n", Minutes, Seconds);
				}
				TimeTick2.setText(toPrintEveryTime);
				
				Seconds++;
				if (Seconds == 60) {
					Seconds = 0;
					Minutes++;
				}
				if (Minutes == TotalMinutes && Seconds == TotalSeconds) {
					timer.stop();
					guiControl.popUpMessage("System Message",
							"Time is up\nYou can't upload your Exam\nPlease contact your Teacher for help");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								timer.stop();
								stopExam();
								// guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

				}

			}
		});

		timer.start();

	}

	/**
	 * Stops the exam when a messages is recivied from teacher.
	 * 
	 * @throws IOException
	 */
	public void stopExam() throws IOException {
		timer.stop();
		Object[] data = { null, null, null, exam };
		ClientMessage FileMessage = new ClientMessage(ClientMessageType.UPLOAD_MANUAL_EXAM, data);
		guiControl.sendToServer(FileMessage);
		guiControl.popUpMessage("System Message", "Exam has been locked by Teacher");
		guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);

	}
	public void addition() {
		
	}

}
