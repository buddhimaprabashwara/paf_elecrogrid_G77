package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Monitoring {

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

	
	public String insertMonitoring(String Date, String Lreading, String Nreading, String Anumber, String Address) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into monitorings(`userID`,`Date`,`Lreading`,`Nreading`,`Anumber`,`Address`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, Date);
			preparedStmt.setString(3, Lreading);
			preparedStmt.setString(4, Nreading);
			preparedStmt.setString(5, Anumber);
			preparedStmt.setString(6, Address);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the monitorings.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readMonitoring() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Bill ID</th><th>Account No</th><th>Date</th><th>Unit Total</th><th>Unit Price</th><th>Amount</th></tr>";
			String query = "select * from monitorings";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String userID = Integer.toString(rs.getInt("userID"));
				String Date = rs.getString("Date");
				String Lreading = rs.getString("Lreading");
				String Nreading = rs.getString("Nreading");
				String Anumber = rs.getString("Anumber");
				String Address = rs.getString("Address");

				// Add into the html table
				output += "<tr><td>" + userID + "</td>";
				output += "<td>" + Date + "</td>";
				output += "<td>" + Lreading + "</td>";
				output += "<td>" + Nreading + "</td>";
				output += "<td>" + Anumber + "</td>";
				output += "<td>" + Address + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the monitorings.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateMonitoring(String userID, String Date, String Lreading, String Nreading, String Anumber, String Address) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE monitorings SET Date=?,Lreading=?,Nreading=?,Anumber=?,Address=?" + "WHERE userID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, Date);
			preparedStmt.setString(2, Lreading);
			preparedStmt.setString(3, Nreading);
			preparedStmt.setString(4, Anumber);
			preparedStmt.setString(5, Address);
			preparedStmt.setInt(6, Integer.parseInt(userID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the monitorings.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteMonitoring(String userID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from monitorings where userID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the monitorings.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
