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

public class StudentExamExecutionController extends StudentComputerizedExamController implements Initializable {

	private int seconds;
	private int minutes;
	private int currentQuestion = 0;
	private ArrayList<QuestionInExam> questions;
	private int TotalGrade = 0;
	private int tickSeconds = 0;
	private int tickMinutes = 0;

	@FXML
	private ToggleGroup answersGroup;

	@FXML
	private Text NumOfQuestionsText;

	@FXML
	private Text TimerText;

	private Timer timer;

	private static ExamForStudent exam;

	@FXML
	private Text TimeOfExam;

	@FXML
	private Text TimeTick;

	@FXML
	private Text NameOfExam2;

	@FXML
	private Text NotesToStudent;

	@FXML
	private Text QuestionNumberTitle;

	@FXML
	private Text ContentOfQuestion;

	@FXML
	private RadioButton OptionA;

	@FXML
	private Text OptionAText;

	@FXML
	private RadioButton OptionB;

	@FXML
	private Text OptionBText;

	@FXML
	private RadioButton OptionC;

	@FXML
	private Text OptionCText;

	@FXML
	private RadioButton OptionD;

	@FXML
	private Text OptionDText;

	@FXML
	private Button PreviousQuestionButton;

	@FXML
	private Button NextQuestionButton;

	@FXML
	private Button SubmitButton;

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

	public void setQuestion(int num) {

		QuestionInExam que = questions.get(num);
		QuestionNumberTitle.setText("Question " + que.getNumOfQuestion());
		ContentOfQuestion.setText(que.getText());
		OptionAText.setText(que.getAnsA());
		OptionBText.setText(que.getAnsB());
		OptionCText.setText(que.getAnsC());
		OptionDText.setText(que.getAnsD());

	}

	@FXML
	void Option1IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(1);
	}

	@FXML
	void Option2IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(2);
	}

	@FXML
	void Option3IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(3);

	}

	@FXML
	void Option4IsSelected(ActionEvent event) {
		questions.get(currentQuestion).setChosenAnswer(4);

	}

	public void updateTime(int minutes, int seconds) {
		this.tickMinutes = minutes;
		this.tickSeconds = seconds;
	}
	
	public void stopExam() throws IOException {
		GUIControl.popUpMessageYesNo("System Message", "Exam has been locked by Teacher");
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
