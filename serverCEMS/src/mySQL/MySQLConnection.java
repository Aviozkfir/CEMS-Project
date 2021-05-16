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

import entity.Employee;
import entity.EntityConstants;
import entity.EntityConstants.OrderStatus;
import entity.EntityConstants.OrderType;
import entity.EntityConstants.ParkParameter;
import entity.EntityConstants.RequestStatus;
import entity.Order;
import entity.ParameterUpdate;
import entity.Park;
import entity.ParkDiscount;
import entity.PersonCEMS;
import entity.ReportDate;
import entity.Subscriber;
import entity.Visitor;
import entity.VisitorReport;
import message.ServerMessage;
import message.ServerMessageType;

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
		con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/S7BzDq6Xs6?serverTimezone=IST",
				"S7BzDq6Xs6", "puC0UgMgeM");
		System.out.println("SQL connection succeed");
	}

	/**
	 * A method that returns a Visitor with id. Insert to DataBase if there is not.
	 * 
	 * @param id
	 * @return Subscriber or null
	 * @throws SQLException
	 */
	public static PersonCEMS validatePerson(String id) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM visitor where id=? LIMIT 1;");
		logInPreparedStatement.setString(1, id);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Visitor(rs.getString(1));
		} else {
			PreparedStatement insertVisitorPreparedStatement;
			insertVisitorPreparedStatement = con.prepareStatement("INSERT INTO visitor (id) VALUES (?);");
			insertVisitorPreparedStatement.setString(1, id);
			insertVisitorPreparedStatement.executeUpdate();
			return new Visitor(id);
		}
	}

	/**
	 * A method that returns a Subscriber with subNum. Returns null if there is not.
	 * 
	 * @param subNum
	 * @return Subscriber or null
	 * @throws SQLException
	 */
	public static Subscriber validateSubscriber(String subNum) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con.prepareStatement("SELECT * FROM subscriber where subNum=? LIMIT 1;");
		logInPreparedStatement.setString(1, subNum);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), Integer.parseInt(rs.getString(7)), rs.getString(8),
					rs.getString(9).contentEquals("0") ? false : true);
		}
		return null;
	}

	/**
	 * A method that returns an Employee with employeeNum and password. Returns null
	 * if there is not.
	 * 
	 * @param employeeNumber and password
	 * @return Employee or null
	 * @throws SQLException
	 */
	public static Employee validateEmployee(String[] employeeNumberAndPassword) throws SQLException {
		PreparedStatement logInPreparedStatement;
		logInPreparedStatement = con
				.prepareStatement("SELECT * FROM employee where employeeNum=? AND password=? LIMIT 1;");
		logInPreparedStatement.setString(1, employeeNumberAndPassword[0]);
		logInPreparedStatement.setString(2, employeeNumberAndPassword[1]);
		ResultSet rs = logInPreparedStatement.executeQuery();
		if (rs.next()) {
			return new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					EntityConstants.EmployeeRole.valueOf(rs.getString(6)), rs.getString(8));
		}
		return null;
	}

	/**
	 * A method that returns a List of all parks in the DataBase
	 * 
	 * @param
	 * @return List of Park
	 * @throws NumberFormatException, SQLException
	 */
	public static List<Park> getParks() throws SQLException {
		ArrayList<Park> parkList = new ArrayList<>();
		Statement getParkStatement;
		getParkStatement = con.createStatement();
		ResultSet rs = getParkStatement.executeQuery("SELECT * FROM park;");
		while (rs.next()) {
			parkList.add(new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5))));
		}
		return parkList;
	}

	/**
	 * A method that returns a Park with it's name is parkName in DB. If there is
	 * not it returns null.
	 * 
	 * @param parkName
	 * @return Park or null
	 * @throws NumberFormatException, SQLException
	 */
	private static Park getCertainPark(String parkName) throws NumberFormatException, SQLException {

		PreparedStatement getParkStatement;
		getParkStatement = con.prepareStatement("SELECT * FROM park where parkName=?;");
		getParkStatement.setString(1, parkName);
		ResultSet rs = getParkStatement.executeQuery();
		if (rs.next()) {
			return new Park(rs.getString(1), Integer.parseInt(rs.getString(2)), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(5)));
		}
		return null;
	}

	/**
	 * A method that returns the data to the park manager's visitor report from
	 * database According to the data she receives (name of park, month and year)
	 * 
	 * @param reportDate
	 * @return
	 * @throws SQLException
	 */
	public static Map<Integer, VisitorReport> getVisitorReport(ReportDate reportDate) throws SQLException {

		Map<Integer, VisitorReport> reportVisitorMap = new LinkedHashMap<Integer, VisitorReport>();
		Calendar c = Calendar.getInstance();

		String query = "SELECT  orders.type ,orders.dateOfOrder ,sum(finishedOrders.actualNumOfVisitors ) "
				+ "FROM orders" + " JOIN finishedOrders ON (orders.orderNum = finishedOrders.orderNum_fk)"
				+ "WHERE MONTH(orders.dateOfOrder)=? AND YEAR(orders.dateOfOrder)=? AND orders.parkName_fk=?  "
				+ "GROUP by orders.type, orders.dateOfOrder;";
		PreparedStatement getVisitionReport = con.prepareStatement(query);
		getVisitionReport.setInt(1, Integer.parseInt(reportDate.getMonth()));
		getVisitionReport.setInt(2, Integer.parseInt(reportDate.getYear()));
		getVisitionReport.setString(3, reportDate.getNamePark());
		ResultSet rs = getVisitionReport.executeQuery();

		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= daysInMonth; i++)
			reportVisitorMap.put(i, new VisitorReport(reportDate.getNamePark()));

		if (!rs.next()) {
			return reportVisitorMap;
		}

		do {

			String temp = rs.getString(2);
			temp = temp.substring(8, 10);
			if (rs.getString(1).equals("GUIDE"))
				reportVisitorMap.get(Integer.parseInt(temp)).setCountGuid(Integer.parseInt((rs.getString(3))));
			if (rs.getString(1).equals("SUBSCRIBER"))
				reportVisitorMap.get(Integer.parseInt(temp)).setCountSubscriber(Integer.parseInt((rs.getString(3))));
			if (rs.getString(1).equals("REGULAR"))
				reportVisitorMap.get(Integer.parseInt(temp)).setCountRegular(Integer.parseInt((rs.getString(3))));

		} while (rs.next());
		return reportVisitorMap;

	}

	/**
	 * A method that returns the data to the department manager's visition report
	 * from database
	 */
	public static Map<Integer, VisitorReport> getVisitionReport(String namePark) throws SQLException {
		Map<Integer, VisitorReport> reportVisitorMap = new LinkedHashMap<Integer, VisitorReport>();

		PreparedStatement GetIncomeReport;
		GetIncomeReport = con.prepareStatement(
				"SELECT orders.type,sum(finishedOrders.visitDuration)/sum(finishedOrders.actualNumOfVisitors ),finishedOrders.actualTimeOfArrival "
						+ "FROM orders" + " JOIN finishedOrders ON (orders.orderNum = finishedOrders.orderNum_fk) "
						+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) and orders.parkName_fk=?"
						+ " GROUP by orders.type ,finishedOrders.actualTimeOfArrival");
		GetIncomeReport.setString(1, namePark);
		ResultSet rs = GetIncomeReport.executeQuery();
		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++) {
			reportVisitorMap.put(i, new VisitorReport(i));
		}

		if (!rs.next()) {
			return reportVisitorMap;
		}

		do {
			String temp = rs.getString(3);
			temp = temp.substring(0, 2);
			if (rs.getString(1).equals("GUIDE"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgGuid(Double.parseDouble(rs.getString(2)));
			if (rs.getString(1).equals("SUBSCRIBER"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgSubscriber(Double.parseDouble(rs.getString(2)));
			if (rs.getString(1).equals("REGULAR"))
				reportVisitorMap.get(Integer.parseInt(temp)).setAvgRegular(Double.parseDouble(rs.getString(2)));
		} while (rs.next());
		return reportVisitorMap;

	}

	/**
	 * A method that returns the data to the department manager's cancellation
	 * report from database
	 */

	public static Map<String, VisitorReport> getCancellationReport() throws SQLException {
		Map<String, VisitorReport> reportVisitorMap = new HashMap<String, VisitorReport>();
		Statement getCancellationReport;
		getCancellationReport = con.createStatement();
		ResultSet rs = getCancellationReport.executeQuery("SELECT orders.parkName_fk,COUNT(*) "
				+ "FROM orders WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) AND orders.status='EXPIRED' "
				+ "GROUP by orders.parkName_fk");
		List<Park> parkList = getParks();
		for (Park p : parkList)
			reportVisitorMap.put(p.getParkName(), new VisitorReport(p.getParkName()));
		if (rs.next()) {
			String namePark = rs.getString(1);
			do {
				reportVisitorMap.get(namePark).setCountNotRealized((Integer.parseInt(rs.getString(2))));

			} while (rs.next());

		}
		rs = getCancellationReport.executeQuery("SELECT orders.parkName_fk,COUNT(*) FROM orders "
				+ "WHERE (MONTH(NOW()) = MONTH(orders.dateOfOrder)) AND (YEAR(NOW()) = YEAR(orders.dateOfOrder)) "
				+ "AND orders.status='CANCELLED' GROUP by orders.parkName_fk");
		if (rs.next()) {
			String namePark = rs.getString(1);
			do {
				reportVisitorMap.get(namePark).setCountCancellations((Integer.parseInt(rs.getString(2))));

			} while (rs.next());

		}
		return reportVisitorMap;
	}

	/**
	 * A method that returns the data to the park manager's capacity report from
	 * database
	 * 
	 * @param parkName -The name of the park we want data on
	 * @return
	 * @throws SQLException
	 */
	public static Map<Integer, boolean[]> getParkCapacityReport(String parkName) throws SQLException {
		HashMap<Integer, boolean[]> map = new LinkedHashMap<Integer, boolean[]>();
		PreparedStatement getParkCapacityReport;
		Calendar c = Calendar.getInstance();
		getParkCapacityReport = con.prepareStatement(
				"SELECT parkFull.dateFull,parkFull.timeFull FROM parkFull WHERE (MONTH(NOW()) = MONTH(parkFull.dateFull)) "
						+ "AND (YEAR(NOW()) = YEAR(parkFull.dateFull)) AND parkFull.parkName_fk=?");
		getParkCapacityReport.setString(1, parkName);
		ResultSet rs = getParkCapacityReport.executeQuery();
		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = EntityConstants.PARK_OPEN; i <= EntityConstants.PARK_CLOSED; i++)
			map.put(i, new boolean[daysInMonth + 1]);
		if (!(rs.next()))
			return map;
		String time = rs.getString(2).substring(0, 2);
		boolean[] tempArr = map.get(Integer.parseInt(time));
		do {

			if (!time.equals(rs.getString(2).substring(0, 2))) {
				map.put(Integer.parseInt(time), tempArr);
				time = rs.getString(2).substring(0, 2);
				if ((Integer.parseInt(time) > EntityConstants.PARK_CLOSED)
						|| (Integer.parseInt(time) < EntityConstants.PARK_OPEN)) {
					continue;
				}
				tempArr = map.get(Integer.parseInt(time));
			}
			String date = rs.getString(1).substring(8, 10);
			tempArr[Integer.parseInt(date)] = true;

		} while (rs.next());
		return map;
	}

	/**
	 * A method that returns the data to the park manager's icome report from
	 * database According to the data she receives (name of park, month and year)
	 * 
	 * @param reportDate
	 * @return
	 * @throws SQLException
	 */
	public static Map<Integer, VisitorReport> getIncomeReport(ReportDate reportDate) throws SQLException {

		Map<Integer, VisitorReport> reportVisitorMap = new LinkedHashMap<Integer, VisitorReport>();
		Calendar c = Calendar.getInstance();
		String query = ("SELECT sum(finishedOrders.actualPrice ) ," + "orders.dateOfOrder " + "FROM finishedOrders "
				+ "JOIN orders ON (orders.orderNum = finishedOrders.orderNum_fk) "
				+ "WHERE MONTH(orders.dateOfOrder)=? AND YEAR(orders.dateOfOrder)=? AND orders.parkName_fk=? "
				+ "GROUP by orders.parkName_fk,orders.dateOfOrder;");
		PreparedStatement getIncomeReport = con.prepareStatement(query);
		getIncomeReport.setInt(1, Integer.parseInt(reportDate.getMonth()));
		getIncomeReport.setInt(2, Integer.parseInt(reportDate.getYear()));
		getIncomeReport.setString(3, reportDate.getNamePark());
		ResultSet rs = getIncomeReport.executeQuery();

		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= daysInMonth; i++)
			reportVisitorMap.put(i, new VisitorReport(reportDate.getNamePark()));

		if (!rs.next()) {
			return reportVisitorMap;
		}

		do {
			String temp = rs.getString(2);
			temp = temp.substring(8, 10);
			reportVisitorMap.get(Integer.parseInt(temp)).setPrice(Integer.parseInt((rs.getString(1))));

		} while (rs.next());
		return reportVisitorMap;

	}

	public static boolean validateDate(Order orderToValidate)
			throws NumberFormatException, SQLException, ParseException {
		Park park = getCertainPark(orderToValidate.getParkName());
		if (park == null)
			throw new SQLException();
		String[] splitTime = orderToValidate.getTimeOfOrder().split(":");
		Map<Integer, Integer> parkCapacity = parkCapacityForDuration(park, orderToValidate.getDateOfOrder(),
				orderToValidate.getTimeOfOrder(), orderToValidate.getTimeOfOrder());
		for (int hour : parkCapacity.keySet()) {
			if (hour < Integer.parseInt(splitTime[0]))
				continue;
			if (orderToValidate.getNumOfVisitors() + parkCapacity.get(hour) > park.getParkMaxVisitorsDefault()
					- park.getParkDiffFromMax())
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param park
	 * @param date
	 * @param startTime
	 * @param finishTime
	 * @return A Map of hour and capacity for hour in Park park
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private static Map<Integer, Integer> parkCapacityForDuration(Park park, String date, String startTime,
			String finishTime) throws NumberFormatException, SQLException {
		String[] splitTimeStart = startTime.split(":");
		String[] splitTimeFinish = finishTime.split(":");
		if (Integer.parseInt(splitTimeStart[0]) - park.getParkVisitDuration() < EntityConstants.PARK_OPEN)
			startTime = "08:00:00";
		else {
			startTime = String.valueOf(Integer.parseInt(splitTimeStart[0]) - park.getParkVisitDuration()) + ":00:00";
		}
		finishTime = String.valueOf(Integer.parseInt(splitTimeFinish[0]) + park.getParkVisitDuration() - 1) + ":00:00";
		Map<Integer, Integer> parkCapacity = new LinkedHashMap<Integer, Integer>();
		String query = "SELECT timeOfOrder,SUM(numOfVisitors) FROM orders WHERE (status='ACTIVE' OR status='PENDING_APPROVAL_FROM_WAITING_LIST'"
				+ " OR status='PENDING_FINAL_APPROVAL' OR status='APPROVED') AND orders.dateOfOrder=?"
				+ " AND timeOfOrder>=? AND timeOfOrder <=? AND parkName_fk=? GROUP BY timeOfOrder;";
		PreparedStatement validateDatePrepStmt = con.prepareStatement(query);
		validateDatePrepStmt.setString(1, date);
		validateDatePrepStmt.setString(2, startTime);
		validateDatePrepStmt.setString(3, finishTime);
		validateDatePrepStmt.setString(4, park.getParkName());
		ResultSet rs = validateDatePrepStmt.executeQuery();
		Map<Integer, Integer> timeOfOrderForHour = new HashMap<Integer, Integer>();
		while (rs.next()) {
			timeOfOrderForHour.put(Integer.parseInt(rs.getString(1).split(":")[0]), Integer.parseInt(rs.getString(2)));
		}
		query = "SELECT actualTimeOfArrival,SUM(actualNumOfVisitors) FROM finishedOrders WHERE orderNum_fk IN (SELECT orderNum FROM orders WHERE orders.dateOfOrder=? AND parkName_fk=?) AND actualTimeOfLeave IS NOT NULL GROUP BY actualTimeOfArrival";
		PreparedStatement validateDateInParkPrepStmt = con.prepareStatement(query);
		validateDateInParkPrepStmt.setString(1, date);
		validateDateInParkPrepStmt.setString(2, park.getParkName());
		rs = validateDateInParkPrepStmt.executeQuery();
		while (rs.next()) {
			if (timeOfOrderForHour.containsKey(Integer.parseInt(rs.getString(1).split(":")[0]))) {
				int newAmount = timeOfOrderForHour.get(Integer.parseInt(rs.getString(1).split(":")[0]))
						+ Integer.parseInt(rs.getString(2));
				timeOfOrderForHour.replace(Integer.parseInt(rs.getString(1).split(":")[0]), newAmount);
			} else

				timeOfOrderForHour.put(Integer.parseInt(rs.getString(1).split(":")[0]),
						Integer.parseInt(rs.getString(2)));
		}
		for (int i = Integer.parseInt(startTime.split(":")[0]); i <= Integer.parseInt(finishTime.split(":")[0]); i++) {
			int sum = 0;
			for (int j = park.getParkVisitDuration() - 1; j >= 0; j--) {
				if (timeOfOrderForHour.containsKey(i - j))
					sum += timeOfOrderForHour.get(i - j);
			}
			parkCapacity.put(i, sum);
		}

		return parkCapacity;
	}

	/**
	 * 
	 * @param orderToRequest
	 * @param isOccasional
	 * @param payInAdvance
	 * @return price of orderToRequest
	 * @throws SQLException
	 */
	private static double calculateOrder(Order orderToRequest, boolean isOccasional, Boolean payInAdvance)
			throws SQLException {

		double priceForTicket = EntityConstants.TICKET_PRICE;

		double priceForOrder;
		if (orderToRequest.getType().equals(EntityConstants.OrderType.GUIDE)) {
			if (!isOccasional) {
				priceForTicket *= 0.75;
				if (payInAdvance.booleanValue())
					priceForTicket *= 0.88;
				priceForOrder = (orderToRequest.getNumOfVisitors() - 1) * priceForTicket;
			} else {
				priceForTicket *= 0.9;
				priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			}

		} else {
			if (!isOccasional)
				priceForTicket *= 0.85;
			if (orderToRequest.getType().equals(EntityConstants.OrderType.REGULAR))
				priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			else {
				String query = "SELECT familyMembers FROM subscriber WHERE id_fk=?";
				PreparedStatement familyMembersStatement = con.prepareStatement(query);
				familyMembersStatement.setString(1, orderToRequest.getId());
				ResultSet rs = familyMembersStatement.executeQuery();
				if (rs.next()) {
					int familyMembers = Integer.parseInt(rs.getString(1));
					if (familyMembers >= orderToRequest.getNumOfVisitors()) {
						priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket * 0.8;

					} else {
						priceForOrder = (familyMembers) * priceForTicket * 0.8
								+ (orderToRequest.getNumOfVisitors() - familyMembers) * priceForTicket;
					}
				} else
					priceForOrder = (orderToRequest.getNumOfVisitors()) * priceForTicket;
			}

		}
		String query = "SELECT discountAmount FROM discounts WHERE startDate<=? AND finishDate>=? AND parkName_fk=? AND status='APPROVED'";
		PreparedStatement discountStatement = con.prepareStatement(query);
		discountStatement.setString(1, orderToRequest.getDateOfOrder());
		discountStatement.setString(2, orderToRequest.getDateOfOrder());
		discountStatement.setString(3, orderToRequest.getParkName());
		ResultSet rs = discountStatement.executeQuery();
		while (rs.next())
			priceForOrder = priceForOrder * ((100 - Integer.parseInt(rs.getString(1))) / 100.0);
		return priceForOrder;

	}

	/**
	 * 
	 * @param orderToInsert
	 * @param orderStatus
	 * @param isOccasional
	 * @param payInAdvance  This method insert orderToInsert with orderStatus and
	 *                      calculate its price.
	 * @return inserted order
	 * @throws SQLException
	 */
	private static Order insertNewOrder(Order orderToInsert, OrderStatus orderStatus, boolean isOccasional,
			Boolean payInAdvance) throws SQLException {
		PreparedStatement insertOrderStatement = con.prepareStatement(
				"INSERT INTO orders (id_fk,parkName_fk,orderCreationDate,numOfVisitors,status,type,dateOfOrder, timeOfOrder, price,email,phone) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNow = formatter.format(date);
		insertOrderStatement.setString(1, orderToInsert.getId());
		insertOrderStatement.setString(2, orderToInsert.getParkName());
		insertOrderStatement.setString(3, dateNow);
		insertOrderStatement.setString(4, String.valueOf(orderToInsert.getNumOfVisitors()));
		insertOrderStatement.setString(5, orderStatus.name());
		insertOrderStatement.setString(6, orderToInsert.getType().name());
		insertOrderStatement.setString(7, orderToInsert.getDateOfOrder());
		insertOrderStatement.setString(8, orderToInsert.getTimeOfOrder());
		double price = calculateOrder(orderToInsert, isOccasional, payInAdvance);
		insertOrderStatement.setString(9, String.valueOf((int) price));
		insertOrderStatement.setString(10, orderToInsert.getEmail());
		insertOrderStatement.setString(11, orderToInsert.getPhone());
		insertOrderStatement.executeUpdate();
		String query = "SELECT orderNum FROM orders where id_fk=? AND parkName_fk=? AND orderCreationDate=?;";
		PreparedStatement getOrderNumStatement = con.prepareStatement(query);
		getOrderNumStatement.setString(1, orderToInsert.getId());
		getOrderNumStatement.setString(2, orderToInsert.getParkName());
		getOrderNumStatement.setString(3, dateNow);
		ResultSet rs = getOrderNumStatement.executeQuery();
		if (rs.next()) {
			orderToInsert.setOrderNum(rs.getString(1));
			orderToInsert.setOrderCreationDate(dateNow);
			orderToInsert.setPrice((int) price);
			orderToInsert.setStatus(orderStatus);
			if (orderStatus.equals(OrderStatus.PENDING_FINAL_APPROVAL)) {
				sendSms(orderToInsert);
			}
			return orderToInsert;
		}
		throw new SQLException();
	}

	/**
	 * 
	 * @param orderRequest
	 * @param payInAdvance
	 * @return Insert orderRequest with a Status depends of it's date and time of
	 *         orderRequest. If the park is full for requested period it returns
	 *         null.
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Order createOrder(Order orderRequest, Boolean payInAdvance)
			throws SQLException, NumberFormatException, ParseException {
		if (validateDate(orderRequest)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTimeOfOrder = LocalDateTime
					.parse(orderRequest.getDateOfOrder() + " " + orderRequest.getTimeOfOrder(), formatter);
			long diff = ChronoUnit.MINUTES.between(now, dateTimeOfOrder);
			if (diff > 24 * 60)
				return insertNewOrder(orderRequest, OrderStatus.ACTIVE, false, payInAdvance);
			else
				return insertNewOrder(orderRequest, OrderStatus.PENDING_FINAL_APPROVAL, false, payInAdvance);
		}
		return null;
	}

	/**
	 * A method that inserts a ParameterUpdate in DB.
	 * 
	 * @param ParameterUpdate
	 * @return ParameterUpdate
	 * @throws SQLException
	 */
	public static ParameterUpdate createParameterUpdate(ParameterUpdate parameterUpdate) throws SQLException {
		PreparedStatement parameterPreparedStatement = con
				.prepareStatement("SELECT * FROM parameterUpdate WHERE parameter=? AND newValue=? AND parkName_fk=?;");
		parameterPreparedStatement.setString(1, parameterUpdate.getParameter());
		parameterPreparedStatement.setInt(2, parameterUpdate.getNewValue());
		parameterPreparedStatement.setString(3, parameterUpdate.getParkName());
		ResultSet rs = parameterPreparedStatement.executeQuery();
		if(rs.next())
			return null;
		else {
		PreparedStatement parameterPreparedStatement1 = con
				.prepareStatement("INSERT INTO parameterUpdate (parameter,newValue,parkName_fk) VALUES (?,?,?);");
		parameterPreparedStatement1.setString(1, parameterUpdate.getParameter());
		parameterPreparedStatement1.setInt(2, parameterUpdate.getNewValue());
		parameterPreparedStatement1.setString(3, parameterUpdate.getParkName());
		parameterPreparedStatement1.executeUpdate();
		return parameterUpdate;
		}
	}

	/**
	 * A method that returns a ParameterUpdate list from DB.
	 * 
	 * @param
	 * @return List<ParameterUpdate>
	 * @throws SQLException
	 */
	public static List<ParameterUpdate> getParameterRequests() throws SQLException {
		List<ParameterUpdate> parametersUpdateRequestList = new ArrayList<>();
		String query = "Select * From parameterUpdate ;";
		PreparedStatement parametersRequests = con.prepareStatement(query);
		ResultSet rs = parametersRequests.executeQuery();
		while (rs.next()) {
			ParameterUpdate tmpParameter = new ParameterUpdate(ParkParameter.valueOf(rs.getString(1)), rs.getInt(2),
					rs.getString(3));
			parametersUpdateRequestList.add(tmpParameter);
		}
		return parametersUpdateRequestList;
	}

	/**
	 * 
	 * @param orderRequest
	 * @param payInAdvanceWaitingList
	 * @return orderRequest with Waiting status.
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Order enterWaitingist(Order orderRequest, Boolean payInAdvanceWaitingList)
			throws SQLException, NumberFormatException, ParseException {
		return insertNewOrder(orderRequest, OrderStatus.WAITING, false, payInAdvanceWaitingList);
	}

	/**
	 * 
	 * @param newDiscountRequest
	 * @return server message about existing discount if the discount is already
	 *         exist and server message that discount can be insert if not.
	 * @throws SQLException
	 */
	public static ServerMessage discountValidation(ParkDiscount newDiscountRequest) throws SQLException {
		String query = "SELECT * FROM discounts WHERE parkName_fk=? AND startDate=? AND finishDate=? AND discountAmount=?;";
		PreparedStatement checkDiscountRequestStatement = con.prepareStatement(query);
		checkDiscountRequestStatement.setString(1, newDiscountRequest.getParkName());
		checkDiscountRequestStatement.setString(2, newDiscountRequest.getStartDate());
		checkDiscountRequestStatement.setString(3, newDiscountRequest.getFinishDate());
		checkDiscountRequestStatement.setInt(4, newDiscountRequest.getDiscountAmount());
		ResultSet rs = checkDiscountRequestStatement.executeQuery();
		if (rs.next()) {
			ParkDiscount existDiscount = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getInt(4), EntityConstants.RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			return new ServerMessage(ServerMessageType.DISCOUNT_IS_ALREADY_EXIST, existDiscount);

		}
		String query2 = "SELECT * FROM discounts WHERE parkName_fk=? AND startDate<=? AND finishDate>=? AND status=?;";
		PreparedStatement checkDiscountRequestStatement2 = con.prepareStatement(query2);
		checkDiscountRequestStatement2.setString(1, newDiscountRequest.getParkName());
		checkDiscountRequestStatement2.setString(2, newDiscountRequest.getStartDate());
		checkDiscountRequestStatement2.setString(3, newDiscountRequest.getFinishDate());
		checkDiscountRequestStatement2.setString(4, EntityConstants.RequestStatus.APPROVED.name());
		rs=checkDiscountRequestStatement2.executeQuery();
		if (rs.next()) 
		{
			ParkDiscount discountBetweenDates = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getInt(4), EntityConstants.RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			return new ServerMessage(ServerMessageType.DISCOUNT_EXIST_BETWEEN_DATES, discountBetweenDates);
		}
		String query3 = "SELECT * FROM discounts WHERE parkName_fk=? AND startDate<=? AND finishDate>=? AND status=?;";
		PreparedStatement checkDiscountRequestStatement3 = con.prepareStatement(query2);
		checkDiscountRequestStatement3.setString(1, newDiscountRequest.getParkName());
		checkDiscountRequestStatement3.setString(2, newDiscountRequest.getStartDate());
		checkDiscountRequestStatement3.setString(3, newDiscountRequest.getFinishDate());
		checkDiscountRequestStatement3.setString(4, EntityConstants.RequestStatus.WAITING.name());
		rs=checkDiscountRequestStatement3.executeQuery();
		if (rs.next()) 
		{
			ParkDiscount waitingDiscount = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3),
					rs.getInt(4), EntityConstants.RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			return new ServerMessage(ServerMessageType.WAITING_DISCOUNT, waitingDiscount);
		}
		return new ServerMessage(ServerMessageType.CAN_INSERT_NEW_DISCOUNT, null);
	}

	/**
	 * 
	 * @param newDiscountRequest
	 * @return discount request
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static ParkDiscount insertNewDiscountRequest(ParkDiscount newDiscountRequest)
			throws SQLException, ParseException {
		PreparedStatement insertDiscountRequestStatement = con.prepareStatement(
				"INSERT INTO discounts (parkName_fk,startDate,finishDate,discountAmount,status,employeeId) VALUES (?,?,?,?,?,?);");
		insertDiscountRequestStatement.setString(1, newDiscountRequest.getParkName());
		insertDiscountRequestStatement.setString(2, newDiscountRequest.getStartDate());
		insertDiscountRequestStatement.setString(3, newDiscountRequest.getFinishDate());
		insertDiscountRequestStatement.setInt(4, newDiscountRequest.getDiscountAmount());
		insertDiscountRequestStatement.setString(5, newDiscountRequest.getDiscountStatus().name());
		insertDiscountRequestStatement.setString(6, newDiscountRequest.getEmployeeNumber());
		insertDiscountRequestStatement.executeUpdate();

		return newDiscountRequest;
	}

	/**
	 * 
	 * @param order
	 * @return A Map with date and List of hour for the next 7 days of order that
	 *         the requested park can fit numOfvisitors in order
	 * @throws ParseException
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public static Map<String, List<String>> getAvailableDates(Order order)
			throws ParseException, NumberFormatException, SQLException {
		Park park = getCertainPark(order.getParkName());
		Map<String, List<String>> dateMap = new LinkedHashMap<>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(order.getDateOfOrder());
		String startHour = EntityConstants.PARK_OPEN + ":00:00";
		if (EntityConstants.PARK_OPEN < 10)
			startHour = "0" + startHour;
		String finishHour = EntityConstants.PARK_CLOSED + ":00:00";
		if (EntityConstants.PARK_CLOSED < 10)
			finishHour = "0" + finishHour;
		for (int i = 0; i < 7; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, i); // minus number would decrement the days
			Date nextDate = cal.getTime();
			dateMap.put(format.format(nextDate), new ArrayList<String>());
			Map<Integer, Integer> parkCapacity;
			Date today = new Date();
			LocalTime localTime = today.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
			boolean isToday = false;
			if (!format.format(nextDate).equals(format.format(today)))
				parkCapacity = parkCapacityForDuration(park, format.format(nextDate), startHour, finishHour);
			else {
				isToday = true;
				String startHourForToday = (localTime.getHour() + 1) + ":00+:00";
				if (localTime.getHour() + 1 < 10)
					startHourForToday = "0" + startHourForToday;
				parkCapacity = parkCapacityForDuration(park, format.format(nextDate), startHourForToday, finishHour);
			}
			for (int hour : parkCapacity.keySet()) {
				if (isToday) {
					if (hour < localTime.getHour() + 1)
						continue;
				}
				if (hour > EntityConstants.PARK_CLOSED)
					continue;
				int maxCapacity = parkCapacity.get(hour);
				for (int j = 1; j < park.getParkVisitDuration(); j++) {
					if (parkCapacity.get(hour + j) != null) {
						if (maxCapacity < parkCapacity.get(hour + j))
							maxCapacity = parkCapacity.get(hour + j);
					}
				}
				if (order.getNumOfVisitors() + maxCapacity <= park.getParkMaxVisitorsDefault()
						- park.getParkDiffFromMax()) {
					String hourFree = hour + ":00:00";
					if (hour < 10)
						hourFree = "0" + hourFree;
					dateMap.get(format.format(nextDate)).add(hourFree);
				}
			}
		}
		return dateMap;
	}

	/**
	 * 
	 * @param order
	 * @return An Occasional order. If the park can not fit numOfVisitors it returns
	 *         null
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Object OccasionalcreateOrder(Order order) throws SQLException, NumberFormatException, ParseException {

		if (order.getType().equals(OrderType.REGULAR)) {
			Visitor visitor = validateVisitor(order.getId());
		}
		if (order.getType().equals(OrderType.SUBSCRIBER)) {
			Subscriber subscriber = validateSubscriber(order.getId());
			if (subscriber == null)
				return "Subscriber Number" + order.getId() + " " + "is not in the system";
			order.setId(subscriber.getID());
		}
		if (order.getType().equals(OrderType.GUIDE)) {
			Subscriber subscriber = validateSubscriber(order.getId());
			if (subscriber == null)
				return "Subscriber Number" + order.getId() + " " + "is not in the system";
			if (!subscriber.getIsGuide())
				return "Subscriber Number" + order.getId() + " " + "is not a guide";
			order.setId(subscriber.getID());
		}
		Park park = (Park) getCertainPark(order.getParkName());
		if (park == null)
			return null;
		if (park.getParkCurrentVisitors() + order.getNumOfVisitors() < park.getParkMaxVisitorsDefault()) {
			return insertNewOrder(order, OrderStatus.APPROVED, true, new Boolean(false));
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return List of order of id of status WAITING or Pending approvals. (These
	 *         are special because there are orders that the user can cancel or
	 *         approve etc.)
	 * @throws SQLException
	 */
	public static List<Order> getUnfinishedOrdersById(String id) throws SQLException {
		List<Order> orders = new ArrayList<Order>();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String nowString = now.format(formatter);
		String query = "Select * From orders where id_fk=? AND (status='WAITING' OR status='APPROVED' OR status='PENDING_APPROVAL_FROM_WAITING_LIST' OR status='ACTIVE' OR status='PENDING_FINAL_APPROVAL') AND dateOfOrder>=?;";
		PreparedStatement getOrdersForId = con.prepareStatement(query);
		getOrdersForId.setString(1, id);
		getOrdersForId.setString(2, nowString.split(" ")[0]);
		ResultSet rs = getOrdersForId.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		return orders;
	}

	/**
	 * 
	 * @param orderNum
	 * @param newStatus
	 * @return change an order status with orderNum to newStatus and return true if
	 *         it is. If there is not an order with orderNum or it is already been
	 *         canceled it returns false.
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Boolean changeOrderStatus(String orderNum, OrderStatus newStatus)
			throws SQLException, NumberFormatException, ParseException {
		Order order = getCertainOrder(orderNum);
		if (order == null || order.getStatus().equals(OrderStatus.CANCELLED)
				|| order.getStatus().equals(OrderStatus.EXPIRED)) {
			return false;
		}
		String query = "Update orders SET status=? WHERE orderNum=?";
		String queryDelSms = "DELETE FROM smsSend WHERE orderNum_fk=?";
		PreparedStatement delSms = con.prepareStatement(queryDelSms);
		PreparedStatement approveOrder = con.prepareStatement(query);
		delSms.setString(1, orderNum);
		approveOrder.setString(1, newStatus.name());
		approveOrder.setString(2, orderNum);
		approveOrder.executeUpdate();
		delSms.executeUpdate();
		return true;
	}

	/**
	 * 
	 * @param orderNum
	 * @return an Order with orderNum in DataBase. If there is not it returns null.
	 * @throws SQLException
	 */
	public static Order getCertainOrder(String orderNum) throws SQLException {
		String query = "Select * From orders where orderNum=?";
		PreparedStatement getPark = con.prepareStatement(query);
		getPark.setString(1, orderNum);
		ResultSet rs = getPark.executeQuery();
		if (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			return tmpOrder;
		}
		return null;
	}

	/**
	 * 
	 * @param order
	 * @return order with APPROVED or PENDING_FINAL_APPROVAL depending of order's
	 *         date and time. If the order was canceled it returns null.
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static Order activateOrderFromWatingList(Order order)
			throws SQLException, NumberFormatException, ParseException {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTimeOfOrder = LocalDateTime.parse(order.getDateOfOrder() + " " + order.getTimeOfOrder(),
				formatter);
		OrderStatus newStatus;
		boolean res;
		long diff = ChronoUnit.MINUTES.between(now, dateTimeOfOrder);
		if (diff < 24 * 60) {
			if (diff < 2 * 60) {
				res = changeOrderStatus(order.getOrderNum(), OrderStatus.APPROVED);
				newStatus = OrderStatus.APPROVED;
			} else {
				res = changeOrderStatus(order.getOrderNum(), OrderStatus.PENDING_FINAL_APPROVAL);
				newStatus = OrderStatus.PENDING_FINAL_APPROVAL;
				String nowString = now.format(formatter);
				String querySendSms = "INSERT INTO smsSend (orderNum_fk,smsRecviedDate,smsRecviedTime) VALUES (?,?,?)";
				PreparedStatement sendSmStatement = con.prepareStatement(querySendSms);
				sendSmStatement.setString(1, order.getOrderNum());
				sendSmStatement.setString(2, nowString.split(" ")[0]);
				sendSmStatement.setString(3, nowString.split(" ")[1]);
				sendSmStatement.executeUpdate();
			}
		} else {
			res = changeOrderStatus(order.getOrderNum(), OrderStatus.ACTIVE);
			newStatus = OrderStatus.ACTIVE;
		}
		if (!res)
			return null;
		else {
			order.setStatus(newStatus);
			return order;
		}
	}

	/**
	 * 
	 * @param employeeId
	 * @return list of discount requests for specific park and employee
	 * @throws SQLException
	 */
	public static List<ParkDiscount> getDiscountRequests(String employeeId) throws SQLException {
		List<ParkDiscount> parkDiscountRequestList = new ArrayList<>();
		LocalDate today = LocalDate.now();
		String query = "Select * From discounts where finishDate>=? And employeeId=? ;";
		PreparedStatement discountsRequestsForId = con.prepareStatement(query);
		discountsRequestsForId.setString(1, today.toString());
		discountsRequestsForId.setString(2, employeeId);
		ResultSet rs = discountsRequestsForId.executeQuery();
		while (rs.next()) {
			ParkDiscount tmp = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			parkDiscountRequestList.add(tmp);
		}
		return parkDiscountRequestList;
	}

	/**
	 * 
	 * @return list of discount requests for all the parks
	 * @throws SQLException
	 */
	public static List<ParkDiscount> getDepManagerDiscountRequests() throws SQLException {
		List<ParkDiscount> parkDiscountRequestList = new ArrayList<>();
		String query = "Select * from discounts Where status=?;";
		PreparedStatement discountRequests = con.prepareStatement(query);
		discountRequests.setString(1, EntityConstants.RequestStatus.WAITING.toString());
		ResultSet rs = discountRequests.executeQuery();
		while (rs.next()) {
			ParkDiscount tmp = new ParkDiscount(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4),
					RequestStatus.valueOf(rs.getString(5)), rs.getString(6));
			parkDiscountRequestList.add(tmp);
		}
		return parkDiscountRequestList;
	}

	/**
	 * 
	 * @param idVisitorsAndParkName
	 * @return The final price of order of Visitor with id for park now.
	 * 
	 * @throws SQLException
	 */
	public static Integer validateOrderAndReturnPrice(String[] idVisitorsAndParkName) throws SQLException {
		Order orderToValidate = getCurrentOrderByIDAndParkName(idVisitorsAndParkName[0], idVisitorsAndParkName[2]);
		if (orderToValidate == null)
			return null;
		if (orderToValidate.getNumOfVisitors() < Integer.parseInt(idVisitorsAndParkName[1]))
			return new Integer(-1);
		if (!updateOrderToDone(orderToValidate))
			throw new SQLException("FAILED TO UPDATE ORDER TO DONE");
		int actualPrice = (orderToValidate.getPrice() / orderToValidate.getNumOfVisitors())
				* Integer.parseInt(idVisitorsAndParkName[1]);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
		String timeNow = formatter.format(new Date());
		if (!insertFinishedOrder(orderToValidate.getOrderNum(), idVisitorsAndParkName[1], timeNow,
				String.valueOf(actualPrice)))
			throw new SQLException("FAILED TO INSERT FINISHED ORDER");
		if (!addToCurrentParkVisitors(idVisitorsAndParkName[2], Integer.parseInt(idVisitorsAndParkName[1])))
			throw new SQLException("FAILED TO UPDATE CURRENT PARK VISITORS");
		return new Integer(actualPrice);

	}

	/**
	 * 
	 * @param id
	 * @param parkName
	 * @return The current order of Visitor or Subscriber or Guide with id. If there
	 *         is not returns null.
	 * @throws SQLException
	 */
	private static Order getCurrentOrderByIDAndParkName(String id, String parkName) throws SQLException {
		Order order = null;
		String date = LocalDate.now().toString();
		String earliestTime = LocalTime.now().minusMinutes(30).toString();
		String latestTime = LocalTime.now().plusMinutes(30).toString();
		String query = "Select * From orders where id_fk=? AND status='APPROVED' AND dateOfOrder=? AND timeOfOrder>=? AND timeOfOrder<=? AND parkName_fk=? ;";
		PreparedStatement getOrder = con.prepareStatement(query);
		getOrder.setString(1, id);
		getOrder.setString(2, date);
		getOrder.setString(3, earliestTime);
		getOrder.setString(4, latestTime);
		getOrder.setString(5, parkName);
		ResultSet rs = getOrder.executeQuery();
		if (rs.next())
			order = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
		return order;
	}

	/**
	 * 
	 * @param order
	 * @return change order status to Done when the visitors leave.
	 */
	private static boolean updateOrderToDone(Order order) {
		try {
			String query = "Update orders SET status='DONE' WHERE orderNum=?";
			PreparedStatement approveOrder = con.prepareStatement(query);
			approveOrder.setString(1, order.getOrderNum());
			approveOrder.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param orderNum
	 * @param numOfVisitors
	 * @param timeOfArrival
	 * @param price
	 * @return True if the details of actual order with orderNum has been inserted
	 *         to DataBase. If there was a problem it returns false
	 */
	private static boolean insertFinishedOrder(String orderNum, String numOfVisitors, String timeOfArrival,
			String price) {
		try {
			String query = "INSERT INTO finishedOrders (orderNum_fk,actualNumOfVisitors,actualTimeOfArrival,actualPrice) VALUES (?,?,?,?);";
			PreparedStatement insertFinishedOrder = con.prepareStatement(query);
			insertFinishedOrder.setString(1, orderNum);
			insertFinishedOrder.setString(2, numOfVisitors);
			insertFinishedOrder.setString(3, timeOfArrival);
			insertFinishedOrder.setString(4, price);
			insertFinishedOrder.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param parkName
	 * @param amount
	 * @return true if the currentVisitors in parkName has been increased with
	 *         amount of visitors. If there was SQLException return false
	 */
	private static boolean addToCurrentParkVisitors(String parkName, int amount) {
		try {
			String query = "Update park SET currentVisitors=currentVisitors+? WHERE parkName=?";
			PreparedStatement editCurrentVisitors = con.prepareStatement(query);
			editCurrentVisitors.setInt(1, amount);
			editCurrentVisitors.setString(2, parkName);
			editCurrentVisitors.executeUpdate();
			if (updateParkFull(parkName))
				return true;
			else
				return false;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param parkName
	 * @return Check if park with parkName is full now. if it is insert park to
	 *         parkFull Table. returns true if there is no SQLException and false
	 *         otherwise.
	 */
	public static boolean updateParkFull(String parkName) {
		LocalTime opening = LocalTime.of(EntityConstants.PARK_OPEN, 0);
		LocalTime closing = LocalTime.of(EntityConstants.PARK_CLOSED + 4, 0);
		if (LocalTime.now().isBefore(opening) || LocalTime.now().isAfter(closing))
			return true;
		try {
			PreparedStatement checkParkFull = con
					.prepareStatement("SELECT * from park where parkName=? and maxVisitors<=currentVisitors;");
			checkParkFull.setString(1, parkName);
			ResultSet rs = checkParkFull.executeQuery();
			if (rs.next()) {
				String updateParkFullQuery = "INSERT IGNORE INTO parkFull (parkName_fk,dateFull,timeFull) VALUES (?,?,?)";
				PreparedStatement updateParkFull = con.prepareStatement(updateParkFullQuery);
				updateParkFull.setString(1, parkName);
				updateParkFull.setString(2, LocalDate.now().toString());
				Date dateNow = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
				String timeNow = formatter.format(new Date());
				updateParkFull.setString(3, timeNow);
				updateParkFull.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * 
	 * @param idAndParkName
	 * @return Update leave time for orders of Visitor with id in parkName. return
	 *         true if update leave time. If there was an Exception or there is
	 *         Visitor with id in ParkName park return false.
	 */
	public static Boolean validateOrderAndRegisterExit(String[] idAndParkName) {
		try {
			String orderNum, timeOfArrival;
			int amountOfVisitors;
			String date = LocalDate.now().toString();
			String query = "SELECT finishedOrders.orderNum_fk,finishedOrders.actualTimeOfArrival,finishedOrders.actualNumOfVisitors"
					+ " from finishedOrders join orders on (orders.orderNum=finishedOrders.orderNum_fk) where orders.id_fk=? and orders.parkName_fk=? and orders.dateOfOrder=? and (finishedOrders.actualTimeOfLeave is null);";
			PreparedStatement getOrder = con.prepareStatement(query);
			getOrder.setString(1, idAndParkName[0]);
			getOrder.setString(2, idAndParkName[1]);
			getOrder.setString(3, date);
			ResultSet rs = getOrder.executeQuery();
			if (rs.next()) {
				orderNum = rs.getString(1);
				timeOfArrival = rs.getString(2);
				amountOfVisitors = -rs.getInt(3);
			} else
				return null;
			query = "UPDATE finishedOrders set actualTimeOfLeave=? , visitDuration=? where orderNum_fk=?;";
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String timeNow = format.format(new Date());
			Date date1 = format.parse(timeOfArrival);
			Date date2 = format.parse(timeNow);
			long difference = (date2.getTime() - date1.getTime()) / 60000; // in minutes
			if (difference % 60 > 30)
				difference = 1 + difference / 60;
			else
				difference = difference / 60;
			String visitDuration = String.valueOf(difference);
			PreparedStatement updateFinishedOrder = con.prepareStatement(query);
			updateFinishedOrder.setString(1, timeNow);
			updateFinishedOrder.setString(2, visitDuration);
			updateFinishedOrder.setString(3, orderNum);
			updateFinishedOrder.executeUpdate();
			if (!addToCurrentParkVisitors(idAndParkName[1], amountOfVisitors))
				throw new SQLException("FAILED TO UPDATE CURRENT PARK VISITORS");
			return new Boolean(true);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * update approved park parameters and delete from requests
	 * 
	 * @param parameterToApprove
	 * @return true
	 * @throws SQLException
	 */
	public static boolean approveParameterUpdate(ParameterUpdate parameterToApprove) throws SQLException {
		String query1 = "DELETE FROM parameterUpdate WHERE parameter=? AND newValue=? AND parkName_fk=?;";
		String query2 = "UPDATE park SET maxVisitors=? WHERE parkName=?;";
		String query3 = "UPDATE park SET diffFromMax=? WHERE parkName=?;";
		String query4 = "UPDATE park SET visitDur=? WHERE parkName=?;";

		PreparedStatement deleteParameter = con.prepareStatement(query1);

		deleteParameter.setString(1, parameterToApprove.getParameter());
		deleteParameter.setInt(2, parameterToApprove.getNewValue());
		deleteParameter.setString(3, parameterToApprove.getParkName());
		PreparedStatement updateParameter;
		if (parameterToApprove.getParameter().equals("CAPACITY"))
			updateParameter = con.prepareStatement(query2);
		else {
			if (parameterToApprove.getParameter().equals("DIFFERENCE"))
				updateParameter = con.prepareStatement(query3);

			else
				updateParameter = con.prepareStatement(query4);
		}
		updateParameter.setInt(1, parameterToApprove.getNewValue());
		updateParameter.setString(2, parameterToApprove.getParkName());
		updateParameter.executeUpdate();
		deleteParameter.executeUpdate();
		return true;
	}

	/**
	 * delete declined parameter requests
	 * 
	 * @param parameterToDecline
	 * @return true
	 * @throws SQLException
	 */
	public static boolean declineParameterUpdate(ParameterUpdate parameterToDecline) throws SQLException {
		String query1 = "DELETE FROM parameterUpdate WHERE parameter=? AND newValue=? AND parkName_fk=?;";
		PreparedStatement deleteParameter = con.prepareStatement(query1);

		deleteParameter.setString(1, parameterToDecline.getParameter());
		deleteParameter.setInt(2, parameterToDecline.getNewValue());
		deleteParameter.setString(3, parameterToDecline.getParkName());
		deleteParameter.executeUpdate();

		return true;
	}

	/**
	 * update discount status to approved
	 * 
	 * @param discountToApprove
	 * @return true
	 * @throws SQLException
	 */
	public static boolean approveDiscountUpdate(ParkDiscount discountToApprove) throws SQLException {
		String query1 = "UPDATE discounts SET status=? WHERE parkName_fk=? AND startDate=? AND finishDate=? AND discountAmount=? ;";
		PreparedStatement approveDiscount = con.prepareStatement(query1);
		String[] changeStartDateForDB=discountToApprove.getStartDate().split("-");
		String[] changeFinishDateForDB=discountToApprove.getFinishDate().split("-");
		approveDiscount.setString(1, entity.EntityConstants.RequestStatus.APPROVED.name());
		approveDiscount.setString(2, discountToApprove.getParkName());
		approveDiscount.setString(3, changeStartDateForDB[2]+'-'+changeStartDateForDB[1]+'-'+changeStartDateForDB[0]);
		approveDiscount.setString(4, changeFinishDateForDB[2]+'-'+changeFinishDateForDB[1]+'-'+changeFinishDateForDB[0]);
		approveDiscount.setInt(5, discountToApprove.getDiscountAmount());
		approveDiscount.executeUpdate();

		return true;
	}

	/**
	 * 
	 * A method that returns ServerMessage if the registration was succeeded with a
	 * new subscriber, or failure with the existing subscriber details.
	 */
	public static ServerMessage registerSubscriber(Subscriber subscriber) throws SQLException {
		PreparedStatement registerPreparedStatement;
		registerPreparedStatement = con.prepareStatement("SELECT * FROM subscriber WHERE id_fk=? ");
		registerPreparedStatement.setString(1, subscriber.getID());
		ResultSet rs = registerPreparedStatement.executeQuery();
		if (rs.next()) {
			Subscriber registered = new Subscriber(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), Integer.parseInt(rs.getString(7)), rs.getString(8),
					rs.getString(9).contentEquals("0") ? false : true);
			return new ServerMessage(ServerMessageType.REGISTRATION_FAILED, registered);
		}
		validateVisitor(subscriber.getID());
		registerPreparedStatement = con.prepareStatement("INSERT INTO subscriber "
				+ "(id_fk,firstName,lastName, phone, email, familyMembers, cardDetails,isGuide) VALUES "
				+ "(?,?,?,?,?,?,?,?);");
		registerPreparedStatement.setString(1, subscriber.getID());
		registerPreparedStatement.setString(2, subscriber.getFirstName());
		registerPreparedStatement.setString(3, subscriber.getLastName());
		registerPreparedStatement.setString(4, subscriber.getPhone());
		registerPreparedStatement.setString(5, subscriber.getEmail());
		registerPreparedStatement.setInt(6, subscriber.getSubscriberFamilyMembers());
		registerPreparedStatement.setString(7, subscriber.getSubscriberCardDetails());
		registerPreparedStatement.setBoolean(8, subscriber.getIsGuide());
		registerPreparedStatement.executeUpdate();

		registerPreparedStatement = con.prepareStatement("SELECT * FROM subscriber WHERE id_fk=? ");
		registerPreparedStatement.setString(1, subscriber.getID());
		ResultSet rs1 = registerPreparedStatement.executeQuery();
		if (rs1.next()) {
			subscriber.setSuibscriberNum(rs1.getString(1));
		}

		return new ServerMessage(ServerMessageType.REGISTRATION_SUCCESSED, subscriber);

	}

	/**
	 * update discount status to decline
	 * 
	 * @param discountToDecline
	 * @return true
	 * @throws SQLException
	 */
	public static boolean declineDiscountUpdate(ParkDiscount discountToDecline) throws SQLException {
		String query1 = "UPDATE discounts SET status=? WHERE parkName_fk=? AND startDate=? AND finishDate=? AND discountAmount=?;";
		PreparedStatement declineDiscount = con.prepareStatement(query1);
		String[] changeStartDateForDB=discountToDecline.getStartDate().split("-");
		String[] changeFinishDateForDB=discountToDecline.getFinishDate().split("-");
		declineDiscount.setString(1, entity.EntityConstants.RequestStatus.DECLINED.name());
		declineDiscount.setString(2, discountToDecline.getParkName());
		declineDiscount.setString(3, changeStartDateForDB[2]+'-'+changeStartDateForDB[1]+'-'+changeStartDateForDB[0]);
		declineDiscount.setString(4, changeFinishDateForDB[2]+'-'+changeFinishDateForDB[1]+'-'+changeFinishDateForDB[0]);
		declineDiscount.setInt(5, discountToDecline.getDiscountAmount());
		declineDiscount.executeUpdate();

		return true;
	}

	/**
	 * 
	 * @return List of order that their status was changed to PENDING_FINAL_APPROVAL
	 *         in order to send them sms.
	 * @throws SQLException
	 */
	public static List<Order> sendSmsToActiveOrders() throws SQLException {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tommorow = now.plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String nowString = now.format(formatter);
		String dateNow = nowString.split(" ")[0];
		String timeNow = nowString.split(" ")[1];
		String tommorowString = now.format(formatter);
		List<Order> orders = new ArrayList<>();
		String query = "SELECT orders.* FROM orders WHERE dateOfOrder=? AND HOUR(timeOfOrder)<=? AND status='ACTIVE';";
		PreparedStatement getOrdersToSendSms = con.prepareStatement(query);
		getOrdersToSendSms.setString(1, tommorowString.split(" ")[0]);
		getOrdersToSendSms.setInt(2, tommorow.getHour());
		ResultSet rs = getOrdersToSendSms.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		if (orders.size() > 0) {
			String queryUpdateStatus = "Update orders SET status='PENDING_FINAL_APPROVAL' WHERE dateOfOrder=? AND HOUR(timeOfOrder)<=? AND status='ACTIVE'";
			PreparedStatement updateStatus = con.prepareStatement(queryUpdateStatus);
			updateStatus.setString(1, tommorowString.split(" ")[0]);
			updateStatus.setInt(2, tommorow.getHour());
			updateStatus.executeUpdate();
			PreparedStatement sendSms;
			String sendSmsQuery = "INSERT INTO smsSend (orderNum_fk,smsRecviedDate,smsRecviedTime) VALUES ";
			for (int i = 0; i < orders.size(); i++) {
				sendSmsQuery += "(" + orders.get(i).getOrderNum() + ",'" + dateNow + "','" + timeNow + "')";
				if (i != orders.size() - 1) {
					sendSmsQuery += ",";
				}
			}
			sendSms = con.prepareStatement(sendSmsQuery);
			sendSms.executeUpdate();
		}
		return orders;
	}

	/**
	 * 
	 * @return Two lists of orders. First List of cancelled orders and others of
	 *         orders that change to PENDING_APPROVAL_FROM_WAITING_LIST. We send
	 *         them sms that the orders were cancelled or need an approval from
	 *         waiting List
	 * @throws SQLException
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	public static List<List<Order>> sendSmsToCancelOrders() throws SQLException, NumberFormatException, ParseException {
		List<Order> orders = new ArrayList<>();
		List<Order> ordersFromWaitingList = null;
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String nowString = now.format(formatter);
		String dateNow = nowString.split(" ")[0];
		String timeNow = nowString.split(" ")[1];
		String query = "SELECT orders.* FROM orders JOIN smsSend on orders.orderNum=smsSend.orderNum_fk WHERE "
				+ "(smsSend.smsRecviedDate=? AND ((orders.status='PENDING_FINAL_APPROVAL' AND HOUR(TIMEDIFF(?,smsSend.smsRecviedTime))>=2) OR ((orders.status='PENDING_APPROVAL_FROM_WAITING_LIST' AND HOUR(TIMEDIFF(?,smsSend.smsRecviedTime))>=1)))) OR (smsSend.smsRecviedDate<? AND (orders.status='PENDING_APPROVAL_FROM_WAITING_LIST' OR orders.status='PENDING_FINAL_APPROVAL'))";
		PreparedStatement getOrdersToSendSms = con.prepareStatement(query);
		getOrdersToSendSms.setString(1, dateNow);
		getOrdersToSendSms.setString(2, timeNow);
		getOrdersToSendSms.setString(3, timeNow);
		getOrdersToSendSms.setString(4, dateNow);
		ResultSet rs = getOrdersToSendSms.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			orders.add(tmpOrder);
		}
		if (orders.size() > 0) {
			String queryCancelOrders = "UPDATE orders SET status='CANCELLED' WHERE ";
			String queryDelSms = "DELETE FROM smsSend WHERE ";
			Map<String, Map<String, List<String>>> checkWating = new LinkedHashMap<String, Map<String, List<String>>>();
			for (int i = 0; i < orders.size(); i++) {
				if (checkWating.containsKey(orders.get(i).getParkName())) {
					Map<String, List<String>> dateAndTimesForPark = checkWating.get(orders.get(i).getParkName());
					if (dateAndTimesForPark.containsKey(orders.get(i).getDateOfOrder())) {
						List<String> timesForDateInPark = dateAndTimesForPark.get(orders.get(i).getDateOfOrder());
						if (!timesForDateInPark.contains(orders.get(i).getTimeOfOrder())) {
							timesForDateInPark.add(orders.get(i).getTimeOfOrder());
						}
					} else {
						List<String> times = new ArrayList<String>();
						times.add(orders.get(i).getTimeOfOrder());
						dateAndTimesForPark.put(orders.get(i).getDateOfOrder(), times);
					}
				} else {
					Map<String, List<String>> dateAndTime = new LinkedHashMap<String, List<String>>();
					List<String> times = new ArrayList<String>();
					times.add(orders.get(i).getTimeOfOrder());
					dateAndTime.put(orders.get(i).getDateOfOrder(), times);
					checkWating.put(orders.get(i).getParkName(), dateAndTime);
				}
				queryCancelOrders += "orderNum=" + orders.get(i).getOrderNum();
				queryDelSms += "orderNum_fk=" + orders.get(i).getOrderNum();
				if (i != orders.size() - 1) {
					queryCancelOrders += " OR ";
					queryDelSms += " OR ";
				}
			}
			con.prepareStatement(queryCancelOrders).executeUpdate();
			con.prepareStatement(queryDelSms).executeUpdate();
			ordersFromWaitingList = checkWatingList(checkWating);
		}
		List<List<Order>> ordersToSendSMS = new ArrayList<>();
		ordersToSendSMS.add(orders);
		ordersToSendSMS.add(ordersFromWaitingList);
		return ordersToSendSMS;
	}

	/**
	 * This method expires all orders that were approved and did not show up
	 * 
	 * @throws SQLException
	 */
	public static void expiredApprovedOrders() throws SQLException {
		String queryUpdateStatus = "Update orders SET status='EXPIRED' WHERE ((dateOfOrder=? AND HOUR(TIMEDIFF(?,timeOfOrder))>=1 AND ?>timeOfOrder) OR dateOfOrder<?) AND status='APPROVED'";
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
		Date now = new Date();
		String timeNow = timeFormat.format(now);
		String dateNow = dateFormat.format(now);
		PreparedStatement expirePreparedStatement = con.prepareStatement(queryUpdateStatus);
		expirePreparedStatement.setString(1, dateNow);
		expirePreparedStatement.setString(2, timeNow);
		expirePreparedStatement.setString(3, timeNow);
		expirePreparedStatement.setString(4, dateNow);
		expirePreparedStatement.executeUpdate();
	}

	/**
	 * Insert order to smsSend Table with date and time of now
	 * 
	 * @param order
	 */
	private static void sendSms(Order order) {
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String nowString = now.format(formatter);
			String querySendSms = "INSERT INTO smsSend (orderNum_fk,smsRecviedDate,smsRecviedTime) VALUES (?,?,?)";
			PreparedStatement sendSmStatement;

			sendSmStatement = con.prepareStatement(querySendSms);

			sendSmStatement.setString(1, order.getOrderNum());
			sendSmStatement.setString(2, nowString.split(" ")[0]);
			sendSmStatement.setString(3, nowString.split(" ")[1]);
			sendSmStatement.executeUpdate();
		} catch (SQLException e) {
		}
	}

	/**
	 * 
	 * @param park
	 * @param date
	 * @param time
	 * @return Orders that their status are waiting for park in date and time
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	private static List<Order> getWatingOrdersForParkInDateAndTime(Park park, String date, String time)
			throws NumberFormatException, SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime nowPlus2Hours = LocalDateTime.now().plusHours(2);
		List<Order> ordersInTimeIntervalForDateInPark = new ArrayList<Order>();
		if (park == null)
			return null;
		String query = "SELECT * FROM orders where parkName_fk=? AND dateOfOrder=? AND HOUR(TIMEDIFF(timeOfOrder,?))<? AND status=? ORDER BY orderCreationDate ASC";
		PreparedStatement getWatingOrdersStatement = con.prepareStatement(query);
		getWatingOrdersStatement.setString(1, park.getParkName());
		getWatingOrdersStatement.setString(2, date);
		getWatingOrdersStatement.setString(3, time);
		getWatingOrdersStatement.setInt(4, park.getParkVisitDuration());
		getWatingOrdersStatement.setString(5, OrderStatus.WAITING.name());
		ResultSet rs = getWatingOrdersStatement.executeQuery();
		while (rs.next()) {
			Order tmpOrder = new Order(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
					OrderStatus.valueOf(rs.getString(6)), OrderType.valueOf(rs.getString(7)), rs.getString(8),
					rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12));
			LocalDateTime dateTimeOfOrder = LocalDateTime
					.parse(tmpOrder.getDateOfOrder() + " " + tmpOrder.getTimeOfOrder(), formatter);
			if (dateTimeOfOrder.isBefore(nowPlus2Hours))
				continue;
			ordersInTimeIntervalForDateInPark.add(tmpOrder);
		}

		return ordersInTimeIntervalForDateInPark;
	}

	/**
	 * 
	 * @param dateAndTimesToCheckForParks
	 * @return List of orders that were changed from waiting to
	 *         PENDING_APPROVAL_FROM_WAITING_LIST
	 * @throws NumberFormatException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static List<Order> checkWatingList(Map<String, Map<String, List<String>>> dateAndTimesToCheckForParks)
			throws NumberFormatException, SQLException, ParseException {
		List<Order> ordersToSendSms = new ArrayList<Order>();
		for (String parkName : dateAndTimesToCheckForParks.keySet()) {
			Park park = getCertainPark(parkName);
			if (park == null)
				continue;
			Map<String, List<String>> dateAndTimesToCheckForPark = dateAndTimesToCheckForParks.get(parkName);
			for (String date : dateAndTimesToCheckForPark.keySet()) {
				List<String> timesToCheckForParkInDate = dateAndTimesToCheckForPark.get(date);
				for (String time : timesToCheckForParkInDate) {
					List<Order> ordersInWait = getWatingOrdersForParkInDateAndTime(park, date, time);
					for (Order order : ordersInWait) {

						if (validateDate(order)) {
							changeOrderStatus(order.getOrderNum(), OrderStatus.PENDING_APPROVAL_FROM_WAITING_LIST);
							sendSms(order);
							ordersToSendSms.add(order);
						}
					}
				}
			}
		}
		return ordersToSendSms;
	}

	/**
	 * @return Check every park and check if it is full now. if there are insert
	 *         those parks to parkFull Table.
	 */
	public static void checkIfParksFull() throws SQLException {
		LocalTime opening = LocalTime.of(EntityConstants.PARK_OPEN, 0);
		LocalTime closing = LocalTime.of(EntityConstants.PARK_CLOSED + 4, 0);
		if (LocalTime.now().isBefore(opening) || LocalTime.now().isAfter(closing))
			return;
		List<Park> parkList = getParks();
		for (Park park : parkList) {
			if (park.getParkCurrentVisitors() > park.getParkMaxVisitorsDefault()) {
				String updateParkFullQuery = "INSERT IGNORE INTO parkFull (parkName_fk,dateFull,timeFull) VALUES (?,?,?)";
				PreparedStatement updateParkFull = con.prepareStatement(updateParkFullQuery);
				updateParkFull.setString(1, park.getParkName());
				updateParkFull.setString(2, LocalDate.now().toString());
				Date dateNow = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("HH:00:00");
				String timeNow = formatter.format(new Date());
				updateParkFull.setString(3, timeNow);
				updateParkFull.executeUpdate();
			}
		}
	}

	/**
	 * A method that gets park name and returns from the DB Park with all it's
	 * details
	 * 
	 * @param String
	 * @return Park
	 * @throws SQLException
	 * 
	 */
	public static Park getPark(String parkname) throws SQLException {
		Park parktoreturn = null;
		PreparedStatement getParkdetails;
		getParkdetails = con.prepareStatement("SELECT * FROM park WHERE parkName=?;");
		getParkdetails.setString(1, parkname);

		ResultSet rs = getParkdetails.executeQuery();
		if (rs.next())
			parktoreturn = new Park(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
		return parktoreturn;
	}
/**
 * This method delete all orders with status waiting in the DB
 * @throws SQLException
 */
	public static void delAllOldWaitingOrders() throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		now = now.plusHours(2);
		String dateString[] = formatter.format(now).split(" ");
		String queryDelWaiting = "DELETE FROM orders WHERE status='WAITING' AND (dateOfOrder<? OR(dateOfOrder=? AND timeOfOrder<=?));";
		PreparedStatement delWaitingStatement = con.prepareStatement(queryDelWaiting);
		delWaitingStatement.setString(1, dateString[0]);
		delWaitingStatement.setString(2, dateString[0]);
		delWaitingStatement.setString(3, dateString[1]);
		delWaitingStatement.executeUpdate();
	}
}
