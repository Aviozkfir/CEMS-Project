package application;

import gui.GUIControl;

public class UpdateThread extends Thread{
	private GUIControl guiControl = GUIControl.getInstance();
		public void run(){  
		System.out.println("thread is running...");  
		while(true) {
			try {
				guiControl.CountRequest();
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		}  
	  
}
