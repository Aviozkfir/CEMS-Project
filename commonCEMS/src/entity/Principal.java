package entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Principal extends PersonCEMS implements Serializable{

	
	//private ArrayList<Request> requestLists = new ArrayList<Request>(); //need to create Request class and set/get. 
	
	public Principal(String firstName, String lastName, String id, String email,String role) {
		super(firstName, lastName,id, email, role);
	}







}
