package tdt4140.gr1827.app.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import tdt4140.gr1827.app.core.MySqlConnector;
import java.util.ArrayList;
import java.util.List;

public class ProductRegistration 
{	
	public ProductRegistration()
	{
		MySqlConnector.connect();
	}
	
	public static void AddProduct(String pNr,int productNr) 
	{
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement("INSERT INTO UserProduct(PNr, ProductNr) VALUES (?,?)");
			stmt.setString(1,pNr);
			stmt.setInt(2,productNr);
			stmt.executeUpdate();
		}
		
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static List<Integer> UpdateProducts() 
	{
		List<Integer> products = new ArrayList<>();
		try {
			String sql = String.format("SELECT ProductNr FROM UserProduct WHERE PNr = " + Login.getSsnString());
	        PreparedStatement stmt = MySqlConnector.conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	        		products.add(Integer.parseInt(rs.getString(1)));
	        }
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
        return products;

	}
	
	public static void DeleteProduct(int productNr) 
	{
		try {
			PreparedStatement stmt = MySqlConnector.conn.prepareStatement("DELETE FROM UserProduct WHERE ProductNr = '%d'", productNr);
			stmt.executeUpdate();
		}
		
		catch(Exception e){
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		ProductRegistration product = new ProductRegistration();
		product.AddProduct("03114290314", 13);
		System.out.println("Product added!");
	}
}
