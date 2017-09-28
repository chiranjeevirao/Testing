<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ResultPage</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

<script type="text/javascript">

var myList = <%=request.getAttribute("resultvalue")%>

	// Builds the HTML Table out of myList.
	function buildHtmlTable(selector) {
	  var columns = addAllColumnHeaders(myList, selector);

	  for (var i = 0; i < myList.length; i++) {
	    var row$ = $('<tr/>');
	    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
	      var cellValue = myList[i][columns[colIndex]];
	      if (cellValue == null) cellValue = "";
	      row$.append($('<td/>').html(cellValue));
	    }
	    $(selector).append(row$);
	  }
	}

	// Adds a header row to the table and returns the set of columns.
	// Need to do union of keys from all records as some records may not contain
	// all records.
	function addAllColumnHeaders(myList, selector) {
	  var columnSet = [];
	  var headerTr$ = $('<tr/>');

	  for (var i = 0; i < myList.length; i++) {
	    var rowHash = myList[i];
	    for (var key in rowHash) {
	      if ($.inArray(key, columnSet) == -1) {
	        columnSet.push(key);
	        headerTr$.append($('<th/>').html(key));
	      }
	    }
	  }
	  $(selector).append(headerTr$);

	  return columnSet;
	}
</script>
</head>


<body onLoad="buildHtmlTable('#excelDataTable')">
  <table id="excelDataTable" border="1">
  </table>
  <p style="color: red;">${resultvalue1}</p>
</body>

</html>