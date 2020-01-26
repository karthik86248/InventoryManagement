<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 

"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="jquery.autocomplete.css" />
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.0/themes/smoothness/jquery-ui.css" />
<script type="text/javascript">

	function IsEmpty(str) {
		return (!str || 0 === str.length);
	}

	function allowsubmit__F1(t) {
		var a = t.P_Qty.value;
		var b = t.M_Qty.value;
		var c = t.M_ID.value;
		var d = t.P_NAME.value;
		
		if (IsEmpty(c) == true && IsEmpty(d) == true) {
			alert("Error: Because you need to enter a Material ID OR Product Name in the respective box");
			return false;
		
		}
		if (IsEmpty(c) == false) {
			
			if (isNaN(b) || IsEmpty(b) ) {
				alert("Error: Because you need to enter a number in Material Qty box");
				return false;
			}
			b = Number(b);
			if (b == 0 ) {
				alert("Error: Bbecause you need to enter a non-zero number in Material Qty box");
				return false;
			}
		}

		if (IsEmpty(d) == false) {
			
			if (isNaN(a) || IsEmpty(a)) {
				alert("Error: Because you need to enter a number in Product Qty box");
				return false;
			}
			a = Number(a);
			if (a == 0 ) {
				alert("Error: Bbecause you need to enter a non-zero number in Product Qty box");
				return false;
			}
			return true;	
		}
		return true;
	}
	
	function allowsubmit__F3(t) {
		var a = t.P1_Qty.value;
		var b = t.P2_Qty.value;
		var c = t.P3_Qty.value;
		if (isNaN(a) || isNaN(b) || isNaN(c)) {
			alert("Error: Because you need to enter a number in Qty box");
			return false;
		}
		
		if (IsEmpty(a) || IsEmpty(b) || IsEmpty(c)) {
			alert("Error: Because you need to enter a number in Qty box");
			return false;
		}		
		a = Number(a);
		b = Number(b);
		c = Number(c);
		if (a <= 0 || b <= 0 || c <= 0) {
			alert("Error: Bbecause you need to enter a positive number in Qty box");
			return false;
		}
		return true
	}
	
</script>
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js">
	
</script>

<script src="jquery.autocomplete.js"></script>
<style>
input {
	font-size: 120%;
}
</style>
<meta charset="ISO-8859-1">
<title>My Material Management System</title>
<!-- CSS -->
<style>
.myForm {
	font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size: 0.8em;
	padding: 1em;
	border: 1px solid #ccc;
	border-radius: 3px;
}

.myForm * {
	box-sizing: border-box;
}

.myForm label {
	padding: 0;
	font-weight: bold;
}

.myForm input {
	border: 1px solid #ccc;
	border-radius: 3px;
	font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	font-size: 0.9em;
	padding: 0.5em;
}

.myForm input[type="email"], .myForm input[type="password"] {
	width: 12em;
}

.myForm button {
	padding: 0.7em;
	border-radius: 0.5em;
	background: #eee;
	border: none;
	font-weight: bold;
}

.myForm button:hover {
	background: #ccc;
	cursor: pointer;
}
</style>
</head>
<body>
	Welcome to My Material Management System
	<br>
	<br>
	<h1>Materials Update</h1>
	<form class="myForm" onsubmit="return allowsubmit__F1(this)"
		NAME="form1" action="Materials" method="post">

		<INPUT TYPE="HIDDEN" NAME="FormName" VALUE="F1">
		<p>
			<label>Enter Material ID
				:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="M_ID"
				name="M_ID" size="25" /> <script>
					$("#M_ID").autocomplete("AutoComplete.jsp");
				</script>
			</label> <label>Enter Qty (with +/-) :<input type="text" id="M_Qty"
				name="M_Qty" size="10"></label> <label>Notes :<input
				type="text" id="M_Notes" name="M_Notes" size="10"></label> </label>
		</p>
		<p>
		<h4>---- OR ----</h4>

		<p>
			<label> Enter Product Name : <select id="P_NAME"
				name="P_NAME">
					<option label=" "></option>
					<option value="COMETPLUS">COMETPLUS</option>
					<option value="TruSkan S500">TruSkan S500</option>
					<option value="PLANET 45">PLANET 45</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				Enter Qty (with +/-) :<input type="text" id="P_Qty" name="P_Qty"
				size="10">
			</label> <label>Notes :<input type="text" id="P_Notes" name="P_Notes"
				size="10"></label>
		</p>
		<p>
			<label> <input type="submit" value="Update">
			</label>
		</p>
		<p>
		<h4>
			Notes: <br> 1. Don't update materials with unit "M" or "ROL".
			Use only materials with units "EA" 1. Don't use comma (,) in Notes
			box
		</h4>

	</form>

	<br>
	<hr>
	<h1>Materials Reporting</h1>
	<FORM class="myForm" NAME="form2" action="Materials" METHOD="POST">
		<INPUT TYPE="HIDDEN" NAME="FormName" VALUE="F2"> <INPUT
			TYPE="radio" NAME="radios" VALUE="radio1" CHECKED> Full
		Inventory <BR> <INPUT TYPE="radio" NAME="radios" VALUE="radio2">
		Mini Inventory <BR> <INPUT TYPE="SUBMIT" VALUE="Generate">
	</FORM>
	<hr>
	<h1>Materials Estimating</h1>
	<FORM class="myForm" onsubmit="return allowsubmit__F3(this)"
		NAME="form3" action="Materials" METHOD="POST">
		<INPUT TYPE="HIDDEN" NAME="FormName" VALUE="F3">
		<p>
			<!--  	<label>Enter Generated Report File :<input type="text" id="R_PATH"
				name="R_PATH" size="45" />
			</label> -->
		</p>
		<p>
			<label> Enter Product Name : <select id="P1_NAME"
				name="P1_NAME">
					<option value="COMETPLUS" selected>COMETPLUS</option>
					<option value="TruSkan S500">TruSkan S500</option>
					<option value="PLANET 45">PLANET 45</option>
			</select> Enter Qty : <input type="text" id="P1_Qty" name="P1_Qty" size="10">
			</label>
		</p>

		<p>
			<label> Enter Product Name : <select id="P2_NAME"
				name="P2_NAME">
					<option value="COMETPLUS">COMETPLUS</option>
					<option value="TruSkan S500" selected>TruSkan S500</option>
					<option value="PLANET 45">PLANET 45</option>
			</select> Enter Qty : <input type="text" id="P2_Qty" name="P2_Qty" size="10">
			</label>
		</p>

		<p>
			<label> Enter Product Name : <select id="P3_NAME"
				name="P3_NAME">
					<option value="COMETPLUS">COMETPLUS</option>
					<option value="TruSkan S500">TruSkan S500</option>
					<option value="PLANET 45" selected>PLANET 45</option>
			</select> Enter Qty : <input type="text" id="P3_Qty" name="P3_Qty" size="10">
			</label>
		</p>
		<p>
			<INPUT TYPE="SUBMIT" VALUE="Calculate">
		</p>
	</FORM>
</body>
</html>