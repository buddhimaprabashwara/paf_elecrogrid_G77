package com;

import model.Customer;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Customer")
public class CustomerService
{
	Customer itemObj = new Customer();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	
	public String readCustomer()
	{
		return itemObj.readCustomer();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String insertCustomer(@FormParam("customerName") String customerName,
					@FormParam("nic") String nic,
					@FormParam("address") String address,
					@FormParam("mobileNo") String mobileNo,
					@FormParam("email") String email)
	{
		String output = itemObj.insertCustomer(customerName, nic, address, mobileNo, email);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateCustomer(String itemData)
	{
		//Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		
		//Read the values from the JSON object
		String customerID = itemObject.get("customerID").getAsString();
		String customerName = itemObject.get("customerName").getAsString();
		String nic = itemObject.get("nic").getAsString();
		String address = itemObject.get("address").getAsString();
		String mobileNo = itemObject.get("mobileNo").getAsString();
		String email = itemObject.get("email").getAsString();
		String output = itemObj.updateCustomer(customerID, customerName, nic, address, mobileNo, email);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String deleteCustomer(String itemData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
		
		//Read the value from the element <customerID>
		String customerID = doc.select("customerID").text();
		String output = itemObj.deleteCustomer(customerID);
		
		return output;
	}
}
