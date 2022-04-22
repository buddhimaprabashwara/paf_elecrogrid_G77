package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db01?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "apache2004v");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	
	public String insertBilling(String bAccNo, String bDate, String bUnit, String bUnitPrice, String bAmount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into billapi(`bID`,`bAccNo`,`bDate`,`bUnit`,`bUnitPrice`,`bAmount`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, bAccNo);
			preparedStmt.setString(3, bDate);
			preparedStmt.setString(4, bUnit);
			preparedStmt.setString(5, bUnitPrice);
			preparedStmt.setString(6, bAmount);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readBilling() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Bill ID</th><th>Account No</th><th>Date</th><th>Unit Total</th><th>Unit Price</th><th>Amount</th></tr>";
			String query = "select * from billapi";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String bID = Integer.toString(rs.getInt("bID"));
				String bAccNo = rs.getString("bAccNo");
				String bDate = rs.getString("bDate");
				String bUnit = rs.getString("bUnit");
				String bUnitPrice = rs.getString("bUnitPrice");
				String bAmount = rs.getString("bAmount");

				// Add into the html table
				output += "<tr><td>" + bID + "</td>";
				output += "<td>" + bAccNo + "</td>";
				output += "<td>" + bDate + "</td>";
				output += "<td>" + bUnit + "</td>";
				output += "<td>" + bUnitPrice + "</td>";
				output += "<td>" + bAmount + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the billing.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateBilling(String bID, String bAccNo, String bDate, String bUnit, String bUnitPrice, String bAmount) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE billapi SET bAccNo=?,bDate=?,bUnit=?,bUnitPrice=?,bAmount=?" + "WHERE bID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, bAccNo);
			preparedStmt.setString(2, bDate);
			preparedStmt.setString(3, bUnit);
			preparedStmt.setString(4, bUnitPrice);
			preparedStmt.setString(5, bAmount);
			preparedStmt.setInt(6, Integer.parseInt(bID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteBilling(String bID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from billapi where bID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the billing.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
