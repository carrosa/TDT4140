package tdt4140.gr1827.app.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthWarning {

	public HealthWarning() {
		MySqlConnector.connect();
	}
	
	public static List<HealthWarningObject> checkPatients(String docPNr) {
		List<HealthWarningObject> patientInfo = new ArrayList<HealthWarningObject>();
		Map<String, List<Integer>> patientThresholds = new HashMap<String, List<Integer>>();
		List<String> patientsPNr = new ArrayList<>();
		
		String sql = String.format("SELECT PatientPNr FROM PatientDoctor WHERE DoctorPNr = '%s'", docPNr);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//Henter pasienter knyttet til legen
		try {
			stmt = MySqlConnector.conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				patientsPNr.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.err.println("HealthWarning - Patients");
			e.printStackTrace();
		}
		//Henter varsel kriterier
		try {
			for (String patientPNr : patientsPNr) {
				sql = String.format("SELECT * FROM WarningThreshold WHERE PNr = '%s'", patientPNr);
				stmt = MySqlConnector.conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				if (rs.next()) {
					Integer[] tempArr = {rs.getInt("AvgPulse"), rs.getInt("MaxPulse"), rs.getInt("MinPulse"), rs.getInt("Steps")};
					List<Integer> tempList = Arrays.asList(tempArr);
					patientThresholds.put(patientPNr, tempList);
				}
			}
			
		} catch (SQLException e) {
			System.err.println("HealthWarning - Thresholds");
			e.printStackTrace();
		}
		//Henter pasientene sine data og sammenligner med kriterier
				try {
					for (String patientPNr : patientsPNr) {
						sql = String.format("SELECT * FROM UserInput WHERE PNr = '%s'", patientPNr);
						stmt = MySqlConnector.conn.prepareStatement(sql);
						rs = stmt.executeQuery();
						List<List<Integer>> patientInfoList = new ArrayList<List<Integer>>();
							while (rs.next()) {
								Integer[] tempArr = {rs.getInt("AvgPulse"), rs.getInt("MaxPulse"), rs.getInt("MinPulse"), rs.getInt("Steps")};
								List<Integer> thresholds = patientThresholds.get(patientPNr);
								if (tempArr[0] >= thresholds.get(0) || tempArr[1] >= thresholds.get(1) || tempArr[2] <= thresholds.get(2) || tempArr[3] <= thresholds.get(3)) {
									patientInfo.add(new HealthWarningObject(rs.getString("PNr"), rs.getString("TimesTamp"), tempArr[0], tempArr[1], tempArr[2], tempArr[3]));
								}
							}		
					}
				} catch (SQLException e) {
					System.err.println("HealthWarning - Patient info");
					e.printStackTrace();
				}
		return patientInfo;
	}
}
