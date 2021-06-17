import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import entity.Principal;
import entity.Student;
import entity.Teacher;
import gui.ILoginGetUserInput;
import gui.IServerClientCommunication;
import gui.LoginPageController;
import message.ClientMessage;
import message.ClientMessageType;
import message.ServerMessage;
import message.ServerMessageTypes;

/**
 * @author guy, sharon
 * test the functionality of login controller page.
 */
public class LoginPageControllerTest {

	 
	/**
	 * @author guy, sharon
	 * stub of IServerClientCommunication
	 * meant to be functionality of server and pop up messages.
	 */
	private class stubServerClientCommunication implements IServerClientCommunication{

		private Object toServerMsg;
		
		public Object getToServerMsg() {
			return toServerMsg;
		}

		

		private ServerMessage returnMsg;
		
		

		private String msg;
		
		private String errorMsg;
		
		private Object user;
		
		@Override
		public void sendToServer(Object msg) {
			toServerMsg=msg;
			
		}

		@Override
		public ServerMessage getServerMsg() {
			
			return returnMsg;
		}

		@Override
		public void popUpError(String msg) {
			
			errorMsg=msg;
		}

		@Override
		public void popUpMessage(String msg) {
			
			this.msg=msg;
		}

		@Override
		public Object getUser() {
			return user;
		}
		
		@Override
		public void setUser(Object user) {
			this.user=user;
		}
		
		public void setReturnMsg(ServerMessage returnMsg) {
			this.returnMsg = returnMsg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		
	}
		
	
	/**
	 * @author guy, sharon
	 * stub of ILoginGetUserInput
	 * meant to set the input pressed by user.
	 */
	private class stubILoginGetUserInput implements ILoginGetUserInput{

		private String ID;
		
		private String password;
		
		public stubILoginGetUserInput(String ID, String password) {
			this.ID=ID;
			this.password=password;
		}
		@Override
		public String getUserID() {
			
			return ID;
		}

		@Override
		public String getUserPassword() {
			
			return password;
		}
		
	}

