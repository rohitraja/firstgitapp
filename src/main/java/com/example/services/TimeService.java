package com.example.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.models.ConnectionDto;
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
    public ConnectionDto createconnection(ConnectionDto conDto)
    {
    	Connection con;
    	Statement stmt;
	    try {
			String url = 
			       "jdbc:postgresql://reddwarf.cs.rit.edu/rkr";
			    // DATABASE CONNECTION MAGIC :-)
			    Class.forName("org.postgresql.Driver");
			    con = DriverManager.getConnection(url, conDto.getUserName(), conDto.getPwd());
			    stmt = con.createStatement();
				conDto.setResponce("Successfully Connection created");

			    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conDto.setResponce("Class no found Exception :"+e.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conDto.setResponce("SQLException e :"+e.toString());

		}
    	
    	return conDto;
    }
}

