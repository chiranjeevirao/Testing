<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<P> Time: ${serverTime}. </P>

<div>
<h2>Welcome to Happy Searching</h2></div>
<div>
<form action="search" style="text-align: center;">
<input type="text" name="searchfor">
<br><input type="submit">
</form>
</div>

</body>
</html>
