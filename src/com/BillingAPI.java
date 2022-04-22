package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Billing;

@Path("/Billing")
public class BillingAPI {
	Billing BillingObj = new Billing();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBilling() {
		return BillingObj.readBilling();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBilling(
	 @FormParam("bAccNo") String bAccNo,		
	 @FormParam("bDate") String bDate,
	 @FormParam("bUnit") String bUnit,
	 @FormParam("bUnitPrice") String bUnitPrice,
	 @FormParam("bAmount") String bAmount)
	{
	 String output = BillingObj.insertBilling(bAccNo, bDate, bUnit, bUnitPrice, bAmount);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBilling(String billData)
	{
	//Convert the input string to a JSON object
	 JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject();
	//Read the values from the JSON object
	 String bID = billObject.get("bID").getAsString();
	 String bAccNo = billObject.get("bAccNo").getAsString();
	 String bDate = billObject.get("bDate").getAsString();
	 String bUnit = billObject.get("bUnit").getAsString();
	 String bUnitPrice = billObject.get("bUnitPrice").getAsString();
	 String bAmount = billObject.get("bAmount").getAsString();
	 String output = BillingObj.updateBilling(bID, bAccNo, bDate, bUnit, bUnitPrice, bAmount);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBilling(String billData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(billData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String bID = doc.select("bID").text();
	 String output = BillingObj.deleteBilling(bID);
	return output;
	}
}
