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

//import com.sun.javafx.webkit.ThemeClientImpl;

//import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

//import com.sun.media.jfxmedia.events.NewFrameEvent;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
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
	public static PersonCEMS validatePerson(String[] idAndPassword) throws SQLException {
		System.out.println(idAndPassword[0]+" "+idAndPassword[1]);
		String role;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Person where Id=? and Password=?");
		logInPreparedStatement.setString(1, idAndPassword[0]);
		logInPreparedStatement.setString(2, idAndPassword[1]);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if(rs.getFetchSize()==0) {
			return null;
		}
		role = rs.getString(5);//Change according to table.
		if (role.equals("Teacher")) {
			if (rs.next()) {
				return new Teacher(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5));
			}
		}
		if (role.equals("Principal")) {
			if (rs.next()) {
				return new Principal(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4),
						rs.getString(5));
			}
		}
		if (role.equals("Student")) {
			if (rs.next()) {
				return new Student(rs.getString(2), rs.getString(3), rs.getString(1), rs.getString(4), rs.getString(5));
			}
		}
		return null;
	}

}
