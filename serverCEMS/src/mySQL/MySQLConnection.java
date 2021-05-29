package mySQL;

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

//import com.sun.javafx.webkit.ThemeClientImpl;

//import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.PersonCEMS;
import entity.Principal;
import entity.Question;
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
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/yguCgBTIbt?serverTimezone=IST",
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
	
	public static Object getPrincipalReportCourses(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		 String str;
		 String[] stringarr;
			Date dateInput=new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
			java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		
		
		logInPreparedStatement = con
				.prepareStatement("SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.cid AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();
		 

		while (rs.next()) {
			str=rs.getString(2);
			stringarr=str.split("-"); //split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]);  // put grade and date
			
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
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
		}
		return exam;
	}
	
	
	public static Object getQuestionByCourse(Course course) throws SQLException {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT q.Qid, q.Text, q.Ans1, q.Ans2, q.Ans3, q.Ans4, q.CorrectAns, q.ID, q.DATE "
						+ "FROM Questions q, Question_In_Course qc "
						+ "where q.Qid=qc.Qid AND qc.Cid=?");
		logInPreparedStatement.setString(1, course.getId());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			questionList.add(new Question(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),
										rs.getString(5),rs.getString(6), rs.getInt(7),rs.getString(8), rs.getString(9)));
		}
		return questionList;
	}
	
	public static Object getPrincipalStudentList() throws SQLException {
		ArrayList<Student> studentList = new ArrayList<Student>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT s.Id, s.FirstName, s.LastName, s.Email  FROM Person s WHERE s.Role = 'Student'");
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
		logInPreparedStatement = con
				.prepareStatement("SELECT s.Id, s.FirstName, s.LastName, s.Email  FROM Person s WHERE s.Role = 'Teacher'");
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			studentList.add(new Teacher(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), null));
		} 
		return studentList;
	}
	
	public static Object getPrincipalReportStudents(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		 String str;
		 String[] stringarr;
			Date dateInput=new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
			java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		
		
		logInPreparedStatement = con
				.prepareStatement("SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.cid AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();
		 

		while (rs.next()) {
			str=rs.getString(2);
			stringarr=str.split("-"); //split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]);  // put grade and date
			
		}
		return report;

	}
	
	public static Object getPrincipalReportTeachers(String[] data) throws SQLException, ParseException {
		HashMap<String, String> report = new HashMap<String, String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		 String str;
		 String[] stringarr;
			Date dateInput=new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
			java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		
		
		logInPreparedStatement = con
				.prepareStatement("SELECT s.Grade,s.Date FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.cid AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();
		 
		while (rs.next()) {
			str=rs.getString(2);
			stringarr=str.split("-"); //split the date yyyy/mm/dd
			report.put(rs.getString(1), stringarr[0]);  // put grade and date
			
		}
		return report;

	}
}