	/**
	 * @author guy, sharon
	 * test for successful login of student
	 * input: name and password
	 * server output: server message with type: student login and object: student
	 * output: true
	 * error popUp: none.
	 */
	@Test
	public void studentSuccessfulLogin() {
		LoginPageController myCon = new LoginPageController();
		stubServerClientCommunication iServerClientCommunication = new stubServerClientCommunication();
		String userName = "Kfir abuhashmil";
		String passWord = "123";
		Student myStudent = new Student("kfir","abuhashmil",null,null,null);
		ILoginGetUserInput iLoginGetUserInput = new stubILoginGetUserInput(userName,passWord);
		
		myCon.setiLoginGetUserInput(iLoginGetUserInput);
		myCon.setiServerClientCommunication(iServerClientCommunication);
		iServerClientCommunication.setReturnMsg(new ServerMessage(ServerMessageTypes.LOGIN_STUDENT, myStudent));
		
		
		assertEquals(myCon.validateLogin(),true);
		assertEquals(((ClientMessage)iServerClientCommunication.getToServerMsg()).getType(),ClientMessageType.LOGIN_PERSON);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[0],userName);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[1],passWord);
		assertEquals(iServerClientCommunication.getServerMsg().getType(),ServerMessageTypes.LOGIN_STUDENT);
		assertEquals(iServerClientCommunication.getServerMsg().getMessage(),myStudent);
		assertEquals(myStudent,iServerClientCommunication.getUser());
		assertEquals(iServerClientCommunication.errorMsg,null);
		
		
	}
	
	
	/**
	 * @author guy, sharon
	 * test for successful login of teacher
	 * input: name and password
	 * server output: server message with type: teacher login and object: teacher
	 * output: true
	 * error popUp: none.
	 */
	@Test
	public void teacherSuccessfulLogin() {
		LoginPageController myCon = new LoginPageController();
		stubServerClientCommunication iServerClientCommunication = new stubServerClientCommunication();
		String userName = "Kfir abuhashmil";
		String passWord = "123";
		Teacher myTeacher = new Teacher("kfir","abuhashmil",null,null,null);
		ILoginGetUserInput iLoginGetUserInput = new stubILoginGetUserInput(userName,passWord);
		
		myCon.setiLoginGetUserInput(iLoginGetUserInput);
		myCon.setiServerClientCommunication(iServerClientCommunication);
		iServerClientCommunication.setReturnMsg(new ServerMessage(ServerMessageTypes.LOGIN_TEACHER, myTeacher));
		
		
		assertEquals(myCon.validateLogin(),true);
		assertEquals(((ClientMessage)iServerClientCommunication.getToServerMsg()).getType(),ClientMessageType.LOGIN_PERSON);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[0],userName);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[1],passWord);
		assertEquals(iServerClientCommunication.getServerMsg().getType(),ServerMessageTypes.LOGIN_TEACHER);
		assertEquals(iServerClientCommunication.getServerMsg().getMessage(),myTeacher);
		assertEquals(myTeacher,iServerClientCommunication.getUser());
		assertEquals(iServerClientCommunication.errorMsg,null);
	}
	
	
	/**
	 * @author guy, sharon
	 * test for successful login of principal
	 * input: name and password
	 * server output: server message with type: principal login and object: principal
	 * output: true
	 * error popUp: none.
	 */
	@Test
	public void principalSuccessfulLogin() {
		LoginPageController myCon = new LoginPageController();
		stubServerClientCommunication iServerClientCommunication = new stubServerClientCommunication();
		String userName = "Kfir abuhashmil";
		String passWord = "123";
		Principal myPrincipal = new Principal("kfir","abuhashmil",null,null,null);
		ILoginGetUserInput iLoginGetUserInput = new stubILoginGetUserInput(userName,passWord);
		
		myCon.setiLoginGetUserInput(iLoginGetUserInput);
		myCon.setiServerClientCommunication(iServerClientCommunication);
		iServerClientCommunication.setReturnMsg(new ServerMessage(ServerMessageTypes.LOGIN_PRINCIPAL, myPrincipal));
		
		
		assertEquals(myCon.validateLogin(),true);
		assertEquals(((ClientMessage)iServerClientCommunication.getToServerMsg()).getType(),ClientMessageType.LOGIN_PERSON);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[0],userName);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[1],passWord);
		assertEquals(iServerClientCommunication.getServerMsg().getType(),ServerMessageTypes.LOGIN_PRINCIPAL);
		assertEquals(iServerClientCommunication.getServerMsg().getMessage(),myPrincipal);
		assertEquals(myPrincipal,iServerClientCommunication.getUser());
		assertEquals(iServerClientCommunication.errorMsg,null);
	}
	
	/**
	 * @author guy, sharon
	 * test for successful login of student
	 * input: name and password
	 * server output: server message with type: student login and object: string : 'logged in'
	 * output: false
	 * error popUp: This user is already logged in
	 */
	@Test
	public void StudentAlreadyLoggedIn() {
		LoginPageController myCon = new LoginPageController();
		stubServerClientCommunication iServerClientCommunication = new stubServerClientCommunication();
		String userName = "Kfir abuhashmil";
		String passWord = "123";
		Student myStudent = new Student("kfir","abuhashmil",null,null,null);
		ILoginGetUserInput iLoginGetUserInput = new stubILoginGetUserInput(userName,passWord);
		
		myCon.setiLoginGetUserInput(iLoginGetUserInput);
		myCon.setiServerClientCommunication(iServerClientCommunication);
		iServerClientCommunication.setReturnMsg(new ServerMessage(ServerMessageTypes.LOGIN_STUDENT, "logged in"));
		
		
		assertEquals(myCon.validateLogin(),false);
		assertEquals(((ClientMessage)iServerClientCommunication.getToServerMsg()).getType(),ClientMessageType.LOGIN_PERSON);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[0],userName);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[1],passWord);
		assertEquals(iServerClientCommunication.getServerMsg().getType(),ServerMessageTypes.LOGIN_STUDENT);
		assertEquals(iServerClientCommunication.getServerMsg().getMessage(),"logged in");
		assertEquals(null,iServerClientCommunication.getUser());
		assertEquals(iServerClientCommunication.errorMsg,"This user is already logged in");
	}
	
	
	
	
	
	/**
	 * @author guy, sharon
	 * test for successful login of student
	 * input: name and password
	 * server output: server message with type: login person not found and object: null
	 * output: false
	 * error popUp: Login information doesn't exist\n Please try again
	 */
	@Test
	public void invalidInput() {
		LoginPageController myCon = new LoginPageController();
		stubServerClientCommunication iServerClientCommunication = new stubServerClientCommunication();
		String userName = "Kfir abuhashmil";
		String passWord = "123";
		
		ILoginGetUserInput iLoginGetUserInput = new stubILoginGetUserInput(userName,passWord);
		
		myCon.setiLoginGetUserInput(iLoginGetUserInput);
		myCon.setiServerClientCommunication(iServerClientCommunication);
		iServerClientCommunication.setReturnMsg(new ServerMessage(ServerMessageTypes.LOGIN_PERSON_NOT_FOUND, null));
		
		
		assertEquals(myCon.validateLogin(),false);
		assertEquals(((ClientMessage)iServerClientCommunication.getToServerMsg()).getType(),ClientMessageType.LOGIN_PERSON);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[0],userName);
		assertEquals(((String[])((ClientMessage)iServerClientCommunication.getToServerMsg()).getMessage())[1],passWord);
		assertEquals(iServerClientCommunication.getServerMsg().getType(),ServerMessageTypes.LOGIN_PERSON_NOT_FOUND);
		assertEquals(iServerClientCommunication.getServerMsg().getMessage(),null);
		assertEquals(null,iServerClientCommunication.getUser());
		assertEquals(iServerClientCommunication.errorMsg,"Login information doesn't exist\n Please try again");
	}
	
	

}
