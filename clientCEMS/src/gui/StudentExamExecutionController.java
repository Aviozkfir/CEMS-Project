package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import message.ClientMessage;
import message.ClientMessageType;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;
import javax.swing.Timer;
import entity.ExamForStudent;
import entity.QuestionInExam;
import entity.SolvedExam;
import entity.Student;
import javafx.fxml.Initializable;

/**
 *  A class Used to manage a computerized exam.
 * 
 * @author Shalom and Omer
 * @extend StudentComputerizedExamController
 *
 */
public class StudentExamExecutionController extends StudentComputerizedExamController implements Initializable {

	/**
	 * Holds seconds of exam.
	 */
	private int seconds;
	/**
	 * holds minutes of exam
	 */
	private int minutes;
	/**
	 * Hold index of current quesion in question ArrayList
	 */
	private int currentQuestion = 0;
	/**
	 * Holds all the questions of current Exam.
	 */
	private ArrayList<QuestionInExam> questions;
	/**
	 * Holds the final grade of student at the end of exam.
	 */
	private int TotalGrade = 0;
	/**
	 * Used to change the current time as the seconds pass.
	 */
	private int tickSeconds = 0;
	/**
	 * Used to change the current time as the minutes pass.
	 */
	private int tickMinutes = 0;

	/**
	 * Used to combine all the answers options into a toggleGroup
	 */
	@FXML
	private ToggleGroup answersGroup;

	/**
	 * Used to hold the number of question
	 */
	@FXML
	private Text NumOfQuestionsText;

	/**
	 * Used to show the time passed since the beggining of exam
	 */
	@FXML
	private Text TimerText;

	/**
	 * Used to change every time according to time.
	 */
	private Timer timer;

	/**
	 * Used to hold all information of current exam
	 */
	private static ExamForStudent exam;

	/**
	 * Used to hold total time of exam.
	 */
	@FXML
	private Text TimeOfExam;

	/**
	 * Used to change every time.
	 */
	@FXML
	private Text TimeTick;

	/**
	 * Used to hold name of exam.
	 */
	@FXML
	private Text NameOfExam2;

	/**
	 * Used to hold notes for student.
	 */
	@FXML
	private Text NotesToStudent;

	/**
	 * Used to show number of question.
	 */
	@FXML
	private Text QuestionNumberTitle;

	/**
	 * Used to show the question.
	 */
	@FXML
	private Text ContentOfQuestion;

	/**
	 * A choice buttion for option 1
	 */
	@FXML
	private RadioButton OptionA;

	/**
	 * Used to hold text for option 1
	 */
	@FXML
	private Text OptionAText;

	/**
	 * A choice buttion for option 2
	 */
	@FXML
	private RadioButton OptionB;

	/**
	 * Used to hold text for option 2
	 */
	@FXML
	private Text OptionBText;

	/**
	 * A choice buttion for option 3
	 */
	@FXML
	private RadioButton OptionC;

	/**
	 * Used to hold text for option 3
	 */
	@FXML
	private Text OptionCText;

	/**
	 * A choice buttion for option 4
	 */
	@FXML
	private RadioButton OptionD;

	/**
	 * Used to hold text for option 4
	 */
	@FXML
	private Text OptionDText;

	/**
	 * Used to go back to previous question
	 */
	@FXML
	private Button PreviousQuestionButton;

	/**
	 * Used to go to next question
	 */
	@FXML
	private Button NextQuestionButton;

	/**
	 * Used to submit the exam
	 */
	@FXML
	private Button SubmitButton;
	
	int addition=0;

	/**
	 * Used to go to the next question and sets all the next question details.
	 * 
	 * @param ActionEvent event
	 */
	@FXML
	void NextQuestionButtonPressed(ActionEvent event) {

		try {
			if (currentQuestion < questions.size() - 1) {
				currentQuestion++;
				setQuestion(currentQuestion);
				if (questions.get(currentQuestion).getChosenAnswer() != -1) {
					int chosenPrevious = questions.get(currentQuestion).getChosenAnswer();
					if (chosenPrevious == 1) {
						OptionA.setSelected(true);
					} else if (chosenPrevious == 2) {
						OptionB.setSelected(true);
					} else if (chosenPrevious == 3) {
						OptionC.setSelected(true);
					} else if (chosenPrevious == 4) {
						OptionD.setSelected(true);
					}

				} else {
					OptionA.setSelected(false);
					OptionB.setSelected(false);
					OptionC.setSelected(false);
					OptionD.setSelected(false);
				}
			}

			else {
				currentQuestion = questions.size() - 1;
				throw new IndexOutOfBoundsException();
			}

		} catch (IndexOutOfBoundsException e) {
			guiControl.popUpMessage("System Error", "This is the last question");

		}
	}

