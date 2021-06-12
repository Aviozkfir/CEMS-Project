package application;

import entity.Principal;
import gui.GUIControl;

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
					System.out.println("\n got in \n");
					
					if(guiControl.getUser() instanceof Principal) {
					guiControl.CountRequest();
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
