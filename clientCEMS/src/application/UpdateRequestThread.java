package application;

import gui.GUIControl;

public class UpdateRequestThread extends Thread{
private boolean exit = false;
	private GUIControl guiControl = GUIControl.getInstance();
		public void run(){  
		System.out.println("thread is running...");  
		while(!exit) {
			try {
				guiControl.CountRequest();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		}  
		
		// for stopping the thread
	    public void exit()
	    {
	        exit = true;
	    }
	}
