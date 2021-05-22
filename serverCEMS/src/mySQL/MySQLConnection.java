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

//import com.sun.javafx.webkit.ThemeClientImpl;

//import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.PersonCEMS;
import entity.Principal;
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
		//System.out.println(idAndPassword[0] + " " + idAndPassword[1]);
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
		ArrayList<Subject> subjectList=new ArrayList<Subject>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT DISTINCT s.Sid,s.name FROM Person_Enrolled_Course p, Course c, Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, teacherID);
		rs = logInPreparedStatement.executeQuery();
		while(rs.next()) {
			subjectList.add(new Subject(rs.getString(2),rs.getString(1)));
		}
		return subjectList;
	}
	
	public static Object getTeacherCourses(String teacherID) throws SQLException {
		ArrayList<Course> courseList=new ArrayList<Course>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT p.Cid,c.name,s.Sid,s.name FROM Person_Enrolled_Course p, Course c,Subject s WHERE p.ID=? and c.Cid=p.Cid and c.Sid=s.Sid");
		logInPreparedStatement.setString(1, teacherID);
		rs = logInPreparedStatement.executeQuery();
		while(rs.next()) {
			courseList.add(new Course(rs.getString(2),rs.getString(1), new Subject(rs.getString(4), rs.getString(3))));
		}
		return courseList;
	}
	
	public static Object getPrincipalSubjects(String teacherID) throws SQLException {
		ArrayList<Subject> subjectList=new ArrayList<Subject>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Subject");
		rs = logInPreparedStatement.executeQuery();
		while(rs.next()) {
			subjectList.add(new Subject(rs.getString(2),rs.getString(1)));
		}
		return subjectList;
	}
	
	public static Object getPrincipalCourses(String teacherID) throws SQLException {
		ArrayList<Course> courseList=new ArrayList<Course>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT c.Cid,c.name,s.Sid,s.name FROM Course c,Subject s WHERE c.Sid=s.Sid");
		rs = logInPreparedStatement.executeQuery();
		while(rs.next()) {
			courseList.add(new Course(rs.getString(2),rs.getString(1), new Subject(rs.getString(4), rs.getString(3))));
		}
		return courseList;
	}

}
