import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.PersonCEMS;
import entity.Principal;
import entity.Student;
import entity.Teacher;
import mySQL.MySQLConnection;
import server.ServerCEMS;

/**
 * @author guy, sharon
 * test the sql queries and output of server
 */
public class ServerCEMSTest {
	//Creating veriables.
	private String username;
	private String password;
	private ServerCEMS server;
	private PersonCEMS person;
	String[] IdAndPass;
	String[] inputData;
	ArrayList<String> report;

	@BeforeClass
	public static void openConnection()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		MySQLConnection.connectToDB();
	}

	@Before
	public void setUp() throws Exception {
		server = new ServerCEMS(5555);
		IdAndPass = new String[2];
		inputData = new String[2];
		report = new ArrayList<String>();
	}

	@After
	public void tearDown() throws Exception {
		server.close();
	}

	
	
	
	/**
	 * check a valid login of Principal
	 * output: Principal object
	 */
	@Test
	public void logInPrincipal() {
		IdAndPass[0] = "312231222";
		IdAndPass[1] = "123";
		Object result;
		try {
			result = server.validatePerson(IdAndPass);
			assertTrue(server.userList.contains(result));
			assertTrue(result instanceof Principal);
			Principal teacher = (Principal) result;
			assertEquals(teacher.getFirstName(),"Moti");
			assertEquals(teacher.getLastName(),"Hanuka");
			assertEquals(teacher.getEmail(),"Moti.Hanuka@gmail.com");
			
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	
	
	
	/**
	 * check a valid login of Teacher
	 * output: Teacher object
	 */
	@Test
	public void logInTeacher() {
		IdAndPass[0] = "311285648";
		IdAndPass[1] = "123";
		Object result;
		try {
			result = server.validatePerson(IdAndPass);
			assertTrue(server.userList.contains(result));
			assertTrue(result instanceof Teacher);
			Teacher teacher = (Teacher) result;
			assertEquals(teacher.getFirstName(),"Kfir");
			assertEquals(teacher.getLastName(),"Abuhashmil");
			assertEquals(teacher.getEmail(),"Kfir.Abuhashmil@gmail.com");
			
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	
	
	/**
	 * check a valid login of Student
	 * output: Student object
	 */
	@Test
	public void logInStudent() {
		IdAndPass[0] = "207417395";
		IdAndPass[1] = "GuyArbel123";
		Object result;
		try {
			result = server.validatePerson(IdAndPass);
			assertTrue(server.userList.contains(result));
			assertTrue(result instanceof Student);
			Student teacher = (Student) result;
			assertEquals(teacher.getFirstName(),"Guy");
			assertEquals(teacher.getLastName(),"Arbel");
			assertEquals(teacher.getEmail(),"Guyarb6@gmail.com");
			
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	
	
	/**
	 * check a double logg in of same user.
	 * output: 'logged in' String
	 */
	@Test
	public void alreadyloggedIn() {
		IdAndPass[0] = "207417395";
		IdAndPass[1] = "GuyArbel123";
		Object result;
		try {
			server.validatePerson(IdAndPass);
			result = server.validatePerson(IdAndPass);
			assertEquals(result,"logged in");
			
		} catch (SQLException e) {
			assertTrue(false);
		}
		
		
	}
	
	/**
	 * check check invalid log in
	 * output: null
	 */
	@Test
	public void invalidUserlogIn() {
		IdAndPass[0] = "lordVoldemord";
		IdAndPass[1] = "123";
		Object result;
		try {
			
			result = server.validatePerson(IdAndPass);
			assertEquals(result,null);
			
		} catch (SQLException e) {
			assertTrue(false);
		}
		
		
	}
	

	
	// Check that the user gets from database the grades of the specific exam we want from specific date(for report).
		// Input: inputData.
		// Result: True.
	@Test
	public void GetReportDataSuccessfully() throws ParseException {
		report.add("100");
		report.add("25");
		report.add("0");
		inputData[0] = "010103";
		inputData[1] = "2000-05-20";
		Object result;
		try {
			result = server.getTeacherSolvedExamReport(inputData);
			assertEquals(result, report);
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	// Check that the user gets from database Empty grades for not exist exam from database with a  specific date(for report).
		// Input: inputData.
		// Result: True.
	@Test
	public void GetReportDataEmpty() throws ParseException {
		inputData[0] = "010104";
		inputData[1] = "2021-05-20";
		Object result;
		try {
			result = server.getTeacherSolvedExamReport(inputData);
			report = (ArrayList<String>) result;
			assertTrue(report.isEmpty());
		} catch (SQLException e) {
			assertTrue(false);
		}
	}
	
	// Check that null object instead of exam id and date catch the exception.
		// Input: null
		// Result: NullPointerException.
	@Test
	public void GetReportDataNull() throws ParseException {
		inputData[0] = "010104";
		inputData[1] = "2021-05-20";
		Object result;
		try {
			result = server.getTeacherSolvedExamReport(null);
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(),null);
		}
	}
	
	
	
	
	
}
