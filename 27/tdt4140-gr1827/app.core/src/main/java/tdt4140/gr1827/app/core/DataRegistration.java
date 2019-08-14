package tdt4140.gr1827.app.core;
import java.sql.*;
 
import com.sun.org.apache.bcel.internal.generic.Select;

public class DataRegistration {
	
	public DataRegistration() {
		MySqlConnector.connect();	
	}
	// returns false if the time stamp is already in use
	public boolean checkTimeStamp(Timestamp timeStamp, int pid) {
		try {
			String sql = String.format("SELECT * FROM UserInput WHERE PNr ='%d' AND TimeStamp = '%s'", pid, timeStamp);
			PreparedStatement stmt =  MySqlConnector.conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return false;
			}
			return true;			
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
		return false;
	}
	// Returns true if there exists a user with this pid 
	public boolean checkPid(int pid) {
		try {			
			String sql = String.format("SELECT PNr FROM User WHERE PNr ='%d'", pid);
			PreparedStatement stmt =  MySqlConnector.conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	// Records data in the database, 
	public void addUserInput(Timestamp timeStamp, int deviceNr, int avgPulse, int maxPulse, int minPulse, int steps)
	{
		String pNr = "-10";
		try {
			String sql = String.format("SELECT PNr FROM UserProduct WHERE ProductNr = '%d'", deviceNr);
			PreparedStatement pNrStmt = MySqlConnector.conn.prepareStatement(sql); 
			ResultSet rs = pNrStmt.executeQuery();
			
			if (rs.next()) {
				pNr = rs.getString(1);
			}
			sql = String.format("INSERT INTO UserInput(PNr, TimeStamp, AvgPulse, MaxPulse, MinPulse, Steps) VALUES ('%s', '%s', '%d', '%d', '%d', '%d')", pNr, timeStamp, avgPulse, maxPulse, minPulse, steps);
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
			stmt.executeUpdate();

				
		}
		catch (Exception e) {
			 System.err.println("Got an exception! addUserInput");
		     System.err.println(e.getMessage());
		}
				
	}
	

	
}
