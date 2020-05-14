<%@ page
		language="java"
		contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"
		%>
<%
	String searchresult = "";
	try{searchresult = request.getAttribute("result").toString();}catch(Exception ex){}
%>
<!DOCTYPE html>
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<style type="text/css">
.auto-style1 {
	text-align: center;
}
.auto-style2 {
	border: 1px solid #000080;
}
select , input{
    background: #EAF3D1;}
button {
    background-color: #EAF3D1;}

</style>
</head>

<body>

<table  style="width:100%">
<tr>
<td style="text-align: center; vertical-align: middle; color: #000080;">
<img alt="" src="001.png"> 
<h2> Digital Microservice Demo: ArchAngel </h2>
</td>
</tr>
<tr>
<td class="auto-style2">
<form action="/query" method="post">
	<div class="auto-style1">
	<select name="car" id="input1" style="width: 125px">
		<option value="Canyon" selected="selected">Canyon</option>
		<option value="Caravan">Caravan</option>
		<option value="Compass">Compass</option>
		<option value="Edge">Edge</option>
		<option value="Maxima">Maxima</option>
		<option value="Scirocco">Scirocco</option>
	</select><br>
	<button type="submit" style="width: 115px" name="Search">Search</button>	
	</div>
</form>
</td>
</tr>
</table>
<div class="auto-style1">
</p>
<pre>
<%= searchresult %>
</pre>
</div>



</body>
</html>
