package tdt4140.gr1827.app.core;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Doctor {
	
	// Made Query class for faster writing of Querys/updates to DB
	Query query = new Query(); //Import here so we can use it/ gets connected to DB with this

	public ObservableList<Patient> patientSearch(String patientName) throws SQLException {
		//Hente ut en liste over pasientpersonnummer fra PatientDoctor
        String sql2 = String.format("SELECT * FROM PatientDoctor");
        ResultSet rs2 = Query.executeQuery(sql2);
        ObservableList<String> patientsSsn = FXCollections.observableArrayList();
        while (rs2.next()) {
        		if (rs2.getString(2).equals(Login.getSsnString())){
        			patientsSsn.add(rs2.getString(1));
        		}
        }
        //Hente ut ønsket informasjon fra mine pasienter
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        for (String ssn : patientsSsn) {
        		String sql3 = String.format("SELECT * FROM User WHERE PNr='%s'", ssn);
	        	ResultSet rs3 = Query.executeQuery(sql3);
	        	if (rs3.next() && (rs3.getString(2).startsWith(patientName) || rs3.getString(3).startsWith(patientName))) {
	        		patients.add(new Patient(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(7)));
	        		}
	        	}
	    return patients;
	}
	
		
	// argument names is the same as i the DB
	public void updateHealthCriteria(String ssn, int AvgPulse, int MaxPulse, int MinPulse, int Steps) {
		// First check if WarningThreshold for Patient exists:
		String sql = String.format("Select * FROM WarningThreshold WHERE PNr='%s'", ssn);
		ResultSet rs = Query.executeQuery(sql);
		try {
			if (!rs.next()) {
				sql = String.format("INSERT INTO WarningThreshold(PNr, AvgPulse, MaxPulse, MinPulse, Steps) VALUES('%s', %d, %d, %d, %d)", ssn, AvgPulse, MaxPulse, MinPulse, Steps);
				Query.executeUpdate(sql);			
			}else {
				sql = String.format("UPDATE WarningThreshold SET AvgPulse=%d, MaxPulse=%d, MinPulse=%d, Steps=%d WHERE PNr='%s'", AvgPulse, MaxPulse, MinPulse, Steps, ssn);
				Query.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
}
