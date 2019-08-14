package tdt4140.gr1827.app.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AnonymousTest {
	
	private Register register;
	private Login login;

	@Before
	public void setupUser() throws SQLException {
		login = new Login();
		register = new Register();
		register.addUser("69493849584", "Andre", "Gomes", "58483848", "gomes@gmail.com", "rullekake", "rullekake", "Male", "Oslo", "1994-12-12");
		login.checkLogin("gomes@gmail.com", "rullekake");
	}

	@Test
	public void testUpdateAnonSwitch() throws SQLException {
		Anonymous anon = new Anonymous();
		String correctTestDataVisible= "0";
		anon.anonymous(true); // Sets anonymous status
		String sql = "SELECT * FROM User WHERE PNr='69493849584'";
		ResultSet rs = Query.executeQuery(sql);
		if (rs.next()) {
			Assert.assertTrue(rs.getString("Visible").equals(correctTestDataVisible));
		}
		anon.anonymous(false); // Sets anonymous status
		String sql2 = "SELECT * FROM User WHERE PNr='69493849584'";
		ResultSet rs2 = Query.executeQuery(sql2);
		if (rs2.next()) {
			Assert.assertEquals(rs2.getString("Visible"), "1");
		}
	}
	
	@After
	public void tearDown() throws SQLException {
		DeleteUser.burnUser("69493849584");
	}
}
