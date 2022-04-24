package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML 
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Payment;

@Path("/Payment")
public class PaymentAPI {
	Payment PaymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment() {
		return PaymentObj.readPayment();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(
	 @FormParam("cHname") String cHname,		
	 @FormParam("cDtype") String cDtype,
	 @FormParam("ExpiryDate") String ExpiryDate,
	 @FormParam("SCode") String SCode,
	 @FormParam("cDnumber") String cDnumber)
	{
	 String output = PaymentObj.insertPayment(cHname, cDtype, ExpiryDate, SCode, cDnumber);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String PayData)
	{
	//Convert the input string to a JSON object
	 JsonObject PayObject = new JsonParser().parse(PayData).getAsJsonObject();
	//Read the values from the JSON object
	 String cdID = PayObject.get("cdID").getAsString();
	 String cHname = PayObject.get("cHname").getAsString();
	 String cDtype = PayObject.get("cDtype").getAsString();
	 String ExpiryDate = PayObject.get("ExpiryDate").getAsString();
	 String SCode = PayObject.get("SCode").getAsString();
	 String cDnumber = PayObject.get("cDnumber").getAsString();
	 String output = PaymentObj.updatePayment(cdID, cHname, cDtype, ExpiryDate, SCode, cDnumber);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String PayData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(PayData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String cdID = doc.select("cdID").text();
	 String output = PaymentObj.deletePayment(cdID);
	return output;
	}
}
