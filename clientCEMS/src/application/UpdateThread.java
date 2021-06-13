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
					
					guiControl.exeThread();
					
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
