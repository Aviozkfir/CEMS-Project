package server;

import java.util.ArrayList;

import entity.Exam;
import ocsf.server.ConnectionToClient;



public class currentExam {

	private Exam exam;

	public Exam getExam() {
		return exam;
	}

	public currentExam(Exam exam, int amountOfStudents, ConnectionToClient teacher) {
		this.amountOfStudents=amountOfStudents;
		this.Eid=exam.getEid();
		this.exam=exam;
		this.teacher=teacher;
	}
		private ArrayList<ConnectionToClient> conToClientStudent=new ArrayList<>();	
		private ArrayList<ConnectionToClient> nonConToClientStudent=new ArrayList<>();
		
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
			
		

		private int amountOfStudents;
		private int amountOfFinishedStudents;
		
		public boolean allStudentAreFinished() {
			amountOfFinishedStudents++;
			if(amountOfStudents!=amountOfFinishedStudents)
				return false;
			return true;
		}
		
		public void remove(ConnectionToClient student) {
			nonConToClientStudent.add(student);
			conToClientStudent.remove(student);
		}
		
		public boolean add(ConnectionToClient student) {
			for(ConnectionToClient e : nonConToClientStudent)
				if(e.equals(student))
					return false;
					
			
			conToClientStudent.add(student);
			return true;
		}
}
