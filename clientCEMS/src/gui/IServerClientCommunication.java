package gui;

import java.io.IOException;

import message.ServerMessage;

public interface IServerClientCommunication {

	public void sendToServer(Object msg);

	public ServerMessage getServerMsg();

	public void popUpError(String msg);

	public void popUpMessage(String msg);
	
	public Object getUser();
	
	public void setUser(Object user);
	
}
