package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Principal extends PersonCEMS implements Serializable{

	
	//private ArrayList<Request> requestLists = new ArrayList<Request>(); //need to create Request class and set/get. 
	
	public Principal(String firstName, String lastName, String email, String role,String id) {
		super(firstName, lastName,id, email, role);
	}





}
