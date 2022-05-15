package model;

import java.sql.*;

public class Feedback {

	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
		 Class.forName("com.mysql.jdbc.Driver");
		 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/feedbackdb", "root", "admin");
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }

	 return con;
	}
	
	//Read function
	public String readNotes()
	{
	 String output = "";
	 
	 try
	 {
	  Connection con = connect();
	  if (con == null)
	  {
		  return "Error while connecting to the database for reading.";
	  }
	 
	// Prepare the HTML table to be displayed
	 output = "<table border='1' class='table table-striped'><tr>"
	 + "<th>Feedback ID</th>"
	 + "<th>User ID</th>"
	 + "<th>User Name</th>"
	 + "<th>Subject</th>"
	 + "<th>Date</th>"
	 + "<th>Comment</th>"
	 + "<th>Update</th><th>Delete</th></tr>";
	 
	 String query = "select * from feedbacks";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 
	 // Iterate through the rows in the result set
	 while (rs.next())
	 {
		 String noticeID = Integer.toString(rs.getInt("noticeID"));
		 String FeedbackID = rs.getString("FeedbackID");
		 String UserID = rs.getString("UserID");
		 String UserName = rs.getString("UserName");
		 String Subject = rs.getString("Subject");
		 String Date = rs.getString("Date");
		 String Comment = rs.getString("Comment");
	 
		 // Add a row into the HTML table
//		 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + noticeID
//				 + "'>" + UserID + "</td>"; 
		 output += "<tr><td><input id=\'hidItemIDUpdate\' name=\'hidItemIDUpdate\' type=\'hidden\' value=\'"
					+ noticeID + "'>" + FeedbackID + "</td>";
		 output += "<td>" + UserID + "</td>";
		 output += "<td>" + UserName + "</td>";
		 output += "<td>" + Subject + "</td>";
		 output += "<td>" + Date + "</td>";
		 output += "<td>" + Comment + "</td>";
		
		 
		 // Buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-noticeID='" + noticeID +"'></td>"
		 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-noticeID='" + noticeID + "'></td></tr>";
	 }
	 con.close();
	 
	 // Complete the HTML table
	 output += "</table>";
	}
	 
	catch (Exception e)
	{
		output = "Error while reading the Notes.";
		System.err.println(e.getMessage());
	}
	 
	return output;
	}
	
	//Insert function
	public String insertNotes(String FeedbackID, String UserID, String UserName, String Subject, String Date, String Comment)
	{
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database";
	 }
	 
	 // Prepared statement
	 String query = " insert into feedbacks(`noticeID`,`FeedbackID`,`UserID`,`UserName`,`Subject`, `Date`, `Comment`)" + " values (?, ?, ?, ?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 
	 // Binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, FeedbackID);
	 preparedStmt.setString(3, UserID);
	 preparedStmt.setString(4, UserName);
	 preparedStmt.setString(5, Subject); 
	 preparedStmt.setString(6, Date);
	 preparedStmt.setString(7, Comment);
	 
	 //Execute the statement
	 preparedStmt.execute();
	 con.close();
	 
	 String newNotices = readNotes();
	 output = "{\"status\":\"success\", \"data\": \"" + 
			 		newNotices + "\"}"; 
	 }
	
	catch (Exception e)
	{
	 //output = "Error while inserting the notice";
	 output = "{\"status\":\"error\", \"data\": \"Error while inserting the notice.\"}"; 
	 System.err.println(e.getMessage());
	 }
	return output; 
	}
	
	//Update function
	public String updateNotes(String noticeID, String FeedbackID, String UserID, String UserName, String Subject, String Date, String Comment) 
	{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 
			 // Create a prepared statement
			 String query = "update feedbacks set FeedbackID=?, UserID=?, UserName=?, Subject=?, Date=?, Comment=? where noticeID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 
			 // Binding values
			 preparedStmt.setString(1, FeedbackID);
			 preparedStmt.setString(2, UserID);
			 preparedStmt.setString(3, UserName);
			 preparedStmt.setString(4, Subject);
			 preparedStmt.setString(5, Date);
			 preparedStmt.setString(6, Comment);
			 preparedStmt.setInt(7, Integer.parseInt(noticeID));
			 
			 // Execute the statement
			 preparedStmt.execute();
			 con.close();
			 
			 String newNotices = readNotes();
			 output = "{\"status\":\"success\", \"data\": \"" + 
					 	newNotices + "\"}"; 
     		 //output = "Updated successfully";
			 }
			 
			 catch (Exception e)
			 {
			 //output = "Error while updating the notice.";
			 output = "{\"status\":\"error\", \"data\":\"Error while updating the Feedback.\"}";
			 System.err.println(e.getMessage());
			 }
			 return output;
	}
	
	//Delete function
	public String deleteNotes(String noticeID)
	{
	 String output = "";
	 try
	  {
	  Connection con = connect();
	  if (con == null)
	  {
	  return "Error while connecting to the database for deleting.";
	  }
	  
	  // Create a prepared statement
	  String query = "delete from feedbacks where noticeID=?";
	  PreparedStatement preparedStmt = con.prepareStatement(query);
	  
	  // Binding values
	  preparedStmt.setInt(1, Integer.parseInt(noticeID));

	  // Execute the statement
	  preparedStmt.execute();
	  con.close();
	  
	  String newNotices = readNotes();
	  output = "{\"status\":\"success\", \"data\": \"" + newNotices + "\"}"; 
	  //output = "Deleted successfully";
	  }
	 catch (Exception e)
	  {
	  //output = "Error while deleting the notice.";
	  output = "{\"status\":\"error\", \"data\": \"Error while deleting the Feedback.\"}";
	  System.err.println(e.getMessage());
	  }
	 return output; 
	}
}
