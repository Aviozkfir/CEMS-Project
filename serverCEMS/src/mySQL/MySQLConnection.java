package mySQL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import entity.Course;
import entity.CourseReport;
import entity.Exam;
import entity.ManualExamFile;

//import com.sun.javafx.webkit.ThemeClientImpl;

//import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.PersonCEMS;
import entity.Principal;
import entity.Question;
import entity.QuestionInExam;
import entity.Request;
import entity.SolvedExam;
import entity.Student;
import entity.Subject;
import entity.Teacher;
import message.ServerMessage;
import message.ServerMessageTypes;

/**
 * class that holds static methods related to database actions such as
 * connection, queries,updates and more
 */
public class MySQLConnection {
	private static Connection con;

	public static void connectToDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		System.out.println("Driver definition succeed");
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yguCgBTIbt?autoReconnect=true&serverTimezone=IST",
				"yguCgBTIbt", "y7ubDV7FNy");
		System.out.println("SQL connection succeed");
	}

	/**
	 * A method that returns a Visitor with id. Insert to DataBase if there is not.
	 * 
	 * @param id
	 * @return Subscriber or null
	 * @throws SQLException
	 */
	public static Object validatePerson(String[] idAndPassword) throws SQLException {
		// System.out.println(idAndPassword[0] + " " + idAndPassword[1]);
		String role;
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Person where Id=? and Password=?");
		logInPreparedStatement.setString(1, idAndPassword[0]);
		logInPreparedStatement.setString(2, idAndPassword[1]);
		rs = logInPreparedStatement.executeQuery();
		if (!(rs.next())) {
			return null;
		}

		role = rs.getString(5);// Change according to table.
		if (role.equals("Teacher")) {
			return new Teacher(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5));

		}
		if (role.equals("Principal")) {

			return new Principal(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5));
		}
		if (role.equals("Student")) {
			return new Student(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5));

		}
		return null;
	}

	public static Object getTeacherSubjects(String teacherID) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT DISTINCT s.Sid,s.name FROM Person_Enrolled_Course p, Course c, Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, teacherID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			subjectList.add(new Subject(rs.getString(2), rs.getString(1)));
		}
		return subjectList;
	}

	public static Object getTeacherCourses(String teacherID) throws SQLException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT p.Cid,c.name,s.Sid,s.name FROM Person_Enrolled_Course p, Course c,Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, teacherID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			courseList.add(new Course(rs.getString(2), rs.getString(1), new Subject(rs.getString(4), rs.getString(3))));
		}
		return courseList;
	}

	public static Object getPrincipalSubjects(String principalID) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Subject");
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			subjectList.add(new Subject(rs.getString(2), rs.getString(1)));
		}
		return subjectList;
	}

	public static Object getPrincipalCourses(String principalID) throws SQLException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT c.Cid,c.name,s.Sid,s.name FROM Course c,Subject s WHERE c.Sid=s.Sid");
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			courseList.add(new Course(rs.getString(2), rs.getString(1), new Subject(rs.getString(4), rs.getString(3))));
		}
		return courseList;
	}

	public static Object getPrincipalStudentList() throws SQLException {
		ArrayList<Student> studentList = new ArrayList<Student>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Id, s.FirstName, s.LastName, s.Email  FROM Person s WHERE s.Role = 'Student'");
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			studentList.add(new Student(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), null));
		}
		return studentList;
	}

	public static Object getPrincipalTeacherList() throws SQLException {
		ArrayList<Teacher> studentList = new ArrayList<Teacher>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Id, s.FirstName, s.LastName, s.Email  FROM Person s WHERE s.Role = 'Teacher'");
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			studentList.add(new Teacher(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), null));
		}
		return studentList;
	}

	public static Object getPrincipalReportCourses(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		String str;
		String[] stringarr;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.cid AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {
			str = rs.getString(2);
			stringarr = str.split("-"); // split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]); // put grade and date

		}
		return report;

	}

	public static Object getPrincipalReportStudents(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		String str;
		String[] stringarr;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=s.ID AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {
			str = rs.getString(2);
			stringarr = str.split("-"); // split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]); // put grade and date

		}
		return report;

	}

	public static Object getPrincipalReportTeachers(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		String str;
		String[] stringarr;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.ID AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {
			str = rs.getString(2);
			stringarr = str.split("-"); // split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]); // put grade and date

		}
		return report;

	}

	public static boolean validateExamCode(String examCode) throws SQLException {
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT Code FROM Exams WHERE Code=?");
		logInPreparedStatement.setString(1, examCode);
		rs = logInPreparedStatement.executeQuery();
		if (!rs.next()) {
			return false;
		} else if (rs.getString(1).equals(examCode)) {
			return true;
		}
		return false;
	}

	public static Object getExamInformation(String examCode) throws SQLException {
		Exam exam = null;
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Exams WHERE Code=?");
		logInPreparedStatement.setString(1, examCode);
		rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			exam = new Exam(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),rs.getString(11));
		}
		return exam;
	}

	
	