	/**
	 * Used to go to previous question and sets all the previous question details
	 * @param ActionEvent event 
	 */
	@FXML
	void PreviousQuestionButtonPressed(ActionEvent event) {

		try {

			if (currentQuestion > 0) {
				currentQuestion--;
				setQuestion(currentQuestion);
				int chosenPrevious = questions.get(currentQuestion).getChosenAnswer();
				if (chosenPrevious == 1) {
					OptionA.setSelected(true);
				} else if (chosenPrevious == 2) {
					OptionB.setSelected(true);
				} else if (chosenPrevious == 3) {
					OptionC.setSelected(true);
				} else if (chosenPrevious == 4) {
					OptionD.setSelected(true);
				}

			} else {
				currentQuestion = 0;
				throw new IndexOutOfBoundsException();
			}

		} catch (IndexOutOfBoundsException e) {
			guiControl.popUpMessage("System Error", "This is the first question");
		}

	}

	/**
	 * 
	 * Used to submit the exam and insert it to database.
	 * @param ActionEvent event
	 * @throws IOException 
	 */
	@FXML
	void SubmitButtonPressed(ActionEvent event) throws IOException {
		GUIControl.popUpMessageYesNo("System Message", "Are you sure you want to submit your exam?");
		if (GUIControl.res == true) {
			for (int i = 0; i < questions.size(); i++) {
				QuestionInExam que = questions.get(i);
				if (que.getChosenAnswer() != -1 && que.getChosenAnswer() == que.getCorrectAnswer()) {
					TotalGrade += que.getPointsQuestion();
				}
			}
			String timeTaken;
			if (minutes >= 100) {
				timeTaken = String.format("%03d:%02d%n", tickMinutes, tickSeconds);

			} else {
				timeTaken = String.format("%02d:%02d%n", tickMinutes, tickSeconds);

			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			SolvedExam solvedExam = new SolvedExam(exam.getEid(), exam.getSid(), exam.getCid(), exam.getName(),
					exam.getDate(), exam.getTdescription(), exam.getSdescription(),
					((Student) guiControl.getUser()).getId(), exam.getTotalTime(), exam.getCode(), exam.getMode(),
					timeTaken, currentDate, TotalGrade + "");
			solvedExam.setSubmitted("Yes");
			ClientMessage examMessage = new ClientMessage(ClientMessageType.INSERT_EXAM_TO_DB, solvedExam);
			guiControl.sendToServer(examMessage);
			String SEid = (String) guiControl.getServerMsg().getMessage();
			if (SEid == null) {
				SEid = "000001";
			}

			Object[] toSend = new Object[] { questions, SEid };
			ClientMessage questionsMessage = new ClientMessage(ClientMessageType.INSERT_EXAM_QUESTIONS, toSend);
			guiControl.sendToServer(questionsMessage);
			boolean sent = (boolean) guiControl.getServerMsg().getMessage();
			if (sent == false) {
				guiControl.popUpMessage("System Message", "There was a problem with submission of question");
			} else {
				guiControl.popUpMessage("System Message", "Exam Submitted Succesfully");
				timer.stop();
				guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);

			}

		}

	}

