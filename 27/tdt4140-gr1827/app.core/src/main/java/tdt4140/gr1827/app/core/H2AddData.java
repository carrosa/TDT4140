package tdt4140.gr1827.app.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class H2AddData 
{
	public H2AddData() {
		h2Connector.localConnect();
	}	
	
	public void addCounty() {

		String Counties = "Oestfold,Akershus,Oslo,Hedemark,Oppland,Buskerud,Vestfold,Telemark,Aust-Agder,Vest-Agder,Rogaland,Hordaland,Sogn og Fjordane,Moere og Romsdal,Troendelag,Nordland,Troms,Finnmark";
		String[] CountiesArr = Counties.split(",");
		try {
			for (int i = 1; i < CountiesArr.length; i++) {
				PreparedStatement stmt = h2Connector.conn.prepareStatement("INSERT INTO COUNTY VALUES(?, ?)");
				// String query = String.formatcd("INSERT INTO County(CountyID, Name)" + "VALUES (%d, %s)" , i, CountiesArr[i]);
				stmt.setString(2, CountiesArr[i]);
				stmt.setInt(1, i);
				stmt.executeUpdate();
			}

		} catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

	}
	public static void main(String[] args) throws Exception {
		
		//DbState.purgedb();
		//DbState db = new DbState();
		//PreparedStatement stmt = h2Connector.conn.prepareStatement("SELECT * FROM County");
		//ResultSet rs = stmt.executeQuery();
		
		//DbState.purgedb();
		//db.GenerateDb();
		
		//H2AddData H2addData = new H2AddData();
		//H2addData.addCounty();
		
		
		
	}
	
}
