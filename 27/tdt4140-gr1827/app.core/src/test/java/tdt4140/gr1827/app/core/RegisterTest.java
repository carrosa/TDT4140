package tdt4140.gr1827.app.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterTest {
	
	private Register register;
	
	@Before
	public void setUp() throws SQLException {
		register = new Register();
		register.addUser("69493849584", "Andre", "Gomes", "58483848", "gomes@gmail.com", "rullekake", "rullekake", "Male", "Oslo", "1994-12-12");

	}
	
	@Test
	public void testAddUser() throws SQLException {
		String sql = String.format("SELECT * FROM User WHERE PNr='69493849584'");
		PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Assert.assertTrue(rs.next());
	}
	
	@Test
	public void testUserValidation() throws SQLException {
		String validation = register.userValidation("6949384958", "Andre", "Gomes", "58483848", "gomes@gmail.com", "rullekake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("Personnummer må inneholde 11 tall", validation);
		validation = register.userValidation("69493849584", "Andre", "Gomes", "58483848", "gomes@gmail.com", "kake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("Passordene må være like", validation);
		validation = register.userValidation("69493849584", "Andre", "Gomes", "584f83848", "gomes@gmail.com", "rullekake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("Telefonnummer må inneholde bare tall", validation);
		validation = register.userValidation("69493849584", "Andre", "", "58483848", "gomes@gmail.com", "rullekake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("Alle feltene må fylles ut", validation);
		validation = register.userValidation("69493849584", "Andre", "Gomes", "58483848", "gomes@gmail.com", "rullekake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("Denne eposten finnes allerede", validation);
		validation = register.userValidation("69493849584", "Andre", "Gomes", "58483848", "gomes2@gmail.com", "rullekake", "rullekake", "Oslo", "1994-12-12");
		Assert.assertEquals("", validation);
	}
	
	@After
	public void tearDown() throws SQLException {
		String sql2 = String.format("DELETE FROM User WHERE PNr=69493849584");
		PreparedStatement stmt2 = MySqlConnector.conn.prepareStatement(sql2);
		stmt2.executeUpdate();
	}
}