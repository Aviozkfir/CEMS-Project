package application;

import java.io.IOException;

import entity.Principal;
import entity.Student;
import entity.Teacher;
import gui.ClientsConstants;
import gui.GUIControl;
import gui.StudentExamExecutionController;
import gui.TeacherOngoingExamsController;

public class UpdateThread extends Thread {
	private boolean exit = false;
	private boolean check = false;
	private GUIControl guiControl = GUIControl.getInstance();
	private UpdateThread thread;

	
	
	public UpdateThread() {
		super();
		thread = this;
	}
	
	

	public void run() {

		System.out.println("thread is running...");

		while (!exit) {
			try {
				thread.sleep(Long.MAX_VALUE);
			
			} catch (InterruptedException e) {	
				if (check) {
					try {
						thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("--> a command from server:");
					
					if(guiControl.getUser() instanceof Principal) {
					guiControl.CountRequest();
					}
					else if(guiControl.getUser() instanceof Student) {
						if(guiControl.getController() instanceof StudentExamExecutionController)
							try {
								((StudentExamExecutionController)guiControl.getController()).stopExam();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					}
					else if(guiControl.getUser() instanceof Teacher) {
						if(guiControl.getController() instanceof TeacherOngoingExamsController) {
							
							
							try {
								TeacherOngoingExamsController a = (TeacherOngoingExamsController) GUIControl.getInstance().loadStage(ClientsConstants.Screens.TEACHER_ONGOING_EXAMS_PAGE.path);
								a.setOngoingExams();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							
					}
					
					check = false;
				}
			}

		}
	}

	// for stopping the thread
	public void exit() {
		exit = true;
		thread.interrupt();
	}

	public void Check() {
		check = true;
		thread.interrupt();
	}
}
