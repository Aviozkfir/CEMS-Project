package application;

import gui.GUIControl;

/**
 * This class purpose is to check some specific scenarios that we need in real
 * time in the client.
 * 
 * @author On Avioz.
 * @extends Thread.
 * 
 *
 *
 */
public class UpdateThread extends Thread {
	/**
	 * exit is true when user logs out.
	 */
	private boolean exit = false;
	/**
	 * check is true when the scenarios we check happened.
	 */
	private boolean check = false;
	/**
	 * instance of guicontrol.
	 */
	private GUIControl guiControl = GUIControl.getInstance();
	/**
	 * The thread.
	 */
	private UpdateThread thread;

	/**
	 * Constructor for the thread.
	 */
	public UpdateThread() {
		super();
		thread = this;
	}

	/**
	 *run method for the thread.
	 */
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

	/**
	 * This method set value true for boolean variable exit, this happened when user
	 * log out.
	 */
	public void exit() {
		exit = true;
		thread.interrupt();
	}

	/**
	 * This method set value true for boolean variable check, this happening when
	 * the scenario we want to check happened.
	 */
	public void Check() {
		check = true;
		thread.interrupt();
	}
}