	/**
	 * Used to set the current exam, sort the questions array, set the timer.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OptionA.setToggleGroup(answersGroup);
		OptionB.setToggleGroup(answersGroup);
		OptionC.setToggleGroup(answersGroup);
		OptionD.setToggleGroup(answersGroup);
		String toPrint = null;
		String toInitilaize = null;
		exam = StudentComputerizedExamController.currentExam;
		questions = exam.getExamQuestions();
		String Time = exam.getTotalTime();
		NumOfQuestionsText.setText("# of questions: " + exam.getExamQuestions().size());
		NotesToStudent.setText(exam.getSdescription());
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
		TimeOfExam.setText(toPrint);
		TimeTick.setText(toInitilaize);
		NameOfExam2.setText(exam.getName());

		timer = new Timer(1000, new ActionListener() {
			int Minutes = 0;
			int Seconds = 0;
			int TotalMinutes = minutes;
			int TotalSeconds = seconds;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String toPrintEveryTime = null;
				String toPrint=null;
				if (addition != 0) {
					guiControl.popUpMessage("System Message", addition + " Minutes have been added!");
					minutes += addition;
					TotalMinutes+=addition;

					if (minutes >= 100) {
						 toPrint = String.format("Total Time is:\n %03d:%02d%n", minutes, seconds);
					} else {
						 toPrint = String.format("Total Time is:\n %02d:%02d%n", minutes, seconds);
						
					}
					TimeOfExam.setText(toPrint);
					addition = 0;
				}
				if (minutes >= 100) {
					toPrintEveryTime = String.format("%03d:%02d%n", Minutes, Seconds);
				} else {
					toPrintEveryTime = String.format("%02d:%02d%n", Minutes, Seconds);
				}
				TimeTick.setText(toPrintEveryTime);
				Seconds++;
				if (Seconds == 60) {
					Seconds = 0;
					Minutes++;
				}
				updateTime(Minutes, Seconds);
				if (Minutes == TotalMinutes && Seconds == TotalSeconds) {
					timer.stop();
					guiControl.popUpMessage("System Message",
							"Exam Time is Over!!\nPlease contact your Teacher\nExam has not been submitted");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								timer.stop();
								guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);
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

		questions.sort(new Comparator<QuestionInExam>() {
			@Override
			public int compare(QuestionInExam o1, QuestionInExam o2) {

				if (o1.getNumOfQuestion() == o2.getNumOfQuestion())
					return 0;
				else if (o1.getNumOfQuestion() < o2.getNumOfQuestion())
					return -1;
				else
					return 1;

			}

		});
		setQuestion(0);

	}

	/**
	 * @param num - number of question sets the number of question given the number
	 *            of question.
	 */
	public void setQuestion(int num) {

		QuestionInExam que = questions.get(num);
		QuestionNumberTitle.setText("Question " + que.getNumOfQuestion());
		ContentOfQuestion.setText(que.getText());
		OptionAText.setText(que.getAnsA());
		OptionBText.setText(que.getAnsB());
		OptionCText.setText(que.getAnsC());
		OptionDText.setText(que.getAnsD());

	}

	/**
	 * @param ActionEvent event Sets the radio button if option 1 is selected.
	 */
	@FXML
	void Option1IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(1);
	}

	/**
	 * @param ActionEvent event Sets the radio button if option 2 is selected.
	 */
	@FXML
	void Option2IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(2);
	}

	/**
	 * @param ActionEvent event Sets the radio button if option 3 is selected.
	 */
	@FXML
	void Option3IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(3);

	}

	/**
	 * @param ActionEvent event Sets the radio button if option 4 is selected.
	 */
	@FXML
	void Option4IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(4);

	}

	/**
	 * @param minutes
	 * @param seconds Used to save the time passed since the beggining of the exam
	 */
	public void updateTime(int minutes, int seconds) {
		this.tickMinutes = minutes;
		this.tickSeconds = seconds;
	}

	/**
	 * @throws IOException Used to stop the exam if the teacher locked it
	 */
	public void stopExam() throws IOException {
		GUIControl.popUpMessage("System Message", "Exam has been locked by Teacher");
		for (int i = 0; i < questions.size(); i++) {
			QuestionInExam que = questions.get(i);
			if (que.getChosenAnswer() != -1 && que.getChosenAnswer() == que.getCorrectAnswer()) {
				TotalGrade += que.getPointsQuestion();
			}
		}
		String timeTaken;
		if (minutes >= 100) {
			timeTaken = String.format("%03d:%02d%n", tickMinutes, tickSeconds);

		} else {
			timeTaken = String.format("%02d:%02d%n", tickMinutes, tickSeconds);

		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		SolvedExam solvedExam = new SolvedExam(exam.getEid(), exam.getSid(), exam.getCid(), exam.getName(),
				exam.getDate(), exam.getTdescription(), exam.getSdescription(),
				((Student) guiControl.getUser()).getId(), exam.getTotalTime(), exam.getCode(), exam.getMode(),
				timeTaken, currentDate, TotalGrade + "");
		solvedExam.setSubmitted("Yes");
		ClientMessage examMessage = new ClientMessage(ClientMessageType.INSERT_EXAM_TO_DB, solvedExam);
		guiControl.sendToServer(examMessage);
		String SEid = (String) guiControl.getServerMsg().getMessage();
		if (SEid == null) {
			SEid = "000001";
		}

		Object[] toSend = new Object[] { questions, SEid };
		ClientMessage questionsMessage = new ClientMessage(ClientMessageType.INSERT_EXAM_QUESTIONS, toSend);
		guiControl.sendToServer(questionsMessage);
		boolean sent = (boolean) guiControl.getServerMsg().getMessage();
		if (sent == false) {
			guiControl.popUpMessage("System Message", "There was a problem with submission of question");
		} else {
			timer.stop();
			guiControl.loadStage(ClientsConstants.Screens.STUDENT_MAIN_PAGE.path);

		}

	}
	
	/**
	 * Sets additional time
	 * @param Object additionTime
	 */
	public void setAddition(Object additionTime) {
		String time=(String)additionTime;
		addition=Integer.parseInt(time);
	}

}
