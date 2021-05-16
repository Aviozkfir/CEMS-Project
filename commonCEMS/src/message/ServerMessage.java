package message;

import java.io.Serializable;
/**
 * ServerMessage is with a type and an Object message that the server sends to the client
 */
public class ServerMessage implements Serializable{
	private ServerMessageTypes type;
	private Object message;
	public ServerMessage(ServerMessageTypes type, Object message) {
		this.type = type;
		this.message = message;
	}
	public ServerMessageTypes getType() {
		return type;
	}
	public void setType(ServerMessageTypes type) {
		this.type = type;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return getType().toString() +" " + message;
	}
}
