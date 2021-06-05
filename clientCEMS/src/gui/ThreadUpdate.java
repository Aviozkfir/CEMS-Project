//package gui;
//
//public class ThreadUpdate extends Thread {
//	
//	private GUIControl guiControl = GUIControl.getInstance();
//	
//		public void run(){  
//		PrincipalMainPageController controller = (PrincipalMainPageController) guiControl.getController();
//		System.out.println("thread is running..."); 
//		while(true) {
//		
//		try {
//			while(true) {
//			controller.CountRequest();
//			Thread.sleep(60000);
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		}
//		}  
//}
