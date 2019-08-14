package tdt4140.gr1827.app.core;

import java.sql.Connection;
import java.sql.DriverManager;

public class LocalMySQLConnector {
	public static Connection conn;

    /**
    Connection connect
    *
    * Connects to the database.
    */
    public static Connection connect(){
        try {
        	String url = "jdbc:mysql://localhost:3306/HealthManager?useSSL=false&serverTimezone=CET";
        conn = DriverManager.getConnection(url, "root", "asdf");


        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
	    }
}
