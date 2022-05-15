$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
 	{
 	$("#alertSuccess").hide();
 	}
 	$("#alertError").hide();
});

//SAVE
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();
 	
 	// Form validation-------------------
	var status = validateItemForm();
	if (status != true)
	{
	$("#alertError").text(status);
	$("#alertError").show();
	return;
	}
	
	// If valid-------------------------
 	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
 	{
 	url : "FeedbackAPI",
 	type : type,
 	data : $("#formFeedback").serialize(),
 	dataType : "text",
 	complete : function(response, status)
 	{
 		onItemSaveComplete(response.responseText, status);
 	}
 	});
 
});

function onItemSaveComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		
 		if (resultSet.status.trim() == "success")
 		{
 			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divItemsGrid").html(resultSet.data);
 		} else if (resultSet.status.trim() == "error")
 		{
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
 		}
 	} else if (status == "error")
 	{
 		$("#alertError").text("Error while saving.");
 		$("#alertError").show();
 	} else
 	{
 		$("#alertError").text("Unknown error while saving..");
 		$("#alertError").show();
 	} 
 	$("#hidItemIDSave").val("");
 	$("#formFeedback")[0].reset();
}

// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
{
	console.log("Update",$("#hidItemIDSave").val());
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	 $("#hidItemIDSave").val($(this).data("noticeid"));
	 //$("#FeedbackID").val($(this).closest("tr").find('td:eq(0)').text());
     $("#FeedbackID").val($(this).closest("tr").find('td:eq(0)').text());
	 $("#UserID").val($(this).closest("tr").find('td:eq(1)').text());
	 $("#UserName").val($(this).closest("tr").find('td:eq(2)').text());
	 $("#Subject").val($(this).closest("tr").find('td:eq(3)').text());
	 $("#Date").val($(this).closest("tr").find('td:eq(4)').text());
     $("#Comment").val($(this).closest("tr").find('td:eq(5)').text());
});

$(document).on("click", ".btnRemove", function(event)
{
 	$.ajax(
 	{
 		url : "FeedbackAPI",
 		type : "DELETE",
 		data : "noticeID=" + $(this).data("noticeid"),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onItemDeleteComplete(response.responseText, status);
 		}
 	});
});

function onItemDeleteComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success")
 		{
			 $("#alertSuccess").text("Successfully deleted.");
			 $("#alertSuccess").show();
			 
			 $("#divItemsGrid").html(resultSet.data);
 		} else if (resultSet.status.trim() == "error")
 		{
			 $("#alertError").text(resultSet.data);
			 $("#alertError").show();
 		}
 		} else if (status == "error")
 		{
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
 		} else
		{
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
 		}
}

// CLIENT-MODEL================================================================
function validateItemForm()
{
	
	// FeedbackID
	let FeedbackID = $("#FeedbackID").val().trim();
	if (!$.isNumeric(FeedbackID)) {
		return "Insert a numerical value for FeedbackID.";
	}
	
	// UserID
	let UserID = $("#UserID").val().trim();
	if (!$.isNumeric(UserID)) {
		return "Insert a numerical value for UserID.";
	}
	 
	// UserName
	if ($("#UserName").val().trim() == "")
	 {
	 return "Insert UserName.";
	 } 
	 
	// Subject
	if ($("#Subject").val().trim() == "")
	 {
	 return "Insert Subject.";
	 }
	 
	// Date
	let Date = $("#Date").val().trim();
	if (!$.isNumeric(Date)) {
		return "Insert a numerical value for Date.";
	}
	 
	// Comment
	if ($("#Comment").val().trim() == "")
	 {
	 return "Insert Comment.";
	 }

	return true;
}

