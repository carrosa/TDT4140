package tdt4140.gr1827.app.core;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AfterSendDataTest {
	String[] timestamps = new String[]{"2009-09-24 00:00:00", "2010-09-24 00:00:00", "2011-09-24 00:00:00", "2012-09-24 00:00:00", "2013-09-24 00:00:00"};
	int[] avgPulses = new int[]{100, 110, 105, 107, 103};
	int[] maxPulses = new int[]{120, 130, 115, 117, 113};
	int[] minPulses = new int[]{90, 100, 95, 97, 93};
	int[] steps = new int[]{2000, 5000, 7500, 6500, 4500};
	
	@Before
	public void setUp() {
		MySqlConnector.connect();
		generateUser();
		generateUserInput();
	}
	
	public void generateUser() {
		String query = "INSERT INTO User VALUES('14253675234', 'Stein', 'Larsen', 90054367, 'sl@gmail.com', 'rullekake', 'Male', 3, 1, 1, '1999-06-24', 1)";
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
			stmt.executeUpdate();
		} catch (SQLException e) {
			shutDown();
			e.printStackTrace();
		}
	}
	
	public void generateUserInput(){
		try {
			for(int i = 0; i < 5; i++) {
				String query = "INSERT INTO UserInput VALUES('14253675234', ?, ?, ?, ?, ?, false)";
				PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
				stmt.setString(1, timestamps[i]);
				stmt.setInt(2, avgPulses[i]);
				stmt.setInt(3, maxPulses[i]);
				stmt.setInt(4, minPulses[i]);
				stmt.setInt(5, steps[i]);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			shutDown();
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGenerateQuery() {
		Timestamp t1 = DataForGraph.generateTimestamp("01012000");
		Timestamp t2 = DataForGraph.generateTimestamp("01012018");
		AfterSendData asd1 = new AfterSendData("14253675234", t1, t2);
		String query1 = asd1.generateSendDataQuery();
		Assert.assertEquals("UPDATE UserInput SET Visible = 1 WHERE PNr = '14253675234' AND TimeStamp >= '2000-01-01 00:00:00.0' AND TimeStamp <= '2018-01-01 00:00:00.0'", query1);
	}
	
	@Test
	public void testInitialPermission(){
		String query = "SELECT Visible FROM UserInput WHERE PNr = '14253675234'";
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Assert.assertEquals(false, rs.getBoolean("Visible"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			shutDown();
		}
	}
	
	@Test
	public void testChangePermissionStepsAndPulse(){
		Timestamp t1 = DataForGraph.generateTimestamp("24092009");
		Timestamp t2 = DataForGraph.generateTimestamp("24092010");
		Timestamp t3 = DataForGraph.generateTimestamp("24092012");
		Timestamp t4 = DataForGraph.generateTimestamp("24092014");
		
		AfterSendData asd1 = new AfterSendData("14253675234", t1, t2);
		asd1.sendData();
		String query = "SELECT Visible FROM UserInput WHERE PNr = '14253675234'";
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int i = 0;
			while(rs.next()) {
				i++;
				if(i <= 2) {
					Assert.assertEquals(true, rs.getBoolean("Visible"));
				}else {
					Assert.assertEquals(false, rs.getBoolean("Visible"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			shutDown();
		}
		
		AfterSendData asd2 = new AfterSendData("14253675234", t3, t4);
		asd2.sendData();
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			int i = 0;
			while(rs.next()) {
				i++;
				if(i <= 2 || i > 3) {
					Assert.assertEquals(true, rs.getBoolean("Visible"));
				}else {
					Assert.assertEquals(false, rs.getBoolean("Visible"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			shutDown();
		}
		
		AfterSendData asd3 = new AfterSendData("14253675234", t1, t4);
		asd3.sendData();
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Assert.assertEquals(true, rs.getBoolean("Visible"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			shutDown();
		}
		shutDown();
		setUp();
	}
		

	
	@After
	public void shutDown() {
		try {
			String query1 = "DELETE FROM User WHERE PNr = '14253675234'";
			String query2 = "DELETE FROM UserInput WHERE PNr = '14253675234'";
			PreparedStatement stmt1 = MySqlConnector.conn.prepareStatement(query1);
			PreparedStatement stmt2 = MySqlConnector.conn.prepareStatement(query2);
			stmt1.executeUpdate();
			stmt2.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
