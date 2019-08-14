package tdt4140.gr1827.app.core;

import java.sql.*;

public class Query {
	
	public Query() {
		MySqlConnector.connect();
	}
	
	   private static ResultSet makeExecuteQuery(String sql) throws SQLException {
	        PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
	        return stmt.executeQuery();
	    }

	    private static void makeExecuteUpdate(String sql) throws SQLException {
	    		PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
	        stmt.executeUpdate();
	    }
	    
	    public static void executeUpdate(String sql) {
	    		try {
					makeExecuteUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    }
	    
	    public static ResultSet executeQuery(String sql) {
	    		try {
					return makeExecuteQuery(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    		return null;
	    }

}
