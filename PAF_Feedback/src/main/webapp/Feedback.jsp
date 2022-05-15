<%@page import="model.Feedback" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Feedback Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>

	<div class="container">
		<h1>Feedback Management</h1>
	
		<form id="formFeedback" name="formFeedback" method="post" action="Feedback.jsp">
			Feedback ID:<input id="FeedbackID" name="FeedbackID" type="text" class="form-control"><br>
			User ID:<input id="UserID" name="UserID" type="text" class="form-control"><br>
			User Name:<input id="UserName" name="UserName" type="text" class="form-control"><br>
			Subject:<input id="Subject" name="Subject" type="text" class="form-control"><br>
			Date:<input id="Date" name="Date" type="text" class="form-control"><br>
			Comment:<input id="Comment" name="Comment" type="text" class="form-control"><br>
			
			<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
			<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
		</form>
	
	<br>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	
	<br>
	
	<div id="divItemsGrid">
	<%
	Feedback noteObj = new Feedback();
		 		out.print(noteObj.readNotes());
	%>
	</div>
	
	</div>
</body>
</html>