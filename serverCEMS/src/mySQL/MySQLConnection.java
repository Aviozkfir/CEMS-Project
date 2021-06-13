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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entity.Course;
import entity.Exam;
import entity.ManualExamFile;
import entity.Principal;
import entity.Question;
import entity.QuestionInExam;
import entity.Request;
import entity.SolvedExam;
import entity.SolvedExamToView;
import entity.SolvedExamType;
import entity.SolvedQuestionToView;
import entity.Student;
import entity.Subject;
import entity.Teacher;
import entity.TeachersExam;

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
		con = DriverManager.getConnection(
				"jdbc:mysql://remotemysql.com:3306/yguCgBTIbt?autoReconnect=true&serverTimezone=IST", "yguCgBTIbt",
				"y7ubDV7FNy");
		System.out.println("SQL connection succeed");
	}

	/**
	 * A method that returns an entity(Student or Teacher or Principal) . Insert to
	 * DataBase if there is not.
	 * 
	 * @param idAndPassword
	 * @return entity or null
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

	public static int numOfStudentsInCourse(String Cid) throws SQLException {
		PreparedStatement exams = con.prepareStatement("SELECT COUNT(ID) FROM Person_Enrolled_Course WHERE Cid=?");
		exams.setString(1, Cid);

		ResultSet rs = exams.executeQuery();
		rs.next();
		return rs.getInt(1);
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

	/**
	 * This method get all subject list from DB and return it to the principal.
	 * @param principalID the id of the principal.
	 * @return ArrayList<Subject> List of all subjects.
	 * @throws SQLException
	 */
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

	/**
	 * This method get all course list from DB and return it to the principal.
	 * @param principalID the id of the principal.
	 * @return ArrayList<Course> List of all courses.
	 * @throws SQLException
	 */
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
		ArrayList<String> report = new ArrayList<String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.cid AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {
			report.add(rs.getString(1));
		}
		return report;

	}

	public static Object getPrincipalReportStudents(String[] data) throws SQLException, ParseException {
		ArrayList<String> report = new ArrayList<String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=s.ID AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {
			report.add(rs.getString(1));

		}
		return report;

	}

	public static Object getPrincipalReportTeachers(String[] data) throws SQLException, ParseException {
		ArrayList<String> report = new ArrayList<String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement(
				"SELECT s.Grade FROM Exams e,SolvedExams s WHERE s.EId=e.Eid and ?=e.ID AND s.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {

			report.add(rs.getString(1));

		}
		return report;

	}

	/**
	 * @param examCode
	 * @return boolean
	 * @throws SQLException checks whether the exam code is correct
	 */
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

	/**
	 * @param examCode
	 * @return exam
	 * @throws SQLException Returns Exam entity with details of the exam
	 */
	public static Object getExamInformation(String examCode) throws SQLException {
		Exam exam = null;
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Exams WHERE Code=?");
		logInPreparedStatement.setString(1, examCode);
		rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			exam = new Exam(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
					rs.getString(11));
		}
		return exam;
	}

	public static ArrayList<SolvedExamType> getTeacherSolvedExamByCourse(Course course) throws SQLException {
		ArrayList<SolvedExamType> exams = new ArrayList<SolvedExamType>();

		ResultSet rs1;
		PreparedStatement logInPreparedStatement1;
		logInPreparedStatement1 = con
				.prepareStatement("SELECT seq.Eid, seq.DATE, e.Name, seq.checkedAmount, seq.sumAmount FROM Exams e, "
						+ "										(SELECT se.Eid, se.DATE, sum(case when Checked = 'Yes' then 1 else 0 end) as checkedAmount, "
						+ "										COUNT(*) as sumAmount FROM SolvedExams se GROUP BY se.Eid, se.DATE ) AS seq WHERE"
						+ "										 seq.Eid=e.Eid AND e.Cid=?");
		logInPreparedStatement1.setString(1, course.getId());
		rs1 = logInPreparedStatement1.executeQuery();
		while (rs1.next()) {
			exams.add(new SolvedExamType(rs1.getString(1), rs1.getDate(2).toString(), rs1.getString(3), rs1.getInt(4),
					rs1.getInt(5)));
		}

		return exams;
	}

	/**
	 * This method get from DB a list of questions by exam id.
	 * 
	 * @param course The course that holds the exam.
	 * @return ArrayList<Exam> list of exams by course.
	 * @throws SQLException
	 */
	public static ArrayList<Exam> getTeacherExamByCourse(Course course) throws SQLException {
		ArrayList<Exam> exams = new ArrayList<Exam>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM Exams WHERE Cid=?");
		logInPreparedStatement.setString(1, course.getId());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			exams.add((new Exam(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
					rs.getString(11))));

		}
		return exams;
	}

	public static ArrayList<Question> setTeacherExamQuesstions(Exam exam) throws SQLException {

		ArrayList<Question> questionList = new ArrayList<Question>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT q.Text, q.Ans1, q.Ans2, q.Ans3, q.Ans4, q.Qid, q.ID, q.DATE, q.CorrectAns "
						+ "FROM Questions q, Question_In_Exam qe " + "where q.Qid=qc.Qid AND qc.Eid=?");
		logInPreparedStatement.setString(1, exam.getEid());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			questionList.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
		}

		return questionList;
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

	/**
	 * This method get from DB a list of questions by course id.
	 * 
	 * @param course The course that holds the question.
	 * @return ArrayList<Question> list of questions by course.
	 * @throws SQLException
	 */
	public static Object getQuestionByCourse(Course course) throws SQLException {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT q.Text, q.Ans1, q.Ans2, q.Ans3, q.Ans4, q.Qid, q.ID, q.DATE, q.CorrectAns "
						+ "FROM Questions q, Question_In_Course qc " + "where q.Qid=qc.Qid AND qc.Cid=?");
		logInPreparedStatement.setString(1, course.getId());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			questionList.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getDate(8).toString(), rs.getInt(9)));
		}
		return questionList;
	}

	public static void deletedQuestion(Question q) throws SQLException {

		PreparedStatement delQ = con.prepareStatement("DELETE FROM Questions WHERE Qid=?");
		delQ.setString(1, q.getId());
		delQ.executeUpdate();

		PreparedStatement delQC;
		delQC = con.prepareStatement("DELETE FROM Question_In_Course WHERE Qid=?");
		delQC.setString(1, q.getId());
		delQC.executeUpdate();

	}

	public static void addQuestionByCourses(Question q, ArrayList<Course> course) throws SQLException, ParseException {

		ResultSet maxID;
		PreparedStatement getMaxID = con.prepareStatement("SELECT MAX(q.Qid) FROM Questions q where q.Sid=?");
		System.out.print(course.size());
		getMaxID.setString(1, course.get(0).getSubject().getId());
		maxID = getMaxID.executeQuery();
		maxID.next();
		String id;

		if (null != maxID.getNString(1)) {

			System.out.print("in worng plase !");

			id = ("" + (1 + Integer.parseInt(maxID.getNString(1))));
			if (id.length() == 4) {
				id = "0" + id;
			}
			q.setId(id);
		} else {
			System.out.print("in !");
			q.setId(course.get(0).getSubject().getId() + "001");
		}
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

		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(q.getModified());
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		addQuestion.setDate(9, dateInputData);
		addQuestion.setString(10, q.getSubject());
		addQuestion.executeUpdate();
		System.out.print("slnfa;lsj;gnalj");
		for (Course c : course) {
			PreparedStatement addToCourse = con
					.prepareStatement("INSERT INTO Question_In_Course (Qid,Cid) " + "VALUES (?, ?)");

			addToCourse.setString(1, q.getId());
			addToCourse.setString(2, c.getId());
			addToCourse.executeUpdate();
		}
	}

	public static String addExam(Exam e, ArrayList<QuestionInExam> list) throws SQLException, ParseException {

		ResultSet maxID;
		PreparedStatement getMaxID = con.prepareStatement("SELECT MAX(e.Eid) FROM Exams e where e.Sid=? AND e.Cid=?");
		getMaxID.setString(1, e.getSid());
		getMaxID.setString(2, e.getCid());
		maxID = getMaxID.executeQuery();
		String id;
		maxID.next();
		if (maxID.getNString(1) != null) {
			id = ("" + (1 + Integer.parseInt(maxID.getNString(1))));
			if (id.length() == 5) {
				id = "0" + id;
			}

		} else {

			id = e.getSid() + e.getCid() + "01";
		}
		e.setEid(id);
		System.out.print(e.getID());
		PreparedStatement addExam;
		addExam = con.prepareStatement(
				"INSERT INTO Exams (Eid, Sid, Cid, Name, Date, Tdescription, Sdescription, ID, TotalTime, Code, Mode)"
						+ "VALUES (?, ? ,? ,? ,? ,? ,? ,? ,? ,?, ? )");
		addExam.setString(1, e.getEid());
		addExam.setString(2, e.getSid());
		addExam.setString(3, e.getCid());
		addExam.setString(4, e.getName());
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(e.getDate());
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		addExam.setDate(5, dateInputData);
		addExam.setString(6, e.getTdescription());
		addExam.setString(7, e.getSdescription());

		addExam.setString(8, e.getID());
		addExam.setString(9, e.getTotalTime());
		addExam.setString(10, e.getCode());
		addExam.setString(11, "Computerized");
		addExam.executeUpdate();

		for (QuestionInExam q : list) {
			PreparedStatement addToCourse = con.prepareStatement(
					"INSERT INTO Question_In_Exams (Eid,Qid,Qpoint,QuestionNum) " + "VALUES (?, ?,?,?)");

			addToCourse.setString(1, e.getEid());
			addToCourse.setString(2, q.getId());
			addToCourse.setString(3, "" + q.getPointsQuestion());
			addToCourse.setString(4, "" + q.getNumOfQuestion());
			addToCourse.executeUpdate();
		}
		return id;
	}

	public static String addManualExam(Exam e, File ExamFile)
			throws SQLException, ParseException, FileNotFoundException {

		ResultSet maxID;
		PreparedStatement getMaxID = con.prepareStatement("SELECT MAX(e.Eid) FROM Exams e where e.Sid=? AND e.Cid=?");
		getMaxID.setString(1, e.getSid());
		getMaxID.setString(2, e.getCid());
		maxID = getMaxID.executeQuery();
		String id;

		if (maxID.next()) {
			id = ("" + (1 + Integer.parseInt(maxID.getNString(1))));
			if (id.length() == 5) {
				id = "0" + id;
			}

		} else
			id = e.getSid() + e.getCid() + "01";
		e.setEid(id);

		PreparedStatement addExam;
		addExam = con.prepareStatement(
				"INSERT INTO Exams (Eid, Sid, Cid, Name, Date, Tdescription, Sdescription, ID, TotalTime, Code, Mode)"
						+ "VALUES (?, ? ,? ,? ,? ,? ,? ,? ,? ,?, ? )");
		addExam.setString(1, e.getEid());
		addExam.setString(2, e.getSid());
		addExam.setString(3, e.getCid());
		addExam.setString(4, e.getName());
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(e.getDate());
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());
		addExam.setDate(5, dateInputData);
		addExam.setString(6, e.getTdescription());
		addExam.setString(7, e.getSdescription());

		addExam.setString(8, e.getID());
		addExam.setString(9, e.getTotalTime());
		addExam.setString(10, e.getCode());
		addExam.setString(11, "Manual");
		addExam.executeUpdate();

		PreparedStatement addMExam;
		addMExam = con.prepareStatement("INSERT INTO Teacher_Manual_Exams (Eid, ExamFile, ID)" + "VALUES (?, ? ,?)");
		addMExam.setString(1, e.getEid());

		InputStream inputstream = new FileInputStream(ExamFile);
		addMExam.setBlob(2, inputstream);
		addMExam.setString(3, e.getID());
		return id;
	}

	/**
	 * This method get request list from DB for the principal.
	 * 
	 * @return ArrayList<Request> list of requests.
	 * @throws SQLException
	 */
	public static Object getPrincipalRequests() throws SQLException {
		ArrayList<Request> requestList = new ArrayList<Request>();
		ResultSet rs;
		PreparedStatement statment;
		statment = con.prepareStatement(
				"SELECT r.Enum, r.title, r.currentDuration, r.newDuration, r.Tid  FROM Requests r WHERE r.Status = 'StandBy'");
		rs = statment.executeQuery();
		while (rs.next()) {
			requestList.add(
					new Request(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
		}

		return requestList;
	}

	/**
	 * This method update the status of the approved requests from StandBy to
	 * Approved.
	 * 
	 * @param RequestList The list of the requests that approved by principal.
	 * @return string "Succeed".
	 * @throws SQLException
	 */
	public static Object updatePrincipalApprovedRequests(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con.prepareStatement("UPDATE `Requests` SET `Status` = 'Approved' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeed";
	}

	/**
	 * This method update the status of the declined requests from StandBy to
	 * Declined.
	 * 
	 * @param RequestList The list of the requests that declined by principal.
	 * @return string "Succeed".
	 * @throws SQLException
	 */
	public static Object updatePrincipalDeclinedRequests(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con.prepareStatement("UPDATE `Requests` SET `Status` = 'Declined' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeed";
	}

	/**
	 * This method counting the new requests from the DB for the principal and
	 * return the counting number.
	 * 
	 * @return int Counter.
	 * @throws SQLException
	 */
	public static Object getRequestCount() throws SQLException {
		int Counter = 0;
		ResultSet rs;
		PreparedStatement statment;
		statment = con.prepareStatement(
				"SELECT COUNT(*) FROM Requests r WHERE r.Status = 'StandBy' AND r.Status2 = 'Waiting'");
		rs = statment.executeQuery();
		rs.next();
		Counter = rs.getInt(1);
		return Counter;
	}

	/**
	 * This method update the status2 of requests in DB from Waiting to Working.
	 * 
	 * @param RequestList - List of all requests.
	 * @return string "Succeed".
	 * @throws SQLException
	 */
	public static Object updatePrincipalRequestStatus(ArrayList<String> RequestList) throws SQLException {
		PreparedStatement statment;
		for (String request : RequestList) {
			statment = con.prepareStatement("UPDATE `Requests` SET `Status2` = 'Working' WHERE `Requests`.`Enum` = ?");
			statment.setString(1, request);
			statment.executeUpdate();
		}
		return "Succeed";
	}

	/**
	 * @param StudentId
	 * @return boolean
	 * @throws SQLException checks whether the ID of student is in DataBase
	 */
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

	/**
	 * @param Object[] details
	 * @return boolean
	 * @throws SQLException
	 * @throws FileNotFoundException Gets the exam file and uploads it to DataBase
	 */
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

	/**
	 * @param ExamID
	 * @return ManualExamFile
	 * @throws SQLException
	 * @throws IOException  Gets the manual exam uploaded by teacher given the exam
	 *                      ID
	 */
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

	public static boolean teacherValidExamCode(String ExamCode) throws SQLException {
		PreparedStatement exams = con.prepareStatement("SELECT Eid FROM Exams WHERE Code=?");
		exams.setString(1, ExamCode);
		ResultSet rs = exams.executeQuery();

		if (rs.next())
			return false;
		return true;

	}

	/**
	 * @param ExamID
	 * @return ArrayList<QuestionInExam>
	 * @throws SQLException returns all the question of exam given the ExamID
	 */
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

	/**
	 * @param data
	 * @return
	 * @throws SQLException Inserts all questions of solved exam by student to
	 *                      DataBase
	 */
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

	/**
	 * @param studentID
	 * @return ArrayList<Subject>
	 * @throws SQLException returns all subjects list of student given studentID
	 */
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

	/**
	 * @param studentID
	 * @return ArrayList<Course>
	 * @throws SQLException Returns all student courses given studentID
	 */
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

	/**
	 * This method get a list of question by exam id from the DB and return it to
	 * the client.
	 * 
	 * @param exam The exam that holds the questions.
	 * @return ArrayList<Question> list of questions by exam id.
	 * @throws SQLException
	 */
	public static Object getQuestionByExam(Exam exam) throws SQLException {
		ArrayList<Question> questionList = new ArrayList<Question>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT q.Text, q.Ans1, q.Ans2, q.Ans3, q.Ans4, q.Qid, q.ID, q.DATE, q.CorrectAns "
						+ "FROM Questions q, Question_In_Exams qie " + "where q.Qid=qie.Qid AND qie.Eid=?");
		logInPreparedStatement.setString(1, exam.getEid());
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			questionList.add(new Question(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
		}
		return questionList;
	}

	public static Object getTeacherExamList(String teacherID) throws SQLException {
		ArrayList<TeachersExam> examsList = new ArrayList<TeachersExam>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT s.Eid, s.Name,s.date FROM Exams s  WHERE s.ID = ?");
		logInPreparedStatement.setString(1, teacherID);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			examsList.add(new TeachersExam(rs.getString(1), rs.getString(2), rs.getString(3)));
		}
		return examsList;
	}

	public static Object getTeacherSolvedExamReport(String[] data) throws SQLException, ParseException {
		ArrayList<String> report = new ArrayList<String>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		Date dateInput = new SimpleDateFormat("yyyy-MM-dd").parse(data[1]);
		java.sql.Date dateInputData = new java.sql.Date(dateInput.getTime());

		logInPreparedStatement = con.prepareStatement("SELECT e.Grade FROM SolvedExams e WHERE e.Eid=? AND e.Date >=?");
		logInPreparedStatement.setString(1, data[0]);
		logInPreparedStatement.setDate(2, dateInputData);
		rs = logInPreparedStatement.executeQuery();

		while (rs.next()) {

			report.add(rs.getString(1));

		}
		return report;

	}

	/**
	 * @param SEid
	 * @return ArrayList<SolvedQuestionToView>
	 * @throws SQLException Returns all question of exam given SEid which is a
	 *                      certain submission
	 */
	public static Object getQuestionsForExamSEid(String SEid) throws SQLException {
		ArrayList<SolvedQuestionToView> questions = new ArrayList<SolvedQuestionToView>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT q.Qid,a.Ans,a.NotesForStudent,q.Text,q.Ans1,q.Ans2,q.Ans3,q.Ans4,q.CorrectAns,k.QuestionNum FROM AnsweredQuestionsForExams a,SolvedExams s,Questions q,Question_In_Exams k WHERE s.SEid=? AND a.Qid=k.Qid AND s.Eid=k.Eid AND s.SEid=a.SEid AND a.Qid=q.Qid");
		logInPreparedStatement.setString(1, SEid);
		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			questions.add(new SolvedQuestionToView(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10)));
		}
		rs.close();
		return questions;
	}

	/**
	 * @param student
	 * @return ArrayList<SolvedExamToView>
	 * @throws SQLException Gets all exams of student given Student entity.
	 */
	public static Object getStudentExamCourses(Student student) throws SQLException {
		ArrayList<SolvedExamToView> solvedExamsList = new ArrayList<SolvedExamToView>();
		ResultSet rs;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement(
				"SELECT s.SEid,s.Eid,s.DATE,e.Name,s.Grade FROM SolvedExams s,Exams e WHERE s.Eid=e.Eid AND s.ID=? AND s.Checked=?");
		logInPreparedStatement.setString(1, student.getId());
		logInPreparedStatement.setString(2, "Yes");

		rs = logInPreparedStatement.executeQuery();
		while (rs.next()) {
			solvedExamsList.add(new SolvedExamToView(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5)));
		}
		rs.close();
		return solvedExamsList;

	}

	/**
	 * @param exam
	 * @return String Eid
	 * @throws SQLException
	 * @throws ParseException inserts exam to DB and returns the SEid of that exam.
	 */
	public static String insertExamToDB(SolvedExam exam) throws SQLException, ParseException {
		ResultSet rs;
		int rslt;
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT MAX(SEid) FROM SolvedExams");
		rs = logInPreparedStatement.executeQuery();
		String Eid = "";
		if (rs.next()) {
			String eid = rs.getString(1);
			if (!(eid == null)) {
				System.out.println(eid);
				int i = Integer.parseInt(eid.trim());
				i++;
				Eid = String.format("%06d", i);
			} else {
				Eid = "000001";
			}

		} else {
			Eid = "000001";
		}
		rs.close();
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
		rs.close();
		return Eid;

	}

}
