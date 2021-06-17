import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.PersonCEMS;
import mySQL.MySQLConnection;
import server.ServerCEMS;

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

	@Test
	public void logInSubvalidInput() {
		IdAndPass[0] = "312231222";
		IdAndPass[1] = "123";
		Object result;
		try {
			result = server.validatePerson(IdAndPass);
			assertTrue(server.userList.contains(result));
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
