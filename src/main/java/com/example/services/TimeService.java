package com.example.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.models.ConnectionDto;
import com.example.models.EmpDto;
import com.example.models.Time;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class TimeService {

    @GET
    @Path("/test")
    public Time get() {
        return new Time();
    }

    @POST
    @Path("/createconnection")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ConnectionDto createconnection(ConnectionDto conDto) throws URISyntaxException,SQLException
    {
    	
    	/*
    	 * taken help from https://github.com/heroku/devcenter-java-database refer for more information
    	 */
    	
        try {
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

			Connection con;
			con = DriverManager.getConnection(dbUrl, username, password);
			conDto.setResponce("Connection created Successfully");
			Statement stat = con.createStatement();
			
			//data base close and clean up
			stat.close();
			con.close();
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conDto.setResponce("Connection Error: "+ e.toString());
		}
         return conDto;
    }
    
    @POST
    @Path("/createrow")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EmpDto insertRow() throws URISyntaxException, SQLException
    {
    	ConnectionDto condto = new ConnectionDto();
    	EmpDto empdto = new EmpDto();
		try {
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();

			Connection con;
			con = DriverManager.getConnection(dbUrl, username, password);
			Statement stat = con.createStatement();
//			stat.executeUpdate("insert into employees (employee_id,last_name, first_name, title) values (4,'ramesh','raju','Manager')"); // this query has error not working 
			ResultSet rst = stat.executeQuery("select * from employees");
			while (rst.next()) {
				empdto.setFirst_name(rst.getString("first_name"));
				empdto.setLast_name(rst.getString("last_name"));
				
			}

			condto.setResponce("query exceucted");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			condto.setResponce("Error Occured : "+e.toString());
		}
    	return empdto;
    }
}