//
//	public static Object checkForDuplicateQuestion(Question q, ArrayList<Course> course) throws SQLException {
//		ArrayList<Question> questionList = new ArrayList<Question>();
//		ResultSet rs;
//		PreparedStatement logInPreparedStatement;
//		StringBuilder s= new StringBuilder("(qc.Cid=?");
//		for(int i=0; i<course.size()-1;i++)
//			s.append(" OR qc.Cid=?");
//		
//		logInPreparedStatement = con
//				.prepareStatement("SELECT c.name"
//						+ "FROM Questions q, Question_In_Course qc Course c" + "where q.Qid=qc.Qid AND qc.Cid=c.Cid AND  q.Text=? AND q.Ans1=? AND q.Ans2=? AND q.Ans3=? AND q.Ans4=? AND q.CorrectAns=?" +"AND "+s.toString()+")");
//		logInPreparedStatement.setString(1, q.getText());
//		logInPreparedStatement.setString(2, q.getAnsA());
//		logInPreparedStatement.setString(3, q.getAnsB());
//		logInPreparedStatement.setString(4, q.getAnsC());
//		logInPreparedStatement.setString(5, q.getAnsD());
//		logInPreparedStatement.setString(6, ""+q.getCorrectAnswar());
//		
//		for(int i=7; i<course.size()+7;i++)
//			logInPreparedStatement.setString(i, course.get(i).getId());
//		rs = logInPreparedStatement.executeQuery();
//		while (rs.next()) {
//		
//		}
//		return questionList;
//	}
	
	public static Object getQuestionByCourse(Course course) throws SQLException {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT q.Qid, q.Text, q.Ans1, q.Ans2, q.Ans3, q.Ans4, q.CorrectAns, q.ID, q.DATE "
						+ "FROM Questions q, Question_In_Course qc " + "where q.Qid=qc.Qid AND qc.Cid=?");
		logInPreparedStatement.setString(1, course.getId());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			//questionList.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					//rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9)));
		}
		return questionList;
	}
	
	
	public static void deletedQuestion(Question q) throws SQLException {
		
		
		PreparedStatement delQ =con.prepareStatement("DELETE FROM Questions WHERE Qid=?");
		delQ.setString(1, q.getId());
		delQ.executeUpdate();
		
		
		
		PreparedStatement delQC;
		delQC = con.prepareStatement("DELETE FROM Question_In_Course WHERE Qid=?");
		delQC.setString(1, q.getId());
		delQC.executeUpdate();
		
	}
	
	public static void addQuestionByCourses(Question q, ArrayList<Course> course) throws SQLException {
		
		ResultSet maxID;
		PreparedStatement getMaxID =con.prepareStatement("SELECT MAX(q.Qid) FROM Questions q where q.Sid=?");
		getMaxID.setString(1, course.get(0).getSubject().getId());
		maxID=getMaxID.executeQuery();
		String id;
		
		if(maxID.next()) {
			id=(""+(1+Integer.parseInt(maxID.getNString(1))));
			if(id.length()==4) {
				id="0"+id;
			}
			q.setId(id);
		}
		else 
			q.setId(course.get(0).getSubject().getId()+"001");
		
		PreparedStatement addQuestion;
		addQuestion = con
				.prepareStatement("INSERT INTO Questions (Qid, Text, Ans1, Ans2, Ans3, Ans4, CorrectAns, ID,DATE,Sid) "
						+ "VALUES (?, ? ,? ,? ,? ,? ,? ,? ,? ,? )");
		addQuestion.setString(1, q.getId());
		addQuestion.setString(2, q.getText());
		addQuestion.setString(3, q.getAnsA());
		addQuestion.setString(4, q.getAnsB());
		addQuestion.setString(5, q.getAnsC());
		addQuestion.setString(6, q.getAnsD());
		addQuestion.setInt(7, q.getCorrectAnswer());
		addQuestion.setString(8, q.getAuthor());
		addQuestion.setString(9, q.getModified());
		addQuestion.setString(10, q.getSubject());
		addQuestion.executeUpdate();
		System.out.print("slnfa;lsj;gnalj");
		for(Course c : course) {
			PreparedStatement addToCourse =con
					.prepareStatement("INSERT INTO Question_In_Course (Qid,Cid) "
							+ "VALUES (?, ?)");
			
			addToCourse.setString(1, q.getId());
			addToCourse.setString(2, c.getId());
			addToCourse.executeUpdate();
		}
	}

	public static Object getPrincipalRequests() throws SQLException {
		ArrayList<Request> requestList = new ArrayList<Request>();
		ResultSet rs;
		PreparedStatement statment;
		statment = con
				.prepareStatement("SELECT r.Enum, r.title, r.currentDuration, r.newDuration, r.Tid  FROM Requests r WHERE r.Status = 'StandBy' AND (r.Status2 ='Waiting' OR Status2 ='Working')");
		rs = statment.executeQuery();
		while (rs.next()) {
			requestList.add(
					new Request(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
		}
		
		return requestList;
	}

	public static Object updatePrincipalApprovedRequests(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con
					.prepareStatement("UPDATE `Requests` SET `Status` = 'Approved' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeded";
	}
	
	public static Object updatePrincipalDeclinedRequests(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con
					.prepareStatement("UPDATE `Requests` SET `Status` = 'Declined' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeded";
	}
	
	public static Object getRequestCount() throws SQLException {
		int Counter = 0;
		ResultSet rs;
		PreparedStatement statment;
		statment = con.prepareStatement("SELECT COUNT(*) FROM Requests r WHERE r.Status = 'StandBy' AND r.Status2 = 'Waiting'");
		rs = statment.executeQuery();
		rs.next();
			 Counter = rs.getInt(1);
		return Counter;
	}
	
	public static Object updatePrincipalRequestStatus(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con
					.prepareStatement("UPDATE `Requests` SET `Status2` = 'Working' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeded";
	}
	

	public static boolean validateStudentID(String StudentId) throws SQLException {
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT Id FROM Person WHERE Role='Student' AND Id=?");
		logInPreparedStatement.setString(1, StudentId);
		rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			if (rs.getString(1).equals(StudentId)) {
				return true;
			}
		}
		return false;
	}

	public static boolean uploadManualExam(Object[] details) throws SQLException, FileNotFoundException {
		String Eid = (String) details[0];
		File ExamFile = (File) details[1];
		String ID = (String) details[2];
		InputStream inputstream = new FileInputStream(ExamFile);
		int rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("INSERT INTO `Student_Manual_Exam` (`Eid`,`ExamFile` ,`ID`) VALUES (?,?,?)");
		logInPreparedStatement.setString(1, Eid);
		logInPreparedStatement.setBlob(2, inputstream);
		logInPreparedStatement.setString(3, ID);
		try {
			rs = logInPreparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public static Object downloadManualExam(String ExamID) throws SQLException, IOException {
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT ExamFile FROM `Teacher_Manual_Exam` WHERE Eid=?");
		logInPreparedStatement.setString(1, ExamID);
		rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			byte[] array = rs.getBytes("ExamFile");
			ManualExamFile manualExam = new ManualExamFile(array);
			return manualExam;

		} else {
			return null;
		}

	}

	public static Object returnExamQuestions(String ExamID) throws SQLException {
		boolean has = false;
		ArrayList<QuestionInExam> questions = new ArrayList<QuestionInExam>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT q.Qid,q.Qpoint,q.QuestionNum,y.Text,y.Ans1,y.Ans2,y.Ans3,y.Ans4,y.CorrectAns,y.ID,y.DATE FROM Question_In_Exams q,Questions y WHERE q.Eid=? AND q.Qid=y.Qid");
		logInPreparedStatement.setString(1, ExamID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			has = true;
			questions.add(new QuestionInExam(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
					rs.getString(8), rs.getInt(2), rs.getInt(3), rs.getString(1), rs.getString(10), rs.getString(11),
					rs.getInt(9)));
		}
		if (has == false) {
			return null;
		} else
			return questions;

	}

	public static boolean insertExamQuestions(Object[] data) throws SQLException {
		int rslt = -1;
		boolean success = true;
		PreparedStatement logInPreparedStatement;
		ArrayList<QuestionInExam> questions = (ArrayList<QuestionInExam>) data[0];
		String SEid = (String) data[1];
		for (int i = 0; i < questions.size(); i++) {
			QuestionInExam que = questions.get(i);
			logInPreparedStatement = con.prepareStatement(
					"INSERT INTO `AnsweredQuestionsForExams` (`SEid`, `Qid`, `Ans`, `Qpoints`, `NotesForStudent`) VALUES (?,?,?,?,?)");
			logInPreparedStatement.setString(1, SEid);
			logInPreparedStatement.setString(2, que.getId());
			logInPreparedStatement.setInt(3, que.getChosenAnswer());
			if (que.getChosenAnswer() == que.getCorrectAnswer()) {
				logInPreparedStatement.setInt(4, que.getPointsQuestion());
			} else {
				logInPreparedStatement.setInt(4, 0);
			}
			logInPreparedStatement.setString(5, "");
			rslt = logInPreparedStatement.executeUpdate();
			if (rslt == 0) {
				success = false;
			}
		}

		return success;
	}

	public static String insertExamToDB(SolvedExam exam) throws SQLException, ParseException {
		ResultSet rs;
		int rslt;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT MAX(SEid) FROM SolvedExams");
		rs = logInPreparedStatement.executeQuery();
		String Eid = "";
		if (rs.next()) {
			String eid = rs.getString(1);
			int i = Integer.parseInt(eid.trim());
			i++;
			Eid = String.format("%06d", i);

		} else {
			Eid = "000001";
		}
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(exam.getFinishDate());
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		logInPreparedStatement = con.prepareStatement(
				"INSERT INTO `SolvedExams` (`SEid`, `ID`, `Eid`, `DATE`, `Grade`, `TimeOfExe`,`NotesForStudent` ,`Submitted`, `Checked`) VALUES(?,?,?,?,?,?,?,?,?)");
		logInPreparedStatement.setString(1, Eid);
		logInPreparedStatement.setString(2, exam.getID());
		logInPreparedStatement.setString(3, exam.getEid());
		logInPreparedStatement.setDate(4, dateInputData);
		logInPreparedStatement.setString(5, exam.getFinalGrade());
		logInPreparedStatement.setString(6, exam.getFinishTime());
		logInPreparedStatement.setString(7, "");
		logInPreparedStatement.setString(8, exam.getSubmitted());
		logInPreparedStatement.setString(9, "No");
		rslt = logInPreparedStatement.executeUpdate();
		return Eid;

	}
	public static Object getStudentSubjects(String studentID) throws SQLException {
		ArrayList<Subject> subjectList = new ArrayList<Subject>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT DISTINCT s.Sid,s.name FROM Person_Enrolled_Course p, Course c, Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, studentID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			subjectList.add(new Subject(rs.getString(2), rs.getString(1)));
		}
		return subjectList;
	}
	
	public static Object getStudentCourses(String studentID) throws SQLException {
		ArrayList<Course> courseList = new ArrayList<Course>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT p.Cid,c.name,s.Sid,s.name FROM Person_Enrolled_Course p, Course c,Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, studentID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			courseList.add(new Course(rs.getString(2), rs.getString(1), new Subject(rs.getString(4), rs.getString(3))));
		}
		return courseList;
	}
	
}
