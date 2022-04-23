package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Monitoring;

@Path("/Monitoring")
public class MonitoringAPI {
	Monitoring MonitoringObj = new Monitoring();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readMonitoring() {
		return MonitoringObj.readMonitoring();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertMonitoring(
	 @FormParam("Date") String Date,		
	 @FormParam("Lreading") String Lreading,
	 @FormParam("Nreading") String Nreading,
	 @FormParam("Anumber") String Anumber,
	 @FormParam("Address") String Address)
	{
	 String output = MonitoringObj.insertMonitoring(Date, Lreading, Nreading, Anumber, Address);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateMonitoring(String MoniData)
	{
	//Convert the input string to a JSON object
	 JsonObject MoniObject = new JsonParser().parse(MoniData).getAsJsonObject();
	//Read the values from the JSON object
	 String userID = MoniObject.get("userID").getAsString();
	 String Date = MoniObject.get("Date").getAsString();
	 String Lreading = MoniObject.get("Lreading").getAsString();
	 String Nreading = MoniObject.get("Nreading").getAsString();
	 String Anumber = MoniObject.get("Anumber").getAsString();
	 String Address = MoniObject.get("Address").getAsString();
	 String output = MonitoringObj.updateMonitoring(userID, Date, Lreading, Nreading, Anumber, Address);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteMonitoring(String MoniData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(MoniData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String userID = doc.select("userID").text();
	 String output = MonitoringObj.deleteMonitoring(userID);
	return output;
	}
}
