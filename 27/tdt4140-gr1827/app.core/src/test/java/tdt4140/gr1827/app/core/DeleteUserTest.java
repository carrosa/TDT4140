package tdt4140.gr1827.app.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeleteUserTest {

private Register register;
	
	@Before
	public void setUp() throws SQLException {
		register = new Register();
		register.addUser("12038945456", "Peder", "Olafson", "98994671", "pederOL@gmail.com", "rullekake", "rullekake", "Male", "Telemark", "1994-12-12");
		
	}
	
	@Test
	public void testDeleteUser() throws SQLException {
		String sql = String.format("SELECT * FROM User WHERE PNr=12038945456");
		PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		Assert.assertTrue(rs.next());
		DeleteUser.burnUser("12038945456");
		Assert.assertTrue(!rs.next());
	}
	
	
	@After
	public void tearDown() throws SQLException {
		String sql2 = String.format("DELETE FROM User WHERE PNr=12038945456");
		PreparedStatement stmt2 = MySqlConnector.conn.prepareStatement(sql2);
		stmt2.executeUpdate();
	}
	
}
