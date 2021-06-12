package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;



public class currentExam {

	

		private ArrayList<ConnectionToClient> conToClientStudent=new ArrayList<>();	

		public ArrayList<ConnectionToClient> getConToClientStudent() {
			return conToClientStudent;
		}

		public void setConToClientStudent(ArrayList<ConnectionToClient> conToClientStudent) {
			this.conToClientStudent = conToClientStudent;
		}

		public ConnectionToClient getTeacher() {
			return teacher;
		}

		public void setTeacher(ConnectionToClient teacher) {
			this.teacher = teacher;
		}

		public String getEid() {
			return Eid;
		}

		public void setEid(String eid) {
			Eid = eid;
		}

		private ConnectionToClient teacher;
			
		private String Eid;
			
		

}
